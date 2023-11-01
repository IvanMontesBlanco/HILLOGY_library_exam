package com.example.HILLOGY_library_exam;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.HILLOGY_library_exam.classes.Book;
import com.example.HILLOGY_library_exam.exceptions.BookCheckedOutException;
import com.example.HILLOGY_library_exam.exceptions.BookDuplicatedException;
import com.example.HILLOGY_library_exam.exceptions.BookNotFoundException;
import com.example.HILLOGY_library_exam.exceptions.ISBNInvalidException;

//tag::hateoas-imports[]
//end::hateoas-imports[]

@RestController
class LibraryService {

	@Autowired 
	private Library library;
	
	void BookController(Library library) {
		this.library = library;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/library")
	CollectionModel<EntityModel<Book>> listAll() {
		List<EntityModel<Book>> books = library.findAll().stream()
				.map(book -> EntityModel.of(book,
						linkTo(methodOn(LibraryService.class).findByISBN(book.getISBN())).withSelfRel(),
						linkTo(methodOn(LibraryService.class).listAll()).withRel("books")))
				.collect(Collectors.toList());

		return CollectionModel.of(books, linkTo(methodOn(LibraryService.class).listAll()).withSelfRel());
	}
	// end::get-aggregate-root[]

	// checks if book is already in the library before adding it
	@PostMapping("/library")
	Book newBook(@RequestBody Book newBook) {
		return library.save(newBook);
	}

	// find book by ISBN
	// tag::get-single-item[]
	@GetMapping("/library/{ISBN}")
	EntityModel<Book> findByISBN(@PathVariable String ISBN) {
		Book book = library.findById(ISBN) //
				.orElseThrow(() -> new BookNotFoundException(ISBN));

		return EntityModel.of(book, //
				linkTo(methodOn(LibraryService.class).findByISBN(ISBN)).withSelfRel(),
				linkTo(methodOn(LibraryService.class).listAll()).withRel("books"));
	}
	// end::get-single-item[]

	@PutMapping("/library/{ISBN}")
	Book replaceBook(@RequestBody Book newBook, @PathVariable String ISBN) {

		return library.findById(ISBN) //
				.map(book -> {
					book.setTitle(newBook.getTitle());
					book.setAuthor(newBook.getAuthor());
					book.setISBN(newBook.getISBN());
					book.setAvailable(newBook.getAvailable());
					return library.save(book);
				}) //
				.orElseGet(() -> {
					newBook.setISBN(ISBN);
					return library.save(newBook);
				});
	}

	// checks if book is checked out before deleting it
	@DeleteMapping("/library/{ISBN}")
	void deleteBook(@PathVariable String ISBN) {
		library.deleteById(ISBN);
	}
}
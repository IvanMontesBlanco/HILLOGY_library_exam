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
		try {
			findByISBN(newBook.getISBN());
			throw new BookDuplicatedException(newBook.getISBN());
		} catch (BookNotFoundException e) {
			return library.save(newBook);
		}
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

	// find book by title
	// tag::get-aggregate-root[]
	@GetMapping("/library/findByTitle/{title}")
	CollectionModel<EntityModel<Book>> findByTitle(@PathVariable String title) {
		List<EntityModel<Book>> books = library.findAll().stream().filter(book -> book.getTitle().contains(title))
				.map(book -> EntityModel.of(book,
						linkTo(methodOn(LibraryService.class).findByISBN(book.getISBN())).withSelfRel(),
						linkTo(methodOn(LibraryService.class).listAll()).withRel("books")))
				.collect(Collectors.toList());

		return CollectionModel.of(books, linkTo(methodOn(LibraryService.class).listAll()).withSelfRel());
	}
	// end::get-aggregate-root[]

	// find book by author
	// tag::get-aggregate-root[]
	@GetMapping("/library/findByAuthor/{author}")
	CollectionModel<EntityModel<Book>> findByAuthor(@PathVariable String author) {
		List<EntityModel<Book>> books = library.findAll().stream().filter(book -> book.getAuthor().equals(author))
				.map(book -> EntityModel.of(book,
						linkTo(methodOn(LibraryService.class).findByISBN(book.getISBN())).withSelfRel(),
						linkTo(methodOn(LibraryService.class).listAll()).withRel("books")))
				.collect(Collectors.toList());

		return CollectionModel.of(books, linkTo(methodOn(LibraryService.class).listAll()).withSelfRel());
	}
	// end::get-aggregate-root[]

	// list available books
	// tag::get-aggregate-root[]
	@GetMapping("/library/listAvailable")
	CollectionModel<EntityModel<Book>> listAvailable() {
		List<EntityModel<Book>> books = library.findAll().stream().filter(book -> book.getAvailable())
				.map(book -> EntityModel.of(book,
						linkTo(methodOn(LibraryService.class).findByISBN(book.getISBN())).withSelfRel(),
						linkTo(methodOn(LibraryService.class).listAll()).withRel("books")))
				.collect(Collectors.toList());

		return CollectionModel.of(books, linkTo(methodOn(LibraryService.class).listAll()).withSelfRel());
	}
	// end::get-aggregate-root[]

	// updates state of a single book
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

	// changes a single book's availability
	// not in mapping since it's reused for the checkOutBook and returnBook
	// functions
	void updateAvailability(String ISBN, Boolean available) {
		Book toUpdate = library.findById(ISBN).get();
		toUpdate.setAvailable(available);
		replaceBook(toUpdate, ISBN);
	}

	// checks out a single book
	@GetMapping("/library/checkout/{ISBN}")
	void checkOutBook(@PathVariable String ISBN) {
		Book toCheckOut = library.findById(ISBN).get();
		if (toCheckOut.getAvailable()) {
			updateAvailability(ISBN, false);
		} else {
			throw new BookCheckedOutException(ISBN);
		}
	}

	// returns a single book
	@GetMapping("/library/return/{ISBN}")
	void returnBook(@PathVariable String ISBN) {
		updateAvailability(ISBN, true);
	}

	// checks if book is checked out before deleting it
	@DeleteMapping("/library/{ISBN}")
	void deleteBook(@PathVariable String ISBN) {
		library.deleteById(ISBN);
	}
}
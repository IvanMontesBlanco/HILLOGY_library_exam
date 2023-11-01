package com.example.HILLOGY_library_exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exceptions.BookCheckedOutException;
import exceptions.BookNotFoundException;
import exceptions.BookDuplicatedException;
import exceptions.ISBNInvalidException;

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
	List<Book> all() {
		return library.findAll();
	}
	// end::get-aggregate-root[]

	// checks if book is already in the library before adding it
	@PostMapping("/library")
	Book newBook(@RequestBody Book newBook) throws BookDuplicatedException {
		try {
			Book check = findByISBN(newBook.getISBN());
			throw new BookDuplicatedException(check.getISBN());
		} catch (BookNotFoundException e) {
			return library.save(newBook);
		}
	}

	// find book by ISBN
	@GetMapping("/library/{ISBN}")
	Book findByISBN(@PathVariable String ISBN) throws BookNotFoundException {

		return library.findById(ISBN).orElseThrow(() -> new BookNotFoundException(ISBN));
	}

	@PutMapping("/library/{ISBN}")
	Book replaceBook(@RequestBody Book newBook, @PathVariable String ISBN) throws ISBNInvalidException {

		return library.findById(ISBN).map(book -> {
			book.setTitle(newBook.getTitle());
			book.setAuthor(newBook.getAuthor());
			try {
				book.setISBN(newBook.getISBN());
			} catch (ISBNInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			book.setAvailable(newBook.getAvailable());
			return library.save(book);
		}).orElseGet(() -> {
			try {
				newBook.setISBN(ISBN);
			} catch (ISBNInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return library.save(newBook);
		});
	}

	// checks if book is checked out before deleting it
	@DeleteMapping("/library/{ISBN}")
	void deleteBook(@PathVariable String ISBN)
			throws ISBNInvalidException, BookNotFoundException, BookCheckedOutException {
		Book check = findByISBN(ISBN);

		if (!check.getAvailable()) {
			throw new BookCheckedOutException(ISBN);
		} else {
			library.deleteById(ISBN);
		}
	}
}
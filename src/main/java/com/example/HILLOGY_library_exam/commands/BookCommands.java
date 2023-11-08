package com.example.HILLOGY_library_exam.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.example.HILLOGY_library_exam.entities.Book;
import com.example.HILLOGY_library_exam.exceptions.BookDuplicatedException;
import com.example.HILLOGY_library_exam.exceptions.BookNotFoundException;
import com.example.HILLOGY_library_exam.repositories.Library;
import com.example.HILLOGY_library_exam.services.LibraryService;

@Component
public class BookCommands implements CommandMarker {

	@Autowired
	private LibraryService libraryService;

	public BookCommands(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	@CliCommand(value = { "list_books", "listBooks" }, help = "Lists all books in the library.")
	public String list_books() {
		return bookCollection2String(libraryService.listAll());
	}

	@CliCommand(value = { "list_available",
			"listAvailable" }, help = "Lists all books in the library that have not been checked out by users.")
	public String list_available() {
		return bookCollection2String(libraryService.listAvailable());
	}

	@CliCommand(value = { "find_by_ISBN",
			"findByISBN" }, help = "Finds a single book by its ISBN. Looks for exact matches.")
	public String find_by_ISBN(@CliOption(key = { "", "ISBN" }, mandatory = true) String ISBN) {
		try {
			return bookEntity2String(libraryService.findByISBN(ISBN));
		}
		catch(BookNotFoundException e) {
			return e.getMessage();
		}
	}

	@CliCommand(value = { "find_by_title",
			"findByTitle" }, help = "Finds books by its title. Includes similar titles to the one given.")
	public String find_by_title(@CliOption(key = { "", "title" }, mandatory = true) String title) {
		return bookCollection2String(libraryService.findByTitle(title));
	}

	@CliCommand(value = { "find_by_author",
			"findByAuthor" }, help = "Finds books by their author. Includes similar authors to the one given.")
	public String find_by_author(@CliOption(key = { "", "author" }, mandatory = true) String author) {
		return bookCollection2String(libraryService.findByAuthor(author));
	}

	@CliCommand(value = { "add_book",
			"addBook" }, help = "Adds a book with the given ISBN, title and author. Uses the format --ISBN BOOK_ISBN --title BOOK_TITLE --author BOOK_AUTHOR.")
	public String add_book(@CliOption(key = { "i", "ISBN" }, mandatory = true) String ISBN,
			@CliOption(key = { "t", "title" }, mandatory = true) String title,
			@CliOption(key = { "a", "author" }, mandatory = true) String author) {
		Book newBook = new Book(ISBN, title, author);

		if (!newBook.validateISBN(ISBN)) {
			return "Invalid ISBN: " + ISBN;
		} else {
			try {
				return libraryService.newBook(newBook).toString();
			}
			catch(BookDuplicatedException e) {
				return e.getMessage();
			}
		}
	}

//	@CliCommand(value = { "delete_book", "deleteBook" }, help = "Deletes the book with the ISBN given.")
//	public String delete_book(@CliOption(key = { "", "ISBN" }, mandatory = true) String ISBN) {
//		return books2string("http://localhost:8080/api/library/delete/" + ISBN);
//	}

	// returns a given book collection in text form
	private String bookCollection2String(CollectionModel<EntityModel<Book>> bookCollection) {
		StringBuilder toret = new StringBuilder();
		Iterator<EntityModel<Book>> itr = bookCollection.iterator();

		while (itr.hasNext()) {
			toret.append(bookEntity2String(itr.next()));
		}

		return toret.toString();
	}

	// returns a given book entity model in text form
	private String bookEntity2String(EntityModel<Book> bookEntity) {
		return bookEntity.getContent().toString() + "\n";
	}

	// commands for testing
	// add_book --i 080442957X --t title --a author
}
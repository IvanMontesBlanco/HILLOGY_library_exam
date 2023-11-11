package com.example.HILLOGY_library_exam.exceptions;

public class BookNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BookNotFoundException(String ISBN) {
		super("The library does not have a book with ISBN (" + ISBN + ").");
	}
}

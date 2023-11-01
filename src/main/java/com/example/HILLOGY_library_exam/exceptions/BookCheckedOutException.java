package com.example.HILLOGY_library_exam.exceptions;

public class BookCheckedOutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BookCheckedOutException(String ISBN) {
		super("Book with ISBN (" + ISBN + ") is currently checked out.");
	}
}
package com.example.HILLOGY_library_exam.exceptions;

public class BookDuplicatedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BookDuplicatedException(String ISBN) {
		super("A book with ISBN (" + ISBN + ") already exists in the library.");
	}
}

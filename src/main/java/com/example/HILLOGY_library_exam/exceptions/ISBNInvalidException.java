package com.example.HILLOGY_library_exam.exceptions;

public class ISBNInvalidException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ISBNInvalidException(String ISBN) {
		super(ISBN + " is not a valid ISBN number.");
	}
}

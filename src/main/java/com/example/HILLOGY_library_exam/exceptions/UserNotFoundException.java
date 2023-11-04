package com.example.HILLOGY_library_exam.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(Long id) {
		super("The user with id (" + String.valueOf(id) + ") was not found or does not exist.");
	}

}

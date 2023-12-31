package com.example.HILLOGY_library_exam.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.HILLOGY_library_exam.exceptions.BookDuplicatedException;

@ControllerAdvice
class BookDuplicatedAdvice {

	@ResponseBody
	@ExceptionHandler(BookDuplicatedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String BookNotFoundHandler(BookDuplicatedException ex) {
		return ex.getMessage();
	}
}
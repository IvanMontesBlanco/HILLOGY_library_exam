package com.example.HILLOGY_library_exam.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.HILLOGY_library_exam.exceptions.BookCheckedOutException;

@ControllerAdvice
class BookCheckedOutAdvice {

  @ResponseBody
  @ExceptionHandler(BookCheckedOutException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  String bookCheckedOutHandler(BookCheckedOutException ex) {
    return ex.getMessage();
  }
}
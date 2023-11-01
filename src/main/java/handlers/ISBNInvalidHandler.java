package handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import exceptions.ISBNInvalidException;

@ControllerAdvice
class ISBNInvalidAdvice {

  @ResponseBody
  @ExceptionHandler(ISBNInvalidException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String BookNotFoundHandler(ISBNInvalidException ex) {
    return ex.getMessage();
  }
}
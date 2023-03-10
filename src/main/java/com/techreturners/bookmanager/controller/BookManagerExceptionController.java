package com.techreturners.bookmanager.controller;
import com.techreturners.bookmanager.exceptionhandler.BookNotFoundException;
import com.techreturners.bookmanager.exceptionhandler.DuplicateBookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class BookManagerExceptionController {
    @ExceptionHandler(BookNotFoundException.class)
        public ResponseEntity<Object> exception(BookNotFoundException exception) {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<Object> exception(DuplicateBookException exception) {
        return new ResponseEntity<>("Book already exists", HttpStatus.SEE_OTHER);
    }


}


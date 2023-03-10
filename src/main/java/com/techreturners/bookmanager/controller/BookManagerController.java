package com.techreturners.bookmanager.controller;

import com.techreturners.bookmanager.exceptionhandler.BookNotFoundException;
import com.techreturners.bookmanager.exceptionhandler.DuplicateBookException;
import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.service.BookManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class BookManagerController {

    @Autowired
    BookManagerService bookManagerService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookManagerService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping({"/{bookId}"})
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookManagerService.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException();
        }

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        if(bookManagerService.getBookById(book.getId()) != null) throw new DuplicateBookException();
        Book newBook = bookManagerService.insertBook(book);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("book", "/api/v1/book/" + newBook.getId().toString());
        return new ResponseEntity<>(newBook, httpHeaders, HttpStatus.CREATED);
    }

    //User Story 4 - Update Book By Id Solution
    @PutMapping({"/{bookId}"})
    public ResponseEntity<Book> updateBookById(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
        if(bookManagerService.getBookById(bookId) == null)throw new BookNotFoundException();
        bookManagerService.updateBookById(bookId, book);
        return new ResponseEntity<>(bookManagerService.getBookById(bookId), HttpStatus.OK);
    }

    //Delete Book By Id
    @DeleteMapping ({"/{bookId}"})
    public void deleteBookById(@PathVariable("bookId") Long bookId) {
        bookManagerService.deleteBookById(bookId);
    }

}

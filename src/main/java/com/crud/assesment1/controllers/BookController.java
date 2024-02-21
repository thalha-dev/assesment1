package com.crud.assesment1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.assesment1.entity.Book;
import com.crud.assesment1.repository.BookRepository;

@RestController
public class BookController {

  @Autowired
  private BookRepository bookRepository;

  @PostMapping("/book/addNewBook")
  public ResponseEntity<Book> addNewStudent(@RequestBody Book newBook) {
    Book addedBook = bookRepository.save(newBook);
    return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
  }

  @GetMapping("/book/getAllBooks")
  public ResponseEntity<List<Book>> getAllBooks() {
    try {
      List<Book> students = bookRepository.findAll();
      if (students.isEmpty()) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(students, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/book/removeBookFromLibrary")
  public ResponseEntity<Book> removeBookFromLibrary(@RequestParam Long book_id) {
    try {
      bookRepository.deleteById(book_id);
      return new ResponseEntity<>(null, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

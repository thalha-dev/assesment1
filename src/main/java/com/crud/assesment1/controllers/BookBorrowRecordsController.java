package com.crud.assesment1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.assesment1.entity.Book;
import com.crud.assesment1.entity.BookBorrowRecords;
import com.crud.assesment1.entity.Student;
import com.crud.assesment1.repository.BookBorrowRecordsRepository;
import com.crud.assesment1.repository.BookRepository;
import com.crud.assesment1.repository.StudentRepository;

@RestController
public class BookBorrowRecordsController {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private BookBorrowRecordsRepository bookBorrowRecordsRepository;

  @Autowired
  private BookRepository bookRepository;

  @PostMapping("/bookBorrow/borrowBook")
  public ResponseEntity<BookBorrowRecords> borrowABook(@RequestBody BookBorrowRecords newApplication) {
    if (newApplication.getStudent() == null || newApplication.getStudent().getStudentId() == null) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    String bookName = newApplication.getBookName();
    Long studentId = newApplication.getStudent().getStudentId();
    Book book = bookRepository.findByBookName(bookName);
    if (book.getNoOfBooks() < 1) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    Optional<Student> student = studentRepository.findById(studentId);

    if (book != null && student.isPresent()) {
      book.setNoOfBooks(book.getNoOfBooks() - 1);
      student.get().setCurrentBorrowedBook(book);
      studentRepository.save(student.get());
      newApplication.setBook(bookRepository.save(book));
      newApplication.setStudent(student.get());
    } else {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    List<BookBorrowRecords> existedBooksWithSameName = bookBorrowRecordsRepository.findByBook_BookId(book.getBookId());

    for (BookBorrowRecords record : existedBooksWithSameName) {
      if (record.getStudent().getStudentId() == studentId) {
        if (newApplication.getBorrowedDate().before(record.getDueDate())) {
          return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
      }
    }

    BookBorrowRecords borrowedRecord = bookBorrowRecordsRepository.save(newApplication);
    Student std = student.get();
    std.setCurrentBorrowedBookRecord(borrowedRecord);
    studentRepository.save(std);
    return new ResponseEntity<>(borrowedRecord, HttpStatus.CREATED);
  }

  @PutMapping("/bookBorrow/returnBook")
  public ResponseEntity<BookBorrowRecords> returnBook(@RequestBody BookBorrowRecords returnApplication) {
    if (returnApplication.getStudent() == null || returnApplication.getStudent().getStudentId() == null) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    String bookName = returnApplication.getBookName();
    Long studentId = returnApplication.getStudent().getStudentId();
    Book book = bookRepository.findByBookName(bookName);
    Optional<Student> student = studentRepository.findById(studentId);

    if (book != null && student.isPresent()) {
      book.setNoOfBooks(book.getNoOfBooks() + 1);
      student.get().setCurrentBorrowedBook(null);
      studentRepository.save(student.get());
      bookRepository.save(book);
    } else {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    Student stud = student.get();
    BookBorrowRecords borrowApplication = bookBorrowRecordsRepository
        .findById(stud.getCurrentBorrowedBookRecord().getBookBorrowId()).get();

    stud.setCurrentBorrowedBookRecord(null);
    studentRepository.save(stud);

    if (borrowApplication.getDueDate() != null && returnApplication.getReturnedDate() != null) {
      if (borrowApplication.getDueDate().before(returnApplication.getReturnedDate())) {
        borrowApplication.setFineAmount(100);
      }
    }

    borrowApplication.setReturnedDate(returnApplication.getReturnedDate());

    BookBorrowRecords borrowedRecord = bookBorrowRecordsRepository.save(borrowApplication);
    return new ResponseEntity<>(borrowedRecord, HttpStatus.CREATED);
  }

  @GetMapping("/bookBorrow/getAllRecords")
  public ResponseEntity<List<BookBorrowRecords>> getAllBooks() {
    try {
      List<BookBorrowRecords> records = bookBorrowRecordsRepository.findAll();
      if (records.isEmpty()) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(records, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

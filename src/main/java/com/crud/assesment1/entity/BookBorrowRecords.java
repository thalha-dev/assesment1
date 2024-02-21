package com.crud.assesment1.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookBorrowRecords {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookBorrowId;
  private Date borrowedDate;
  private Date dueDate;
  private Date returnedDate;
  private Integer fineAmount;
  private String bookName;
  @ManyToOne
  @JoinColumn(name = "student_id", referencedColumnName = "studentId")
  @JsonManagedReference
  private Student student;
  @ManyToOne
  @JoinColumn(name = "book_id", referencedColumnName = "bookId")
  private Book book;

}

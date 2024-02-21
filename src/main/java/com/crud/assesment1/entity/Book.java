package com.crud.assesment1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(uniqueConstraints = @UniqueConstraint(name = "book_name_unique", columnNames = "book_name"))
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;
  @Column(name = "book_name", nullable = false)
  private String bookName;
  private String authorName;
  private Integer noOfBooks;
}

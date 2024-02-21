package com.crud.assesment1.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(uniqueConstraints = @UniqueConstraint(name = "phone_number_unique", columnNames = "phone_number"))
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long studentId;
  private String studentName;
  @Column(name = "phone_number", nullable = false)
  private Long phoneNumber;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "book_id", referencedColumnName = "bookId")
  private Book currentBorrowedBook;
}

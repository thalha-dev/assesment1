package com.crud.assesment1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.assesment1.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  Book findByBookName(String bookName);
}

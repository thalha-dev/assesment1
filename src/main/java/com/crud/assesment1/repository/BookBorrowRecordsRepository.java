package com.crud.assesment1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.assesment1.entity.BookBorrowRecords;

@Repository
public interface BookBorrowRecordsRepository extends JpaRepository<BookBorrowRecords, Long> {

}

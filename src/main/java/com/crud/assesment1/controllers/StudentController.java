package com.crud.assesment1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.assesment1.entity.Student;
import com.crud.assesment1.repository.StudentRepository;

@RestController
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;

  @PostMapping("/student/addNewStudent")
  public ResponseEntity<Student> addNewStudent(@RequestBody Student newStudent) {
    Student savedStudent = studentRepository.save(newStudent);
    return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
  }

  @GetMapping("/student/getAllStudents")
  public ResponseEntity<List<Student>> getAllStudents() {
    try {
      List<Student> students = studentRepository.findAll();
      if (students.isEmpty()) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(students, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

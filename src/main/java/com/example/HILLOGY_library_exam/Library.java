package com.example.HILLOGY_library_exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HILLOGY_library_exam.classes.Book;

@Repository
interface Library extends JpaRepository<Book, String> {

}
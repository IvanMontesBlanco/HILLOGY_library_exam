package com.example.HILLOGY_library_exam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.HILLOGY_library_exam.entities.Book;

@Repository
public interface Library extends JpaRepository<Book, String> {
	CrudRepository<Book, String> findByTitle(String title);
	ListCrudRepository<Book, String> findByAuthor(String author);
	ListCrudRepository<Book, Boolean> findByAvailable(Boolean available);
}
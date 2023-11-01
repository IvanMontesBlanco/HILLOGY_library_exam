package com.example.HILLOGY_library_exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface Library extends JpaRepository<Book, String> {

}
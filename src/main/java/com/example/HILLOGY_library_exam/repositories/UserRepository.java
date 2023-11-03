package com.example.HILLOGY_library_exam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HILLOGY_library_exam.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
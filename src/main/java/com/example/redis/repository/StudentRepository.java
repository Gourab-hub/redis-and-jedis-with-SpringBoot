package com.example.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.redis.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	 
}
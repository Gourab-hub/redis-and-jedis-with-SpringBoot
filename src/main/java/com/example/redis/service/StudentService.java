package com.example.redis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.redis.entity.Student;
import com.example.redis.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Cacheable(value = "students")
	public List<Student> getAllStudent() {
        System.out.println("Fetching from DB: getAllStudent");
		return studentRepository.findAll();
	}
	@Cacheable(value = "students", key = "#id")
	public Optional<Student> getStudentById(Integer id) {
        System.out.println("Fetching from DB: getStudentById");
		return studentRepository.findById(id);
	}

	public Student createStudent(Student student) {
        System.out.println("Fetching from DB: createStudent");
		return studentRepository.save(student);
	}

	public Student updateStudent(Integer id, Student studentDetails) {
        System.out.println("Fetching from DB: updateStudent");

		return studentRepository.findById(id).map(user -> {
			user.setName(studentDetails.getName());
			user.setCity(studentDetails.getCity());
			return studentRepository.save(user);
		}).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
	}
	@CacheEvict(value = "students", key = "#id")
	public void deleteStudent(Integer id) {
        System.out.println("Fetching from DB: deleteStudent");

//		studentRepository.deleteById(id);
	}
}


package com.example.redis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.config.JedisConfig;
import com.example.redis.entity.Student;
import com.example.redis.service.JedisService;
import com.example.redis.service.RedisService;
import com.example.redis.service.StudentService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private JedisService jedisService;

	@GetMapping("/students")
	public List<Student> getAllUsers() {
		return studentService.getAllStudent();
	}

	@GetMapping("/student/{id}")
	@ResponseBody
	public Optional<Student> getByIdStudent(@PathVariable("id") int id) {
		Optional<Student> student = studentService.getStudentById(id);
		return student;
	}

	@PostMapping("/addStudent")
	public Student createUser(@RequestBody Student student) {
		return studentService.createStudent(student);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> updateUser(@PathVariable Integer id, @RequestBody Student studentDetails) {
		try {
			return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/redis")
	public void redislpush(@RequestBody Student student) {
		jedisService.pushToList("gourab", student.getName());
		List<String> students = jedisService.getList("gourab");
		System.out.println(students);
//		String pop = jedisService.popFromList("gourab");
//		System.out.println(pop);

	}

}

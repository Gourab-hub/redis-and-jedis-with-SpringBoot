package com.example.redis.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.redis.entity.Student;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void pushToList(String key, String value) {
		redisTemplate.opsForList().leftPush(key, value);
	}

	public List<Object> getList(String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	public String setValue(String id, Student student) {
		redisTemplate.opsForValue().set("students::" + id, student, 10, TimeUnit.MINUTES);
		return "Value stored successfully!";
	}

	public Student popFromList(String key) {
		return (Student) redisTemplate.opsForList().leftPop(key);
	}

	public Student getValue(String id) {
		return (Student) redisTemplate.opsForValue().get("students::" + id);
	}


}

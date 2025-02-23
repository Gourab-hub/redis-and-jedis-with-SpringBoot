package com.example.redis.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.redis.config.JedisConfig;

import redis.clients.jedis.Jedis;

@Service
public class JedisService {

	@Autowired
	private JedisConfig jedisConfig;

	public void pushToList(String key, String value) {
		try (Jedis jedis = jedisConfig.jedisPool().getResource()) {
			jedis.lpush(key, value);
		}
	}

	public List<String> getList(String key) {
		try (Jedis jedis = jedisConfig.jedisPool().getResource()) {
			return jedis.lrange(key, 0, -1);
		}
	}

	public String setValue(String id, String studentJson) {
		try (Jedis jedis = jedisConfig.jedisPool().getResource()) {
			jedis.setex("students::" + id, (int) TimeUnit.MINUTES.toSeconds(10), studentJson);
			return "Value stored successfully!";
		}
	}

	public String popFromList(String key) {
		try (Jedis jedis = jedisConfig.jedisPool().getResource()) {
			return jedis.lpop(key);
		}
	}

	public String getValue(String id) {
		try (Jedis jedis = jedisConfig.jedisPool().getResource()) {
			return jedis.get("students::" + id);
		}
	}

}

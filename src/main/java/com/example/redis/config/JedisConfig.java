package com.example.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration

public class JedisConfig {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

//	private final JedisPool jedisPool = new JedisPool(redisHost, 6379);

	@Bean
	public JedisPool jedisPool() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(10);
		poolConfig.setMinIdle(2);
		poolConfig.setMaxIdle(5);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setJmxEnabled(false); 
		return new JedisPool(poolConfig, redisHost, redisPort);
	}

}

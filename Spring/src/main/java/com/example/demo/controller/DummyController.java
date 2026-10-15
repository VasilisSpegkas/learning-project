package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class DummyController {


    // Endpoint to check if Spring is up and running
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring";
    }


    // Endpoint to check if Redis is up and running
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @GetMapping("/redis-test")
    public String testRedis() {
        // Add key and value to Redis and return it
        redisTemplate.opsForValue().set("greeting", "Hello from Redis");
        return redisTemplate.opsForValue().get("greeting");
    }

    @GetMapping("/slow-data")
    public String getSlowData() throws InterruptedException {

        // Check if this value already exists in Redis. If it does return it immediately and finish
        String cached = redisTemplate.opsForValue().get("slow-data");
        if (cached != null) {
            return "From Redis (fast): " + cached;
        }

        // If it doesn't simulate a (time consuming) calculation
        Thread.sleep(2000);
        String result = "Computed value at " + System.currentTimeMillis();

        // Save the result into Redis so next time it's already there and return the result
        redisTemplate.opsForValue().set("slow-data", result);
        return "From computation (slow): " + result;
    }

}

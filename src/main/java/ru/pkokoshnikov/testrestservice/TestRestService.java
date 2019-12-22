package ru.pkokoshnikov.testrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class TestRestService {
    public static void main(String[] args) {
        SpringApplication.run(TestRestService.class, args);
    }
}
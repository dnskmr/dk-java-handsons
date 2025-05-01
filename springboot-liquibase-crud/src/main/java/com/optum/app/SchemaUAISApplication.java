package com.optum.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.optum.dto")
@EnableJpaRepositories(basePackages = "com.optum.repository")
public class SchemaUAISApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchemaUAISApplication.class, args);
    }
}

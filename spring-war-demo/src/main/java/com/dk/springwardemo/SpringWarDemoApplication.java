package com.dk.springwardemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dinesh
 * @version 1.0
 * @since 10/20/2021
 */

@SpringBootApplication
public class SpringWarDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWarDemoApplication.class, args);
    }

}

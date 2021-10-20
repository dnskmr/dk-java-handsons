package com.dk.springwardemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dinesh
 * @version 1.0
 * @since 10/20/2021
 */
@RestController
public class HomeController {

    /**
     * @return the String
     */
    @GetMapping("/")
    public String init() {
        return "API Application is Running...";
    }

    /**
     * @return the String
     */
    @GetMapping("/hello")
    public String hello() {
        return "Welcome to War deployment";
    }
}

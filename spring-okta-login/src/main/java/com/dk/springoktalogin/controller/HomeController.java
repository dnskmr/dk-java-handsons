package com.dk.springoktalogin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Dinesh
 * @version 1.0
 * @since 10/06/2021
 */
@RestController
public class HomeController {
    @GetMapping("/")
    public String greeting(Principal principal) {
        return "Welcome " + principal.getName() + " for Okta Demo";
    }
}

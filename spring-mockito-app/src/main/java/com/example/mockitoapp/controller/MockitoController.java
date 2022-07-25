package com.example.mockitoapp.controller;

import com.example.mockitoapp.service.MockitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mockito")
public class MockitoController {

    @Autowired
    private MockitoService mockitoService;


    /**
     *
     * @return
     */
    @GetMapping("/welcome")
    public String welcome() {
        return mockitoService.welcome();
    }
}

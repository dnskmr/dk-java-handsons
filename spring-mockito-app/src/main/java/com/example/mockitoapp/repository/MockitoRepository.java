package com.example.mockitoapp.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MockitoRepository {

    /**
     *
     * @return
     */
    public String welcome() {
        return "Welcome to Mockito Test App";
    }
}

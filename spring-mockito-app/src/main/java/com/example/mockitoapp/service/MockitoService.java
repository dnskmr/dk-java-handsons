package com.example.mockitoapp.service;

import com.example.mockitoapp.repository.MockitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockitoService {

    @Autowired
    private MockitoRepository mockitoRepository;

    /**
     *
     * @return
     */
    public String welcome() {
        return mockitoRepository.welcome();
    }
}

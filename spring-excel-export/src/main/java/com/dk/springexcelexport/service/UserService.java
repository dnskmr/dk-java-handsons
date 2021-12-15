package com.dk.springexcelexport.service;

import com.dk.springexcelexport.model.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    /**
     * @return
     */
    public List<User> listAll() {
        List<User> userList = Arrays.asList(new User(123, "abc@gmail.com", "12345",
                "ABC", true, "admin"));
        return userList;
    }
}

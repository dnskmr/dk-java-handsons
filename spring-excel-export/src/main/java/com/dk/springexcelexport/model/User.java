package com.dk.springexcelexport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private boolean enabled;
    private String roles;
}

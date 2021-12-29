package com.example.springboot.dto;

import com.example.springboot.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String surname;
    private String email;
    private int age;
    private String password;
    private Set<String> roleName;
}

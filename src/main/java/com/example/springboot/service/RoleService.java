package com.example.springboot.service;

import com.example.springboot.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    void add(Role role);
    void edit(Role role);
    Role getById(Long id);
    Role findRoleByName(String name);
}
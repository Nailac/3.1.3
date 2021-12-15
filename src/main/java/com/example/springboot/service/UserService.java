package com.example.springboot.service;

import com.example.springboot.model.User;

;import java.util.List;

public interface UserService {
    List<User> findAll();
    User getById(long id);
    User findUserByName(String name);
    void save(User user);
    void update(User user);
    void delete(User user);
}

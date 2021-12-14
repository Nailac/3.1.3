package com.example.springboot.service;

import com.example.springboot.model.Role;
import com.example.springboot.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void add(Role role) {
        roleRepository.save(role);
    }

    public void edit(Role role) {
        roleRepository.save(role);
    }

    public Role getById(Long id) {
        return roleRepository.getById(id);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}

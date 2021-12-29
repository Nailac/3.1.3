package com.example.springboot.controller;

import com.example.springboot.dto.UserDto;
import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class UserRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRestController(RoleService roleService, UserService userService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


//    Все юзеры
    @GetMapping("admin/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

//Определённый юзер
    @GetMapping("admin/getUser")
    public ResponseEntity<User> getUs(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles().stream().map(Role::getName).reduce((r1, r2) -> String.join(", ", r1, r2)).orElse(""));
        return ResponseEntity.ok(user);
    }


//    Все роли
    @GetMapping("admin/roles")
    public List<Role> getAllRoles(){
        return roleService.findAll();
    }

//    Получение юзера по id
    @GetMapping("admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") long id){
        User user =  userService.getById(id);
        return ResponseEntity.ok(user);
    }

//  Создание нового юзера
    @PostMapping("admin")
    public ResponseEntity<Void> createNewUser(@RequestBody UserDto userDto){
        User user = modelMapper.map(userDto,User.class);
        user.setRoles(userDto.getRoleName().stream().map(roleService::findRoleByName).collect(Collectors.toSet()));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    Изменение юзера
    @PutMapping("admin")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        user.setRoles(userDto.getRoleName().stream().map(roleService::findRoleByName).collect(Collectors.toSet()));
        userService.update(user);
        return ResponseEntity.ok().build();
    }

//    Удаление юзера
    @DeleteMapping("admin/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") long id){
        User user = userService.getById(id);
        userService.delete(user);
        return ResponseEntity.ok().build();
    }

}

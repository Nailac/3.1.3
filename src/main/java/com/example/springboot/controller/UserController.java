package com.example.springboot.controller;

import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.RoleServiceImpl;
import com.example.springboot.service.UserService;
import com.example.springboot.service.UserServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;


    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/admin")
    public String index(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("listRoles", roleService.findAll());
        model.addAttribute("listUser", userService.findAll());
        return "/pageAdmin";
    }


    @GetMapping("/user")
    public String infoUser(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles().stream().map(Role::getName).reduce((r1, r2) -> String.join(", ", r1, r2)).orElse(""));
        return "/pageUser";
    }


//    @GetMapping("/user/new_user")
//    public String addUser(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("roles", roleService.findAll());
//        return "/new";
//    }

//    @PostMapping("/user/new")
//    public String create(@ModelAttribute("user") User user,
//                         @RequestParam(value = "rolesMas") String[] role){
//        Set<Role> rolesSet = new HashSet<>();
//        for (String rolesMas : role){
//            rolesSet.add(roleService.findRoleByName(rolesMas));
//        }
//        user.setRoles(rolesSet);
//        userService.save(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/user/edit/{id}")
//    public String edit(@PathVariable("id") long id, Model model){
//        User user = userService.getById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
//        return "/edit";
//    }
//
//    @PostMapping("/user/edit/{id}")
//    public String update(@ModelAttribute("user") User user,
//                         @RequestParam(value = "rolesMas") String[] role){
//        Set<Role> rolesSet = new HashSet<>();
//        for (String roles : role){
//            rolesSet.add(roleService.findRoleByName(roles));
//        }
//        user.setRoles(rolesSet);
//        userService.update(user);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/user/delete/{id}")
//    public String delete(@PathVariable("id") long id){
//        User user = userService.getById(id);
//        userService.delete(user);
//        return "redirect:/admin";
//    }
}

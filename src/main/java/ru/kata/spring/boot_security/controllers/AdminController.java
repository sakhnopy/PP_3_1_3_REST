package ru.kata.spring.boot_security.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.model.Role;
import ru.kata.spring.boot_security.model.User;
import ru.kata.spring.boot_security.service.RoleService;
import ru.kata.spring.boot_security.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/users")
    public String admin(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/users";
    }
    @GetMapping("/new")
    public String newUser(@AuthenticationPrincipal User user,  Model model) {
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/new";
    }
    @PostMapping("/add")
    public String addUser(@RequestParam(value = "rolesId", required = false) String rolesId, @ModelAttribute("user") User user) {
        user.setRoles(roleService.findRoleById(rolesId));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @PutMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesId", required = false) String rolesId) {
        user.setRoles(roleService.findRoleById(rolesId));
        userService.updateUser(user);
        return "redirect:/admin/users";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/user")
    public String printUser(ModelMap model, @ModelAttribute User user) {
        model.addAttribute("AllRoles", roleService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        return "user/user";
    }
}


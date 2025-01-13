package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list"; // Thymeleaf view
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "users/add"; // Thymeleaf view
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/add";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{email}")
    public String showEditForm(@PathVariable String email, Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/edit"; // Thymeleaf view
    }

    @PostMapping("/edit/{email}")
    public String editUser(@PathVariable String email, @Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        user.setEmail(email);
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{email}")
    public String deleteUser(@PathVariable String email) {
        userService.delete(email);
        return "&quot;redirect:/users";
    }
}



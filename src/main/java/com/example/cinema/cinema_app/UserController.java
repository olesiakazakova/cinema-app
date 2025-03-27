package com.example.cinema.cinema_app;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "session/listUsers";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "session/addUser";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно добавлен!");
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String showEditUserForm(@RequestParam String email, Model model) {
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "session/editUser";
    }

    @PostMapping("/edit")
    @Transactional
    public String editUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно обновлен!");
        return "redirect:/users";
    }

    @PostMapping("/delete")
    @Transactional
    public String deleteUser(@RequestParam String email, RedirectAttributes redirectAttributes) {
        userRepository.deleteByEmail(email);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален!");
        return "redirect:/users";
    }
}

package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userForHeader") // Добавляем атрибут userForHeader в модель для каждого запроса
    public User userForHeader(Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            return userService.findByUsername(username);
        }
        return null;
    }


    @GetMapping("/api/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.allUsers();
    }


    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("listRoles", userService.listRoles());
        model.addAttribute("newUser", new User());
        return "all-users";
    }


    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newUser", newUser);
            model.addAttribute("bindingResult", bindingResult);

            return "all-users";
        }
        userService.saveUser(newUser);

        return "redirect:/admin";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

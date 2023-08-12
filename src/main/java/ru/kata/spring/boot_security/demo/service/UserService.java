package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User findUserById(Long userId);

    List<User> allUsers();

    void saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Long userId);

    public List<Role> listRoles();

    public User findByUsername(String username);

    public User getUserByUsername(String name);
}

package com.cms.service;

import com.cms.model.User;
import com.cms.store.DataStore;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final DataStore store = DataStore.getInstance();

    /** Register a new user. Returns null and sets error message on failure. */
    public String register(String name, String email, String password, String role) {
        if (name == null || name.isBlank())     return "Name is required.";
        if (email == null || email.isBlank())   return "Email is required.";
        if (password == null || password.length() < 6) return "Password must be at least 6 characters.";

        if (store.findUserByEmail(email).isPresent()) return "Email already registered.";

        User.Role r;
        try { r = User.Role.valueOf(role.toUpperCase()); }
        catch (Exception e) { r = User.Role.CUSTOMER; }

        User user = new User(store.nextUserId(), name.trim(), email.trim(), password, r);
        store.saveUser(user);
        return null; // null = success
    }

    /** Returns the user on success, null on failure. */
    public User login(String email, String password) {
        return store.findUserByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

    public Optional<User> findById(int id)   { return store.findUserById(id); }
    public List<User>     getAll()           { return store.getAllUsers(); }
    public List<User>     getStaffList()     { return store.getUsersByRole(User.Role.STAFF); }
}

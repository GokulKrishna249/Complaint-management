package com.cms.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

    public enum Role { CUSTOMER, STAFF, ADMIN }

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    private int id;
    private String name;
    private String email;
    private String password;   // plain text — fine for academic project
    private Role   role;
    private LocalDateTime createdAt;

    public User() {}

    public User(int id, String name, String email, String password, Role role) {
        this.id        = id;
        this.name      = name;
        this.email     = email;
        this.password  = password;
        this.role      = role;
        this.createdAt = LocalDateTime.now();
    }

    // ── getters / setters ─────────────────────────────────────────────────────
    public int    getId()       { return id; }
    public void   setId(int id) { this.id = id; }

    public String getName()            { return name; }
    public void   setName(String name) { this.name = name; }

    public String getEmail()             { return email; }
    public void   setEmail(String email) { this.email = email; }

    public String getPassword()                { return password; }
    public void   setPassword(String password) { this.password = password; }

    public Role getRole()          { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getCreatedAt()                    { return createdAt; }
    public void          setCreatedAt(LocalDateTime t)     { this.createdAt = t; }

    public String getCreatedAtFormatted() {
        return createdAt != null ? createdAt.format(FMT) : "";
    }
}

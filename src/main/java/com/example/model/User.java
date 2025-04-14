package com.example.model;

public class User {
    private int userId;
    private String username;
    private String password; // Correct capitalization
    private String email;
    private String role;
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getPassword() { // Correct capitalization
        return password;
    }

    public void setPassword(String password) { // Correct capitalization
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public User(int userId, String username, String password, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // You might also want a constructor without the userId for registration
    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // You can add more user details later (e.g., registration date)
}
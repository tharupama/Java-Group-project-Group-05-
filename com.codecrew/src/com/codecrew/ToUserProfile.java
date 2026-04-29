/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;

/**
 *
 * @author nipun
 */
public class ToUserProfile {
    private String userId;
    private String username;
    private String email;
    private String department;
    private long contact;
    private char[] password;

    public ToUserProfile(String userId, String username, String email, String department, long contact, char[] password) {
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setDepartment(department);
        setContact(contact);
        setPassword(password);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID is required.");
        }
        this.userId = userId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
        this.username = username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required.");
        }
        this.email = email.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department is required.");
        }
        this.department = department.trim();
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        String s = String.valueOf(contact);
        if (s.length() < 9 || s.length() > 12) {
            throw new IllegalArgumentException("Contact number is invalid.");
        }
        this.contact = contact;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        if (password == null || password.length < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        this.password = password.clone();
    }
}

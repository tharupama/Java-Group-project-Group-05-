package model;

import database.DBConnection;

public class Lecturer extends User {

    private String department;
    private String email;

    // Constructor
    public Lecturer(String id, String name, String department, String email) {
        super(id, name);  // calls User constructor
        this.department = department;
        this.email = email;
    }

    // Getters
    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Override from User
    @Override
    public void displayInfo() {
        System.out.println("Lecturer: " + getName());
        System.out.println("Department: " + department);
        System.out.println("Email: " + email);
    }
}

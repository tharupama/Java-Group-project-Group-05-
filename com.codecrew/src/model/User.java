package com.codecrew.model;

/**
 * User Model - FOT Student Management System
 */
public class User {
    private String uId;
    private String fname;
    private String lname;
    private String email;
    private String address;
    private String gender;
    private String dob;
    private String role;
    private String department;

    public User() {}

    public User(String uId, String fname, String lname, String email,
                String address, String gender, String dob, String role, String department) {
        this.uId = uId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.role = role;
        this.department = department;
    }

    public String getuId() { return uId; }
    public void setuId(String uId) { this.uId = uId; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getFullName() { return fname + " " + lname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return uId + " - " + fname + " " + lname + " (" + role + ")";
    }
}

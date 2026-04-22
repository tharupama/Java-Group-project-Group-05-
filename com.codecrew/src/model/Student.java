package com.codecrew.model;

/**
 * Student Model - FOT Student Management System
 */
public class Student {
    private String registrationNo;
    private String fname;
    private String lname;
    private int year;
    private String department;
    private int batch;

    public Student() {}

    public Student(String registrationNo, String fname, String lname, int year, String department, int batch) {
        this.registrationNo = registrationNo;
        this.fname = fname;
        this.lname = lname;
        this.year = year;
        this.department = department;
        this.batch = batch;
    }

    public String getRegistrationNo() { return registrationNo; }
    public void setRegistrationNo(String registrationNo) { this.registrationNo = registrationNo; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getFullName() { return fname + " " + lname; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getBatch() { return batch; }
    public void setBatch(int batch) { this.batch = batch; }

    @Override
    public String toString() {
        return registrationNo + " - " + fname + " " + lname;
    }
}

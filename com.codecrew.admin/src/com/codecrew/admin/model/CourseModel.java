/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.model;

/**
 *
 * @author USER
 */
public class CourseModel {
    private String code;
    private String name;
    private String type;
    private int credit;
    private String lecName;
    private int year;
    private String semester;
    private String department;
    private int theoryHours;
    private int practicalHours;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getLecName() {
        return lecName;
    }

    public void setLecName(String lecName) {
        this.lecName = lecName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getTheoryHours() {
        return theoryHours;
    }

    public void setTheoryHours(int theoryHours) {
        this.theoryHours = theoryHours;
    }

    public int getPracticalHours() {
        return practicalHours;
    }

    public void setPracticalHours(int practicalHours) {
        this.practicalHours = practicalHours;
    }

    public CourseModel() {
    }

    public CourseModel(String code, String name, String type, int credit, String lecName, int year, String semester, String department, int theoryHours, int practicalHours) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.credit = credit;
        this.lecName = lecName;
        this.year = year;
        this.semester = semester;
        this.department = department;
        this.theoryHours = theoryHours;
        this.practicalHours = practicalHours;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.exception;

/**
 *
 * @author USER
 */
public class CourseCodeNotFoundException extends RuntimeException{
    private final String courseCode;
    
    public CourseCodeNotFoundException(String courseCode){
        super(courseCode+" not found in database!");
        this.courseCode = courseCode;
    }
    
}

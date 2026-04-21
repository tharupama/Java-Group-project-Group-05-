/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.model;

import com.codecrew.admin.enums.Day;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author USER
 */
public class TimeTableModel {
    private String courseCode;
    private String type;
    private java.util.Date date;
    private Day day;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private String venue;

    public TimeTableModel(String courseCode, String type, Date date, Day day, LocalTime timeFrom, LocalTime timeTo, String venue) {
        this.courseCode = courseCode;
        this.type = type;
        this.date = date;
        this.day = day;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.venue = venue;
    }

    public TimeTableModel() {
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
    
    

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.model;

import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author USER
 */
public class NoticeModel {
    private String type;
    private String title;
    private String downloadLink;
    private String content;
    
    private String courseId;
    private java.util.Date date;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NoticeModel(String type, String title, String downloadLink, String content, String courseId, Date date, LocalTime timeFrom, LocalTime timeTo, int id) {
        this.type = type;
        this.title = title;
        this.downloadLink = downloadLink;
        this.content = content;
        this.courseId = courseId;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.id = id;
    }

    public NoticeModel(String type, String title, String downloadLink, String content) {
        this.type = type;
        this.title = title;
        this.downloadLink = downloadLink;
        this.content = content;
    }
    
    public NoticeModel(String type, String title, String downloadLink, String content, int id) {
        this.type = type;
        this.title = title;
        this.downloadLink = downloadLink;
        this.content = content;
        this.id = id;
    }

    public NoticeModel(String type, String title, String downloadLink, String content, String courseId, java.util.Date date, LocalTime timeFrom, LocalTime timeTo) {
        this.type = type;
        this.title = title;
        this.downloadLink = downloadLink;
        this.content = content;
        this.courseId = courseId;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
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

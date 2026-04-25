/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.view;

/**
 *
 * @author USER
 */
public class StudentProfileEncapsulated {
    private String contact;
    private String email;
    private byte[] profileImg;
    private String id;

    public StudentProfileEncapsulated(String contact, String email, byte[] profileImg, String id) {
        this.contact = contact;
        this.email = email;
        this.profileImg = profileImg;
        this.id = id;
    }

    public StudentProfileEncapsulated() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    
}

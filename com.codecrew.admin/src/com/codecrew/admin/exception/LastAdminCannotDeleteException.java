/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew.admin.exception;

/**
 *
 * @author USER
 */
public class LastAdminCannotDeleteException extends RuntimeException{

    public LastAdminCannotDeleteException() {
        super("Last admin cannot delete!");
    }
    
    
}

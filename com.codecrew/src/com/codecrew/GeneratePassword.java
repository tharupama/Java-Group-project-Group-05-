package com.codecrew;
import org.mindrot.jbcrypt.BCrypt;

public class GeneratePassword {
    public static void main(String[] args) {
        String hash = BCrypt.hashpw("1234", BCrypt.gensalt());
        System.out.println(hash);
    }
}
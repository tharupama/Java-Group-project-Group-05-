package com.codecrew;

public class LecturerSession {

    private static String userId;
    private static String userName;
    private static String email;
    private static String department;

    private LecturerSession() {
    }

    public static void setSession(String uId, String uName, String uEmail, String uDepartment) {
        userId = uId;
        userName = uName;
        email = uEmail;
        department = uDepartment;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getDepartment() {
        return department;
    }

    public static boolean hasSession() {
        return userId != null && !userId.trim().isEmpty();
    }

    public static void clear() {
        userId = null;
        userName = null;
        email = null;
        department = null;
    }
}

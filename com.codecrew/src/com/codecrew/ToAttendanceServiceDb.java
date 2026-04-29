/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codecrew;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author nipun
 */
public class ToAttendanceServiceDb implements ToAttendanceService {

@Override
public void addAttendance(
    String studentId,
    String courseCode,
    int weekNo,
    int sessionNo,
    String sessionDate,
    String sessionType,
    String status
) throws SQLException {

    try (Connection con = ToConnect.getConnection()) {
        if (con == null) {
            throw new SQLException("Database connection failed");
        }

        int sessionId = findOrCreateSession(con, courseCode, weekNo, sessionNo, sessionDate, sessionType);

        try (PreparedStatement pst = con.prepareStatement(
                "INSERT INTO toattendance (ST_Id, Session_Id, Status) VALUES (?, ?, ?)")) {
            pst.setString(1, studentId);
            pst.setInt(2, sessionId);
            pst.setString(3, status);
            pst.executeUpdate();
        }
    }
}

private int findOrCreateSession(
    Connection con,
    String courseCode,
    int weekNo,
    int sessionNo,
    String sessionDate,
    String sessionType
) throws SQLException {

    try (PreparedStatement checkSession = con.prepareStatement(
            "SELECT Session_Id FROM session WHERE Course_code=? AND Week_Number=? AND Session_Number=? AND Session_Date=? AND Session_Type=?")) {

        checkSession.setString(1, courseCode);
        checkSession.setInt(2, weekNo);
        checkSession.setInt(3, sessionNo);
        checkSession.setString(4, sessionDate);
        checkSession.setString(5, sessionType);

        try (ResultSet rs = checkSession.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("Session_Id");
            }
        }
    }

    try (PreparedStatement createSession = con.prepareStatement(
            "INSERT INTO session (Course_code, Week_Number, Session_Number, Session_Date, Session_Type) VALUES (?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {

        createSession.setString(1, courseCode);
        createSession.setInt(2, weekNo);
        createSession.setInt(3, sessionNo);
        createSession.setString(4, sessionDate);
        createSession.setString(5, sessionType);
        createSession.executeUpdate();

        try (ResultSet rs = createSession.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }

    throw new SQLException("Failed to create session record");
}
}

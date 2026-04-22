package com.codecrew.model;

/**
 * Attendance Model - FOT Student Management System
 */
public class Attendance {
    private String attendanceId;
    private String stId;
    private String courseCode;
    private String sessionDate;
    private int weekNumber;
    private String sessionType;
    private String status;

    public Attendance() {}

    public Attendance(String attendanceId, String stId, String courseCode,
                      String sessionDate, int weekNumber, String sessionType, String status) {
        this.attendanceId = attendanceId;
        this.stId = stId;
        this.courseCode = courseCode;
        this.sessionDate = sessionDate;
        this.weekNumber = weekNumber;
        this.sessionType = sessionType;
        this.status = status;
    }

    public String getAttendanceId() { return attendanceId; }
    public void setAttendanceId(String attendanceId) { this.attendanceId = attendanceId; }

    public String getStId() { return stId; }
    public void setStId(String stId) { this.stId = stId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getSessionDate() { return sessionDate; }
    public void setSessionDate(String sessionDate) { this.sessionDate = sessionDate; }

    public int getWeekNumber() { return weekNumber; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

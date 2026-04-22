package com.codecrew.model;

/**
 * StudentResult Model - FOT Student Management System
 */
public class StudentResult {
    private int resultId;
    private String studentId;
    private String courseCode;
    private String semester;
    private double caMarks;
    private double finalMarks;
    private String finalGrade;
    private String resultStatus;
    private double sgpa;
    private double cgpa;

    public StudentResult() {}

    public StudentResult(int resultId, String studentId, String courseCode,
                         String semester, double caMarks, double finalMarks,
                         String finalGrade, String resultStatus, double sgpa, double cgpa) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
        this.caMarks = caMarks;
        this.finalMarks = finalMarks;
        this.finalGrade = finalGrade;
        this.resultStatus = resultStatus;
        this.sgpa = sgpa;
        this.cgpa = cgpa;
    }

    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public double getCaMarks() { return caMarks; }
    public void setCaMarks(double caMarks) { this.caMarks = caMarks; }

    public double getFinalMarks() { return finalMarks; }
    public void setFinalMarks(double finalMarks) { this.finalMarks = finalMarks; }

    public String getFinalGrade() { return finalGrade; }
    public void setFinalGrade(String finalGrade) { this.finalGrade = finalGrade; }

    public String getResultStatus() { return resultStatus; }
    public void setResultStatus(String resultStatus) { this.resultStatus = resultStatus; }

    public double getSgpa() { return sgpa; }
    public void setSgpa(double sgpa) { this.sgpa = sgpa; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
}

package com.codecrew.model;

/**
 * CourseUnit Model - FOT Student Management System
 */
public class CourseUnit {
    private String courseCode;
    private String name;
    private String type;
    private int credit;
    private String lecName;
    private int year;
    private String semester;
    private String departmentOffering;
    private int theoryHours;
    private int practicalHours;

    public CourseUnit() {}

    public CourseUnit(String courseCode, String name, String type, int credit,
                      String lecName, int year, String semester,
                      String departmentOffering, int theoryHours, int practicalHours) {
        this.courseCode = courseCode;
        this.name = name;
        this.type = type;
        this.credit = credit;
        this.lecName = lecName;
        this.year = year;
        this.semester = semester;
        this.departmentOffering = departmentOffering;
        this.theoryHours = theoryHours;
        this.practicalHours = practicalHours;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

    public String getLecName() { return lecName; }
    public void setLecName(String lecName) { this.lecName = lecName; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getDepartmentOffering() { return departmentOffering; }
    public void setDepartmentOffering(String departmentOffering) { this.departmentOffering = departmentOffering; }

    public int getTheoryHours() { return theoryHours; }
    public void setTheoryHours(int theoryHours) { this.theoryHours = theoryHours; }

    public int getPracticalHours() { return practicalHours; }
    public void setPracticalHours(int practicalHours) { this.practicalHours = practicalHours; }

    @Override
    public String toString() {
        return courseCode + " - " + name;
    }
}

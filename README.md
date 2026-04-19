# Java-Group-project-Group-05-
CREATE DATABASE mis;

CREATE user 'admin'@'localhost' IDENTIFIED BY 'admin123';

GRANT ALL PRIVILEGES ON mis.* TO 'admin'@'localhost';

CREATE TABLE user (
    U_Id VARCHAR(15) NOT NULL,
    Uname VARCHAR(45),
    Contact BIGINT,
    Email VARCHAR(45),
    Password VARCHAR(255),
    Role VARCHAR(50),
    Department VARCHAR(100),
    PRIMARY KEY (U_Id)
);


INSERT INTO user (U_Id, Uname, Contact, Email, Password, Role, Department)
VALUES ('TO/2023/001', 'nipuna', 0771234567, 'nipuna@gmail.com', '123', 'To', 'IT'),('TG/2023/0001', 'deshan', 077123456, 'deshan@gmail.com', '1234', 'student', 'IT');



////////////////////////////////////////////////////////////////////////////////////////////////////////



CREATE TABLE course_unit (
    Course_code VARCHAR(15) NOT NULL PRIMARY KEY,
    Name VARCHAR(100) DEFAULT NULL,
    Type ENUM('Theory', 'Practical', 'Both') NOT NULL,
    Credit INT NOT NULL,
    Lec_Name VARCHAR(45) DEFAULT NULL,
    Year INT DEFAULT NULL,
    Semester VARCHAR(20) DEFAULT NULL,
    Department_Offering ENUM('ICT', 'ET', 'BST') DEFAULT 'ICT',
    Theory_Hours INT DEFAULT 15,
    Practical_Hours INT DEFAULT 15
);


ALTER TABLE course_unit
drop column Year;


INSERT INTO course_unit 
(Course_code, Name, Type, Credit, Lec_Name, Semester, Department_Offering, Theory_Hours, Practical_Hours)
VALUES
('ICT2122', 'Object Oriented Programming', 'Theory', 2, 'P.H.P. Nuwan Laksir', 'Semester 1', 'ICT', 30, 0);



////////////////////////////////////////////////////////////////////////////////////////////////////////




CREATE TABLE session (
    Session_Id INT AUTO_INCREMENT PRIMARY KEY,
    Course_code VARCHAR(15) NOT NULL,
    Week_Number INT NOT NULL CHECK (Week_Number BETWEEN 1 AND 15),
    Session_Number INT NOT NULL,
    Session_Date DATE NOT NULL,
    Session_Type ENUM('Theory','Practical') NOT NULL,

    FOREIGN KEY (Course_code) REFERENCES course_unit(Course_code)
);


INSERT INTO session 
(Course_code, Week_Number, Session_Number, Session_Date, Session_Type)
VALUES 
('ICT2122', 1, 1, '2026-04-19', 'Theory');


////////////////////////////////////////////////////////////////////////////////////////////////////////



CREATE TABLE attendance (
    Attendance_Id INT AUTO_INCREMENT PRIMARY KEY,
    ST_Id VARCHAR(20) NOT NULL,
    Session_Id INT NOT NULL,
    Status ENUM('Present','Absent','Medical') DEFAULT 'Present',

    FOREIGN KEY (ST_Id) REFERENCES user(U_Id),
    FOREIGN KEY (Session_Id) REFERENCES session(Session_Id)
);



INSERT INTO attendance (ST_Id, Session_Id, Status)
VALUES (
    'TG/2023/0001',
    (SELECT Session_Id FROM session 
     WHERE Course_code = 'ICT2122' 
     AND Week_Number = 1 
     AND Session_Number = 1),
    'Present'
);



////////////////////////////////////////////////////////////////////////////////////////////////////////



CREATE TABLE medical_Record (
    Record_Id INT AUTO_INCREMENT PRIMARY KEY,

    ST_Id VARCHAR(20) NOT NULL,
    Course_code VARCHAR(15) NOT NULL,

    Session_Id INT NULL,

    Request_Type ENUM('Attendance','CA','Final_Exam','Quiz') NOT NULL,

    Date_Submit DATE NOT NULL,
   
    Status ENUM('Approved','Rejected') DEFAULT 'Approved',

    Approved_By VARCHAR(20) NULL,

    Approved_Date DATE NULL,

    FOREIGN KEY (ST_Id) REFERENCES user(U_Id),
    FOREIGN KEY (Course_code) REFERENCES course_unit(Course_code),
    FOREIGN KEY (Session_Id) REFERENCES session(Session_Id)
);



INSERT INTO medical_Record 
(ST_Id, Course_code, Session_Id, Request_Type, Date_Submit, Status, Approved_By, Approved_Date)
VALUES 
('TG/2023/0001', 'ICT2122', 1, 'Attendance', '2026-04-19', 'Approved', 'ADMIN01', '2026-04-20');

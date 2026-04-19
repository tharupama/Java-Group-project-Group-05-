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


*************************************************************************************************


CREATE TABLE student (
  Registration_No VARCHAR(20) NOT NULL PRIMARY KEY,
  Fname VARCHAR(45),
  Lname VARCHAR(45),
  Year INT,
  Department VARCHAR(45),
  Batch INT DEFAULT 2024,
  Status ENUM('Proper','Repeat','Suspended') NOT NULL DEFAULT 'Proper'
);



***********************************************************************************************

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


******************************************************************************************************


CREATE TABLE attendance (
     Attendance_Id VARCHAR(20) NOT NULL PRIMARY KEY,
     ST_Id VARCHAR(20) NOT NULL,
     Course_code VARCHAR(15) NOT NULL,
     Session_Date DATE NOT NULL,
     Week_Number INT NOT NULL,
     Session_Number INT NOT NULL,
     Session_Type ENUM('Theory','Practical','Lab') NOT NULL,
     Status ENUM('Present','Absent','Medical') DEFAULT 'Present'
 );

 ALTER TABLE attendance
         ADD CONSTRAINT fk_attendance_student
             FOREIGN KEY (ST_Id) REFERENCES student(Registration_No)
             ON UPDATE CASCADE ON DELETE CASCADE,
         ADD CONSTRAINT fk_attendance_course
             FOREIGN KEY (Course_code) REFERENCES course_unit(Course_code)
             ON UPDATE CASCADE ON DELETE RESTRICT;


UPDATE attendance SET session_type = "Practical" WHERE session_type = "Lab";


***************************************************************************************************

CREATE TABLE medical_record (
  Medical_Id VARCHAR(10) NOT NULL PRIMARY KEY,
  ST_Id VARCHAR(20) NOT NULL,
  Course_code VARCHAR(15) NOT NULL,
  Date_Submit DATE NOT NULL,
  Valid_From DATE NOT NULL,
  Valid_To DATE NOT NULL,
  Medical_Type ENUM('Attendance','CA','Final_Exam','Quiz') NOT NULL,
  Approve TINYINT(1) DEFAULT 1,
  Approved_By VARCHAR(20) NOT NULL  
);
ALTER TABLE medical_record
    ADD CONSTRAINT fk_medical_student 
        FOREIGN KEY (ST_Id) REFERENCES student(Registration_No)
        ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE medical_record
    ADD CONSTRAINT fk_medical_course 
        FOREIGN KEY (Course_code) REFERENCES course_unit(Course_code)
        ON UPDATE CASCADE ON DELETE RESTRICT;




ALTER TABLE medical_record
ADD CONSTRAINT Checking_approve_status_valid_or_not
CHECK (
    (Approve = 0 AND Approved_By IS NULL)
    OR
    (Approve = 1 AND Approved_By IS NOT NULL)
);


ALTER TABLE medical_record
MODIFY Approved_By VARCHAR(20) NULL;

**************************************************************************************

INSERT INTO user (U_Id, Uname, Contact, Email, Password, Role, Department)
VALUES ('001', 'nipuna', 0771234567, 'nipuna@gmail.com', '123', 'To', 'IT');

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





////////////////////////////////////////////////////////////////////////////////////////////////////////



CREATE TABLE attendance (
    Attendance_Id INT AUTO_INCREMENT PRIMARY KEY,
    ST_Id VARCHAR(20) NOT NULL,
    Session_Id INT NOT NULL,
    Status ENUM('Present','Absent','Medical') DEFAULT 'Present',

    FOREIGN KEY (ST_Id) REFERENCES user(U_Id),
    FOREIGN KEY (Session_Id) REFERENCES session(Session_Id)
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


////////////////////////////////////////////////////////////////////////////



INSERT INTO time_table (Course_code, Type, date, Day, time_from, time_to, venue) VALUES

-- MONDAY (Morning)
('BST1212', 'LECTURE', '2026-01-01', 'MONDAY', '08:00:00', '10:00:00', 'NBLLT'),
('ICT1222', 'LECTURE', '2026-01-01', 'MONDAY', '08:00:00', '10:00:00', 'lab11'),
('TMS1242', 'LECTURE', '2026-01-01', 'MONDAY', '08:00:00', '10:00:00', 'lab500'),

-- MONDAY (Afternoon)
('BST1262', 'LECTURE', '2026-01-01', 'MONDAY', '13:00:00', '15:00:00', '7001'),
('ICT1233', 'LECTURE', '2026-01-01', 'MONDAY', '13:00:00', '15:00:00', 'lab12'),

-- TUESDAY (Morning)
('BST1272', 'LECTURE', '2026-01-01', 'TUESDAY', '08:00:00', '10:00:00', 'NBLLT'),
('ICT1242', 'LECTURE', '2026-01-01', 'TUESDAY', '08:00:00', '10:00:00', 'lab11'),

-- TUESDAY (Afternoon)
('BST1222', 'LECTURE', '2026-01-01', 'TUESDAY', '13:00:00', '15:00:00', 'NBLLT'),
('ICT1253', 'LECTURE', '2026-01-01', 'TUESDAY', '13:00:00', '15:00:00', 'lab12'),
('TMS1253', 'LECTURE', '2026-01-01', 'TUESDAY', '13:00:00', '15:00:00', 'lab100'),

-- WEDNESDAY (Morning)
('BST1232', 'LECTURE', '2026-01-01', 'WEDNESDAY', '08:00:00', '10:00:00', '7001'),
('ICT2122', 'LECTURE', '2026-01-01', 'WEDNESDAY', '08:00:00', '10:00:00', 'lab11'),

-- WEDNESDAY (Afternoon)
('ENG1222', 'LECTURE', '2026-01-01', 'WEDNESDAY', '13:00:00', '15:00:00', 'lab500'),
('TCS1212', 'LECTURE', '2026-01-01', 'WEDNESDAY', '13:00:00', '15:00:00', 'lab500'),
('TMS1261', 'LECTURE', '2026-01-01', 'WEDNESDAY', '13:00:00', '15:00:00', 'lab100'),

-- THURSDAY (Morning)
('ICT1212', 'LECTURE', '2026-01-01', 'THURSDAY', '08:00:00', '10:00:00', 'lab12'),
('TMS1213', 'LECTURE', '2026-01-01', 'THURSDAY', '08:00:00', '10:00:00', 'lab500'),

-- THURSDAY (Afternoon)
('BST1242', 'LECTURE', '2026-01-01', 'THURSDAY', '13:00:00', '15:00:00', '7001'),
('TMS1222', 'LECTURE', '2026-01-01', 'THURSDAY', '13:00:00', '15:00:00', 'lab100'),

-- FRIDAY (Morning)
('BST1253', 'LECTURE', '2026-01-01', 'FRIDAY', '08:00:00', '10:00:00', 'NBLLT'),
('TMS1231', 'LECTURE', '2026-01-01', 'FRIDAY', '08:00:00', '10:00:00', 'lab500'),

-- FRIDAY (Afternoon)
('ENT1212', 'LECTURE', '2026-01-01', 'FRIDAY', '13:00:00', '15:00:00', 'lab251'),
('TMS1233', 'LECTURE', '2026-01-01', 'FRIDAY', '13:00:00', '15:00:00', 'lab100');




///////////////////////////////////////////////////////////////////////////////////


DELIMITER $$

CREATE PROCEDURE generate_week_sessions(IN p_week INT)
BEGIN
    DECLARE base_date DATE;

    SET base_date = DATE_ADD('2026-01-01', INTERVAL (p_week - 1) * 7 DAY);

    SET @rownum := 0;

    INSERT INTO session (
        Course_code,
        Week_Number,
        Session_Number,
        Session_Date,
        Session_Type
    )

    SELECT 
        t.Course_code,
        p_week,
        (@rownum := @rownum + 1) + ((p_week - 1) * 23),

        CASE t.Day
            WHEN 'MONDAY' THEN DATE_ADD(base_date, INTERVAL 0 DAY)
            WHEN 'TUESDAY' THEN DATE_ADD(base_date, INTERVAL 1 DAY)
            WHEN 'WEDNESDAY' THEN DATE_ADD(base_date, INTERVAL 2 DAY)
            WHEN 'THURSDAY' THEN DATE_ADD(base_date, INTERVAL 3 DAY)
            WHEN 'FRIDAY' THEN DATE_ADD(base_date, INTERVAL 4 DAY)
        END,

        -- ✔ FIXED LOGIC USING ONLY YOUR EXISTING DATA
        CASE 
            WHEN t.venue LIKE 'lab%' THEN 'Practical'
            ELSE 'Theory'
        END

    FROM time_table t;

END$$

DELIMITER ;





CALL generate_week_sessions(1);
CALL generate_week_sessions(2);
CALL generate_week_sessions(3);
CALL generate_week_sessions(4);
CALL generate_week_sessions(5);
CALL generate_week_sessions(6);
CALL generate_week_sessions(7);
CALL generate_week_sessions(8);
CALL generate_week_sessions(9);
CALL generate_week_sessions(10);
CALL generate_week_sessions(11);
CALL generate_week_sessions(12);
CALL generate_week_sessions(13);
CALL generate_week_sessions(14);
CALL generate_week_sessions(15);


//////////////////////////////////////////////////////////////////////////////////////




INSERT INTO attendance (ST_Id, Session_Id, Status)
SELECT 
    u.U_Id,
    s.Session_Id,
    'Present'
FROM user u
JOIN session s
WHERE u.Role = 'Student'
AND s.Week_Number BETWEEN 1 AND 15;





WITH StudentScenario AS (
    -- Assign each student a random scenario (1-5)
    SELECT 
        ST_Id,
        FLOOR(1 + RAND() * 5) AS scenario_id
    FROM (SELECT DISTINCT ST_Id FROM attendance WHERE Status = 'Present') AS students
),
RankedRecords AS (
    -- Randomly rank each student's attendance records
    SELECT 
        a.ST_Id,
        a.Session_Id,
        s.scenario_id,
        ROW_NUMBER() OVER (PARTITION BY a.ST_Id ORDER BY RAND()) AS rn
    FROM attendance a
    JOIN StudentScenario s ON a.ST_Id = s.ST_Id
    WHERE a.Status = 'Present'
)
UPDATE attendance att
JOIN RankedRecords rr ON att.ST_Id = rr.ST_Id AND att.Session_Id = rr.Session_Id
SET att.Status = CASE
    -- Scenario 1: >80% attendance (13-15 Present), 0 Medical → Keep all Present
    WHEN rr.scenario_id = 1 THEN 'Present'
    
    -- Scenario 2: Exactly 80% attendance (12 Present), 0 Medical → 3 Absent
    WHEN rr.scenario_id = 2 AND rr.rn <= 3 THEN 'Absent'
    
    -- Scenario 3: <80% attendance (≤11 Present), 0 Medical → 5 Absent
    WHEN rr.scenario_id = 3 AND rr.rn <= 5 THEN 'Absent'
    
    -- Scenario 4: >80% attendance (13-15 Present), WITH Medicals → 2 Medical
    WHEN rr.scenario_id = 4 AND rr.rn <= 2 THEN 'Medical'
    
    -- Scenario 5: <80% attendance (≤11 Present), WITH Medicals → 2 Medical + 3 Absent
    WHEN rr.scenario_id = 5 AND rr.rn <= 2 THEN 'Medical'
    WHEN rr.scenario_id = 5 AND rr.rn BETWEEN 3 AND 5 THEN 'Absent'
    
    ELSE att.Status
END;






-- 🔁 Auto-select a random student with enough 'Present' records to modify
SET @TargetStudent = (
    SELECT ST_Id 
    FROM attendance 
    WHERE Status = 'Present' 
    GROUP BY ST_Id 
    HAVING COUNT(*) >= 70 
    ORDER BY RAND() 
    LIMIT 1
);

-- Update 70 records: 2 Medical + 68 Absent → drops attendance to 79.7%
UPDATE attendance a
JOIN (
    SELECT ST_Id, Session_Id, 
           ROW_NUMBER() OVER (ORDER BY RAND()) AS rn
    FROM attendance
    WHERE ST_Id = @TargetStudent AND Status = 'Present'
) AS ranked
ON a.ST_Id = ranked.ST_Id AND a.Session_Id = ranked.Session_Id
SET a.Status = CASE
    WHEN ranked.rn <= 2 THEN 'Medical'   -- Max 2 Medical
    WHEN ranked.rn <= 70 THEN 'Absent'   -- 68 Absent + 2 Medical = 70 changed
END
WHERE ranked.rn <= 70;

-- Show which student was updated
SELECT @TargetStudent AS Updated_Student_ID;





SELECT 
    ST_Id,
    COUNT(*) AS TotalSessions,
    SUM(Status = 'Present') AS PresentCount,
    SUM(Status = 'Absent') AS AbsentCount,
    SUM(Status = 'Medical') AS MedicalCount,
    ROUND(SUM(Status = 'Present') / COUNT(*) * 100, 1) AS AttendancePct,
    CASE 
        WHEN SUM(Status = 'Present') / COUNT(*) > 0.8 AND SUM(Status = 'Medical') = 0 THEN '>80% No Medical'
        WHEN ROUND(SUM(Status = 'Present') / COUNT(*) * 100) = 80 AND SUM(Status = 'Medical') = 0 THEN '=80% No Medical'
        WHEN SUM(Status = 'Present') / COUNT(*) < 0.8 AND SUM(Status = 'Medical') = 0 THEN '<80% No Medical'
        WHEN SUM(Status = 'Present') / COUNT(*) > 0.8 AND SUM(Status = 'Medical') > 0 THEN '>80% With Medical'
        WHEN SUM(Status = 'Present') / COUNT(*) < 0.8 AND SUM(Status = 'Medical') > 0 THEN '<80% With Medical'
    END AS Scenario
FROM attendance
GROUP BY ST_Id
ORDER BY ST_Id;


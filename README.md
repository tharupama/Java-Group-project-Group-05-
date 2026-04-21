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








INSERT INTO user (U_Id, Uname, Contact, Email, Password, Role, Department, image_data)
VALUES
('admin/2023/001', 'Nipuna Deshan', NULL, 'nipuna.deshan@ruhuna.lk', '1234', 'Admin', 'ICT', NULL),
('dean FOT', 'Sahan Bandara', NULL, 'sahan.bandara@ruhuna.lk', '1234', 'Dean', 'BST', NULL),

('lc/2023/002', 'Dilini Wickramasinghe', NULL, 'dilini.wickramasinghe@ruhuna.lk', '1234', 'Lecturer', 'BST', NULL),
('lc/2023/003', 'Yasodha Gunaratne', NULL, 'yasodha.gunaratne@ruhuna.lk', '1234', 'Lecturer', 'BST', NULL),
('lc/2023/004', 'Seelawathi Ganga', NULL, 'seelawathi.ganga@ruhuna.lk', '1234', 'Lecturer', 'BST', NULL),
('lc/2023/005', 'Mahesh Ranasinghe', NULL, 'mahesh.ranasinghe@ruhuna.lk', '1234', 'Lecturer', 'ICT', NULL),
('lc/2023/006', 'Nirmala Herath', NULL, 'nirmala.herath@ruhuna.lk', '1234', 'Lecturer', 'BST', NULL),
('lc/2024/001', 'Ravi Silva', NULL, 'ravi.silva@ruhuna.lk', '1234', 'Lecturer', 'ICT', NULL),

('tg/2023/0002', 'Kamani Fernando', NULL, 'kamani.fernando@ruhuna.lk', '1234', 'Student', 'ICT', NULL),
('tg/2023/0003', 'Tharindu Jayasinghe', NULL, 'tharindu.jayasinghe@ruhuna.lk', '1234', 'Student', 'ICT', NULL),
('tg/2023/0004', 'Anjali Rajapaksa', NULL, 'anjali.rajapaksa@ruhuna.lk', '1234', 'Student', 'ICT', NULL),
('tg/2023/0005', 'Chaminda Weerasinghe', NULL, 'chaminda.weerasinghe@ruhuna.lk', '1234', 'Student', 'ICT', NULL),
('tg/2023/0006', 'Nadeesha Herath', NULL, 'nadeesha.herath@ruhuna.lk', '1234', 'Student', 'ICT', NULL),
('tg/2023/0007', 'Dinuka Amarasinghe', NULL, 'dinuka.amarasinghe@ruhuna.lk', '1234', 'Student', 'ET', NULL),
('tg/2023/0008', 'Ishara Gunawardena', NULL, 'ishara.gunawardena@ruhuna.lk', '1234', 'Student', 'ET', NULL),
('tg/2023/0009', 'Rukshan Mendis', NULL, 'rukshan.mendis@ruhuna.lk', '1234', 'Student', 'ET', NULL),
('tg/2023/0010', 'Shanika Wijesinghe', NULL, 'shanika.wijesinghe@ruhuna.lk', '1234', 'Student', 'ET', NULL),
('tg/2023/0011', 'Kavindu Rathnayake', NULL, 'kavindu.rathnayake@ruhuna.lk', '1234', 'Student', 'ET', NULL),
('tg/2023/0012', 'Piumi De Silva', NULL, 'piumi.desilva@ruhuna.lk', '1234', 'Student', 'BST', NULL),
('tg/2023/0013', 'Sachin Kumara', NULL, 'sachin.kumara@ruhuna.lk', '1234', 'Student', 'BST', NULL),
('tg/2023/0014', 'Thilini Peries', NULL, 'thilini.peries@ruhuna.lk', '1234', 'Student', 'BST', NULL),
('tg/2023/0015', 'Yohan Edirisinghe', NULL, 'yohan.edirisinghe@ruhuna.lk', '1234', 'Student', 'BST', NULL),
('tg/2024/0001', 'Nimal Perera', NULL, 'nimal.perera@ruhuna.lk', '1234', 'Student', 'ICT', NULL),

('to/2023/001', 'Amila Rathnayake', NULL, 'amila.rathnayake@ruhuna.lk', '1234', 'Technical Officer', 'ET', NULL),
('to/2023/002', 'Lahiru De Silva', NULL, 'lahiru.desilva@ruhuna.lk', '1234', 'Technical Officer', 'ICT', NULL),
('to/2023/003', 'Chathura Fernando', NULL, 'chathura.fernando@ruhuna.lk', '1234', 'Technical Officer', 'ICT', NULL),
('to/2023/004', 'Sanduni Jayawardena', NULL, 'sanduni.jayawardena@ruhuna.lk', '1234', 'Technical Officer', 'BST', NULL),
('to/2023/005', 'Kasun Madushanka', NULL, 'kasun.madushanka@ruhuna.lk', '1234', 'Technical Officer', 'BST', NULL),
('to/2023/006', 'Nipuni Abeywickrama', NULL, 'nipuni.abeywickrama@ruhuna.lk', '1234', 'Technical Officer', 'BST', NULL);







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
    Session_Type ENUM('Theory','Practical') NOT NULL
);





////////////////////////////////////////////////////////////////////////////////////////////////////////



CREATE TABLE toattendance (
    Attendance_Id INT AUTO_INCREMENT PRIMARY KEY,
    ST_Id VARCHAR(20) NOT NULL,
    Session_Id INT NOT NULL,
    Status ENUM('Present','Absent','Medical') DEFAULT 'Present',

    FOREIGN KEY (ST_Id) REFERENCES user(U_Id),
    FOREIGN KEY (Session_Id) REFERENCES session(Session_Id)
);







////////////////////////////////////////////////////////////////////////////////////////////////////////



CREATE TABLE tomedical_record (
    Record_Id INT NOT NULL AUTO_INCREMENT,
    ST_Id VARCHAR(20) NOT NULL,
    Course_code VARCHAR(15) NOT NULL,
    Session_Id INT DEFAULT NULL,
    Request_Type ENUM('Attendance', 'CA', 'Final_Exam', 'Quiz') NOT NULL,
    Date_Submit DATE NOT NULL,
    Reason TEXT NOT NULL,
    Status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    Approved_By VARCHAR(20) DEFAULT NULL,
    Approved_Date DATE DEFAULT NULL,
    PRIMARY KEY (Record_Id),
    INDEX (ST_Id),
    INDEX (Course_code),
    INDEX (Session_Id)
);






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




INSERT INTO toattendance (ST_Id, Session_Id, Status)
SELECT 
    u.U_Id,
    s.Session_Id,
    'Present'
FROM user u
JOIN session s
WHERE u.Role = 'Student'
AND s.Week_Number BETWEEN 1 AND 15;




//////////////////////////////////////////////////////////////////////////////////////////////////////













WITH StudentScenario AS (
    -- Assign each student a random scenario (1-5)
    SELECT 
        ST_Id,
        FLOOR(1 + RAND() * 5) AS scenario_id
    FROM (SELECT DISTINCT ST_Id FROM toattendance WHERE Status = 'Present') AS students
),
RankedRecords AS (
    -- Randomly rank each student's attendance records
    SELECT 
        a.ST_Id,
        a.Session_Id,
        s.scenario_id,
        ROW_NUMBER() OVER (PARTITION BY a.ST_Id ORDER BY RAND()) AS rn
    FROM toattendance a
    JOIN StudentScenario s ON a.ST_Id = s.ST_Id
    WHERE a.Status = 'Present'
)
UPDATE toattendance att
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
    FROM toattendance 
    WHERE Status = 'Present' 
    GROUP BY ST_Id 
    HAVING COUNT(*) >= 70 
    ORDER BY RAND() 
    LIMIT 1
);

-- Update 70 records: 2 Medical + 68 Absent → drops attendance to 79.7%
UPDATE toattendance a
JOIN (
    SELECT ST_Id, Session_Id, 
           ROW_NUMBER() OVER (ORDER BY RAND()) AS rn
    FROM toattendance
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
        WHEN SUM(Status = 'Present') / COUNT(*) > 0.8 AND SUM(Status = 'Medical') = 0 
            THEN '>80% No Medical'

        WHEN ROUND(SUM(Status = 'Present') / COUNT(*) * 100) = 80 AND SUM(Status = 'Medical') = 0 
            THEN '=80% No Medical'

        WHEN SUM(Status = 'Present') / COUNT(*) < 0.8 AND SUM(Status = 'Medical') = 0 
            THEN '<80% No Medical'

        WHEN SUM(Status = 'Present') / COUNT(*) > 0.8 AND SUM(Status = 'Medical') > 0 
            THEN '>80% With Medical'

        WHEN SUM(Status = 'Present') / COUNT(*) < 0.8 AND SUM(Status = 'Medical') > 0 
            THEN '<80% With Medical'
    END AS Scenario

FROM toattendance 
GROUP BY ST_Id 
ORDER BY ST_Id;








///////////////////////////////////////////////////////////////////////////////////////////////////////







INSERT INTO tomedical_record (
    ST_Id,
    Course_code,
    Session_Id,
    Request_Type,
    Date_Submit,
    Reason,
    Status,
    Approved_By,
    Approved_Date
)
SELECT 
    a.ST_Id,
    s.Course_code,
    a.Session_Id,
    'Attendance' AS Request_Type,

    DATE_ADD(s.Session_Date, INTERVAL 1 DAY) AS Date_Submit,

    'Medical leave submitted' AS Reason,

    'Approved' AS Status,
    'Chathura Fernando' AS Approved_By,

    DATE_ADD(s.Session_Date, INTERVAL 2 DAY) AS Approved_Date

FROM toattendance a
JOIN session s ON a.Session_Id = s.Session_Id
WHERE a.Status = 'Medical';





///////////////////////// create view /////////////////////////////////////////







CREATE VIEW attendance_summary AS
SELECT 
    a.ST_Id,
    s.Course_Code,
    
    ROUND(SUM(a.Status = 'Present') / COUNT(*) * 100, 1) AS Eligibility_Percentage,

    CASE 
        WHEN SUM(a.Status = 'Present') / COUNT(*) >= 0.8 
        THEN 'Eligible'
        ELSE 'Not Eligible'
    END AS Eligibility_Status

FROM toattendance a
JOIN session s ON a.Session_Id = s.Session_Id

GROUP BY a.ST_Id, s.Course_Code;






SELECT * FROM attendance_summary;







/////////////////////////////////////////////////////////////////////////////////////////////////////////////

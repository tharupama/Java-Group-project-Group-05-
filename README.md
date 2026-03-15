# Java-Group-project-Group-05-
CREATE DATABASE mis;

CREATE user 'admin'@'localhost' IDENTIFIED BY 'admin123';

GRANT ALL PRIVILEGES ON mis.* TO 'admin'@'localhost';

CREATE TABLE account(id VARCHAR(20) PRIMARY KEY, name VARCHAR(50) NOT NULL, contact INT, email VARCHAR(30), password VARCHAR(255), role ENUM('Lecturer','Student','Technical officer','Admin'));

ALTER TABLE account ADD COLUMN department ENUM('ICT', 'ET', 'BST', 'MULTIDISCIPLINARY');
////////
ALTER TABLE user DROP COLUMN Lname;

ALTER TABLE user RENAME COLUMN Fname TO Uname;

ALTER TABLE user DROP COLUMN Gender;
ALTER TABLE user DROP COLUMN Dob;
ALTER TABLE user DROP COLUMN Address;

ALTER TABLE user ADD COLUMN Contact INT;
ALTER TABLE user ADD COLUMN Password VARCHAR(255);
ALTER TABLE user MODIFY COLUMN Contact INT AFTER Uname;
ALTER TABLE user MODIFY COLUMN Password VARCHAR(255) AFTER Email;

mysql> CREATE TABLE notice(type VARCHAR(10), title VARCHAR(255), download_link VARCHAR(255), content VARCHAR(255), course_id VARCHAR(15), date DATE, time_from TIME, time_to TIME);

ALTER TABLE notice ADD notice_id INT AUTO_INCREMENT PRIMARY KEY;

 ALTER TABLE notice MODIFY COLUMN notice_id INT FIRST;
 ALTER TABLE notice MODIFY notice_id INT AUTO_INCREMENT;
 ALTER TABLE notice RENAME COLUMN date TO exam_date;
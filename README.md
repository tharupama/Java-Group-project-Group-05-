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
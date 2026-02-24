# Java-Group-project-Group-05-
CREATE DATABASE mis;

CREATE user 'admin'@'localhost' IDENTIFIED BY 'admin123';

GRANT ALL PRIVILEGES ON mis.* TO 'admin'@'localhost';

CREATE TABLE account(id VARCHAR(20) PRIMARY KEY, name VARCHAR(50) NOT NULL, contact INT, email VARCHAR(30), password VARCHAR(255), role ENUM('Lecturer','Student','Technical officer','Admin'));

ALTER TABLE account ADD COLUMN department ENUM('ICT', 'ET', 'BST', 'MULTIDISCIPLINARY');

ALTER TABLE user DROP COLUMN Lname;
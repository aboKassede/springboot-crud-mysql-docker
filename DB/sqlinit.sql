CREATE DATABASE IF NOT EXISTS persons;
USE persons;

CREATE TABLE IF NOT EXISTS person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

INSERT INTO person (name, email) VALUES
('Mahmoud Kassedy', 'mahmod@example.com'),
('John Doe', 'john.doe@example.com'),
('Sara Cohen', 'sara.cohen@example.com');
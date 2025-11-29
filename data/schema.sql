DROP DATABASE IF EXISTS DB614;
CREATE DATABASE DB614;
USE DB614;

CREATE TABLE Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('customer', 'agent', 'admin') NOT NULL DEFAULT 'customer'
);

CREATE TABLE Flights (
    id INT PRIMARY KEY AUTO_INCREMENT,
    airline VARCHAR(100) NOT NULL,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure DATETIME NOT NULL,
    arrival DATETIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    seats_available INT NOT NULL
);

CREATE TABLE Bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    flight_id INT,
    seat_number VARCHAR(10) NOT NULL,
    booking_date DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Users(id),
    FOREIGN KEY (flight_id) REFERENCES Flights(id)
);

CREATE TABLE Payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    booking_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date DATETIME NOT NULL,
    method VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Users(id),
    FOREIGN KEY (booking_id) REFERENCES Bookings(id)
);

CREATE TABLE MonthlyNews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    publish_date DATETIME NOT NULL
);
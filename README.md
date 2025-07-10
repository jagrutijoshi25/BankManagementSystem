#  Bank Account Management System

The **Bank Account Management System** is a console-based Java application built to simulate core banking operations like registration, login, deposit, withdrawal, and balance checking. It uses PostgreSQL for persistent storage, 
JDBC for database interaction, and multithreading to simulate real-time transactions. The system follows core Java principles such as inheritance, interfaces, file handling, and is structured using Maven.

## Features
View all registered customers
- View all transactions performed
- Search for customer details by email
- Register and login securely
- Deposit amount (writes to database and updates balance)
- Withdraw money with balance check
- View current account balance
- View transaction history (uses file + DB)
- Slip generation using file handling (optional)
- Multithreaded transaction simulation (seamless to user)

## Technologies Used
- Java (Core)
- JDBC (Java Database Connectivity)
- PostgreSQL
- Maven
- Object-Oriented Programming (Abstraction, Inheritance, Interface)
- Multithreading
- File Handling

## Database Schema
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'customer',
    balance DOUBLE PRECISION DEFAULT 0
)
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL,
    amount DOUBLE PRECISION NOT NULL
);

## Contact
Name:Jagruti Joshi
Email:jjagruti714@gmail.com

## Output Screenshot
<img width="567" height="307" alt="Screenshot (40)" src="https://github.com/user-attachments/assets/9da26532-fa2b-49be-90b9-6ab8a75be2d6" />



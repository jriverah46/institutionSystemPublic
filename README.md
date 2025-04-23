# 🎓 Institution System - Microservices Architecture

This project is a simulation of an academic institution system built using a **microservices architecture** with **Spring Boot** and **Spring Cloud**.

---

## 🧩 Microservices Overview

### 🔐 Authentication Service
Handles user registration, login, role management.

### 📚 Course Service
Manages course creation, updates, deletions, and listing.

### 🧾 Enrollment Service
Allows students to enroll in courses, considering payment status and course availability.

### 📝 Notes Service
Enables teachers to assign grades to students enrolled in courses.

### 🌐 Gateway Service
Acts as the single entry point to the system, routing requests to the appropriate services and handling authentication.

### 📡 Eureka Service
A service registry that allows services to register and discover each other dynamically.

### ⚙️ Config Service
Centralized configuration management for all services using Spring Cloud Config.

---

## 🚀 Technologies Used

- Java 17
- Spring Boot
- Spring Cloud (Eureka, Gateway, Config)
- Spring Security
- PostgreSQL 
- Maven

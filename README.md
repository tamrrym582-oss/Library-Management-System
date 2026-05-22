# Library-Management-System
# 📖 Modern Library Management System

A production-ready Desktop Application for managing library ecosystem operations, built with a modern user experience using **Java Swing** and **Java 2D (Graphics2D)**. 

This project integrates robust Object-Oriented Software Design with a comprehensive Relational Database Schema to deliver an all-in-one system blueprint.

---

## 🚀 Key Software Features

- **Dynamic Authentication:** Role-based access control distinguishing privileges between Admins, Librarians, and Members.
- **Sleek UX Design:** Fully customized, dark-themed UI components inspired by Tailwind CSS tokens (Slate/Amber palette).
- **Micro-Interactions:** Includes advanced UI feedback like visual window shake animations on unauthorized login attempts.
- **Data Collections Simulator:** In-memory tracking of books dynamic availability, active borrowing records, and fine evaluation.

---

## 🗄️ Database Architecture & Engineering

The backend schema represents a fully normalized relational database engine consisting of **8 strongly coupled entity tables** designed to maintain high data integrity:

- **Membership & Administration:** `Reader` & `Staff` trackers.
- **Catalog Management:** `Book` inventory linked with `Publisher` details.
- **Core Operations:** Comprehensive logging for book `Borrow` events.
- **Financial Auditing:** `Fine` computation records alongside `Payment` receipts processed by authorized staff.
- **Quality Control:** Dynamic asset evaluation via internal damage/loss `Reports`.

*Note: The standalone DDL/DML script configuration is fully documented under the `/database` directory.*

---

## 🛠️ Built With
- **Language:** Java Core (SE)
- **Framework:** Java Swing UI & Java 2D Graphic Engine
- **Database Engine:** MySQL Relational Dialect

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
## 🗄️ Database Design (Course Module 2)

> ⚠️ **Important Note on Data Consistency:** > The dataset injected into the MySQL schema consists entirely of **mock/fictional data** tailored specifically for a separate database course module. 
> This SQL script serves as an independent database architecture blueprint and is **not connected to the active Java GUI runtime layer** (which relies on automated memory collections).

The conceptual database schema includes 8 entity tables designed to showcase a scalable relational structure:
- **Membership & Administration:** `Reader` & `Staff` trackers.
- **Catalog Management:** `Book` inventory linked with `Publisher` details.
- **Core Operations:** Comprehensive logging for book `Borrow` events.
- **Financial Auditing:** `Fine` computation records alongside `Payment` receipts processed by authorized staff.
- **Quality Control:** Dynamic asset evaluation via internal damage/loss `Reports`.

---

## 🛠️ Built With
- **Language:** Java Core (SE)
- **Framework:** Java Swing UI & Java 2D Graphic Engine
- **Database Engine:** MySQL Relational Dialect

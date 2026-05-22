# Library-Management-System
# 📖 Modern Library Management System & Enterprise Infrastructure

A production-ready Desktop Application designed for library ecosystem operations, built with a modern user experience using **Java Swing** and **Java 2D (Graphics2D)**. 

This comprehensive repository integrates robust Object-Oriented Software Design, a fully normalized Relational Database Schema, and an Enterprise Network Topology to deliver a complete, multi-layered system blueprint.

---

## 🚀 Key Software Features (Module 1)

- **Dynamic Authentication:** Role-based access control separating views and privileges dynamically between Admins, Librarians, and Members.
- **Sleek UX Design:** Fully customized, dark-themed UI components inspired by Tailwind CSS design tokens (Slate/Amber palette).
- **Micro-Interactions:** Includes advanced visual feedback, such as window shake animations on unauthorized login attempts.
- **Data Collections Simulator:** In-memory tracking of book dynamic availability, active borrowing records, and fine evaluation.

---

## 🗄️ Database Architecture & Engineering 

> ⚠️ **Important Note on Data Consistency:**
> The dataset injected into the MySQL schema consists entirely of **mock/fictional data** tailored specifically for a separate database course module. 
> This SQL script serves as an independent database architecture blueprint and is **not connected to the active Java GUI runtime layer** (which relies on automated memory collections).

The conceptual database schema includes 8 entity tables designed to showcase a scalable relational structure:
- **Membership & Administration:** `Reader` & `Staff` trackers.
- **Catalog Management:** `Book` inventory linked with `Publisher` details.
- **Core Operations:** Comprehensive logging for book `Borrow` events.
- **Financial Auditing:** `Fine` computation records alongside `Payment` receipts processed by authorized staff.
- **Quality Control:** Dynamic asset evaluation via internal damage/loss `Reports`.

*The full SQL initialization script can be found inside the `/database` folder.*
---

## 🛠️ Built With
- **Language:** Java Core (SE)
- **Framework:** Java Swing UI & Java 2D Graphic Engine
- **Database Engine:** MySQL Relational Dialect

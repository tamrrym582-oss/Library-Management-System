-- ==========================================================
-- Project: Library Management System
-- Team Member: Reem Tamer 250100323
-- Fares mohamed 2050104330
-- mohamed ibrahim 250102989
-- ==========================================================
 
CREATE DATABASE IF NOT EXISTS Library_Management_System;
USE Library_Management_System;


CREATE TABLE Reader (
    reader_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone_num VARCHAR(20)
);

CREATE TABLE Staff (
    staff_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- جدول الناشرين [cite: 256]
CREATE TABLE Publisher (
    publisher_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_num VARCHAR(20)
);

CREATE TABLE Book (
    book_id INT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    category VARCHAR(50),
    price DECIMAL(10,2) CHECK (price > 0),
    publisher_id INT,
    FOREIGN KEY (publisher_id) REFERENCES Publisher(publisher_id)
);

CREATE TABLE Borrow (
    borrow_id INT PRIMARY KEY,
    reader_id INT,
    book_id INT,
    borrow_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (reader_id) REFERENCES Reader(reader_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

CREATE TABLE Fine (
    fine_id INT PRIMARY KEY,
    borrow_id INT,
    amount DECIMAL(10,2) CHECK (amount >= 0),
    fine_date DATE,
    FOREIGN KEY (borrow_id) REFERENCES Borrow(borrow_id)
);

CREATE TABLE Payment (
    pay_id INT PRIMARY KEY,
    fine_id INT,
    staff_id INT,
    total DECIMAL(10,2) CHECK (total >= 0),
    FOREIGN KEY (fine_id) REFERENCES Fine(fine_id),
    FOREIGN KEY (staff_id) REFERENCES Staff(staff_id)
);

CREATE TABLE Reports (
    report_id INT PRIMARY KEY,
    reader_id INT,
    book_id INT,
    issue VARCHAR(255),
    report_date DATE NOT NULL,
    FOREIGN KEY (reader_id) REFERENCES Reader(reader_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

INSERT INTO Reader VALUES
(1,'Ahmed Ali','ahmed@gmail.com','01011111111'),
(2,'Sara Mohamed','sara@gmail.com','01022222222'),
(3,'Omar Tarek','omar@gmail.com','01033333333'),
(4,'Nour Ahmed','nour@gmail.com','01044444444'),
(5,'Khaled Samir','khaled@gmail.com','01055555555'),
(6,'Laila Hassan','laila@gmail.com','01066666666'),
(7,'Yousef Adel','yousef@gmail.com','01077777777'),
(8,'Hana Mostafa','hana@gmail.com','01088888888'),
(9,'Tamer Fathy','tamer@gmail.com','01099999999'),
(10,'Salma Nabil','salma@gmail.com','01012121212');


INSERT INTO Publisher VALUES
(1,'Dar El Shorouk','022345678'), 
(2,'Modern Library','023456789'),
(3,'Nahdet Misr','024567890'), 
(4,'Egyptian Book Organization','025678901'), 
(5,'Oxford Press','026789012');

INSERT INTO Staff VALUES
(1,'Mona Adel'), (2,'Ali Hassan'), (3,'Hassan Mahmoud'), (4,'Nadia El-Sayed'), (5,'Ahmed Khaled');

INSERT INTO Book VALUES
(1,'Database Systems','Education',250,1), (2,'Java Programming','Programming',350,2),
(3,'Python Basics','Programming',200,2), (4,'Operating Systems','Education',280,1),
(5,'Web Development','Programming',320,2), (6,'AI Fundamentals','Technology',400,3),
(7,'Machine Learning','Technology',450,3), (8,'C++ Programming','Programming',290,2),
(9,'Data Structures','Education',270,1), (10,'Software Engineering','Education',310,3),
(11,'JavaScript Guide','Programming',250,2), (12,'Cyber Security','Technology',380,4),
(13,'Cloud Computing','Technology',420,5), (14,'Mobile App Dev','Programming',340,2),
(15,'Discrete Math','Education',220,4);

INSERT INTO Borrow VALUES
(1,1,1,'2026-04-01','2026-04-10'), (2,2,2,'2026-04-05','2026-04-15'),
(3,3,3,'2026-04-07','2026-04-20'), (4,4,4,'2026-04-10','2026-04-20'),
(5,5,5,'2026-04-11','2026-04-21'), (6,6,6,'2026-04-12','2026-04-22'),
(7,7,7,'2026-04-13','2026-04-23'), (8,8,8,'2026-04-14','2026-04-24'),
(9,1,4,'2026-04-15','2026-04-25'), (10,2,5,'2026-04-16','2026-04-26'),
(11,3,6,'2026-04-17','2026-04-27'), (12,4,7,'2026-04-18','2026-04-28'),
(13,5,8,'2026-04-19','2026-04-29'), (14,6,9,'2026-04-20','2026-04-30'),
(15,7,10,'2026-04-21','2026-05-01'), (16,8,11,'2026-04-22','2026-05-02'),
(17,9,12,'2026-04-23','2026-05-03'), (18,10,13,'2026-04-24','2026-05-04');

INSERT INTO Fine VALUES
(1,1,50,'2026-04-12'), (2,2,30,'2026-04-18'), (3,3,40,'2026-04-25'),
(4,4,60,'2026-04-26'), (5,5,20,'2026-04-27'), (6,9,15,'2026-04-28'), (7,10,25,'2026-04-29');

INSERT INTO Payment VALUES
(1,1,1,50), (2,2,2,30), (3,3,3,40), (4,4,4,60), (5,5,5,20), (6,6,3,15), (7,7,4,25);

INSERT INTO Reports VALUES
(1,1,1,'Book damaged','2026-04-11'), (2,2,2,'Missing pages','2026-04-16');


INSERT INTO Reader VALUES (11,'Maged Samy','maged@gmail.com','01000000000');

UPDATE Book SET price = 360 WHERE book_id = 2;

DELETE FROM Reports WHERE report_id = 2;

SELECT * FROM Book WHERE category = 'Programming';

SELECT r.name, b.title, br.borrow_date
FROM Borrow br
JOIN Reader r ON br.reader_id = r.reader_id
JOIN Book b ON br.book_id = b.book_id;

SELECT r.name, br.borrow_id
FROM Reader r
LEFT JOIN Borrow br ON r.reader_id = br.reader_id
WHERE br.borrow_id IS NULL;

SELECT category, COUNT(*) as book_count
FROM Book
GROUP BY category
HAVING COUNT(*) > 2;

SELECT title, price FROM Book ORDER BY price DESC;

SELECT SUM(total) AS total_revenue FROM Payment;

SELECT name FROM Reader
WHERE reader_id IN (SELECT reader_id FROM Borrow br JOIN Fine f ON br.borrow_id = f.borrow_id);

SELECT r.name, SUM(p.total) AS total_paid
FROM Reader r
JOIN Borrow br ON r.reader_id = br.reader_id
JOIN Fine f ON br.borrow_id = f.borrow_id
JOIN Payment p ON f.fine_id = p.fine_id
GROUP BY r.name
ORDER BY total_paid DESC;
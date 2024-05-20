CREATE DATABASE QLSV;

USE QLSV;

CREATE TABLE SinhVien (
    MaSV INT PRIMARY KEY,
    HoTen VARCHAR(100),
    Lop VARCHAR(10),
    DiemTB FLOAT
);

INSERT INTO SinhVien (MaSV, HoTen, Lop, DiemTB) VALUES
(21520714, 'Trinh Tan Dat', 'MMTT', 10),
(21520715, 'Le Huynh Chau', 'CNTT', 9),
(21520716, 'Pham Van Dong', 'CNPM', 9.5);

CREATE TABLE Lop (
    MaLop INT PRIMARY KEY,
    TenLop VARCHAR(50),
    CVHT VARCHAR(100)
);

INSERT INTO Lop (MaLop, TenLop, CVHT) VALUES
(1, 'MMTT', 'Tran Kim Dung'),
(2, 'CNTT', 'Le Thi Bich Ngoc'),
(3, 'CNPM', 'Tran Van Thanh');

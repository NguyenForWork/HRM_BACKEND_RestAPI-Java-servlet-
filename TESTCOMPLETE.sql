CREATE DATABASE TESTCOMPLETE;

CREATE TABLE Payroll (
    PayrollID CHAR(7) PRIMARY KEY,
    MonthYear DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    TotalHoursWorked INT,
    TotalSalary DECIMAL(18,2),
    SalaryStatus NVARCHAR(50) NOT NULL
);

CREATE TRIGGER trg_InsertPayroll
ON Payroll
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO Payroll (PayrollID, MonthYear, TotalHoursWorked, TotalSalary, SalaryStatus)
    SELECT RIGHT('0' + CAST(MONTH(ISNULL(MonthYear, GETDATE())) AS VARCHAR(2)), 2) + '/' + CAST(YEAR(ISNULL(MonthYear, GETDATE())) AS CHAR(4)),
           ISNULL(MonthYear, GETDATE()), TotalHoursWorked, TotalSalary, SalaryStatus
    FROM inserted;
END;

CREATE TABLE Attendance (
    AttendanceID INT IDENTITY(1,1) PRIMARY KEY,
    AttendanceDate DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    StartTime TIME(0) NOT NULL,
    EndTime TIME(0) NOT NULL,
    TotalHours DECIMAL(5, 2),
    PayrollID CHAR(7),
    EmployeeID INT,
    CONSTRAINT FK_Attendance_Payroll FOREIGN KEY (PayrollID) REFERENCES Payroll(PayrollID),
    CONSTRAINT FK_Attendance_Employee FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

DROP TRIGGER trg_InsertAttendance;


CREATE TRIGGER trg_InsertAttendance
ON Attendance
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @AttendanceDate DATE;
    DECLARE @MonthYear DATE;
    DECLARE @PayrollID CHAR(7);
    DECLARE @EmployeeID INT;

    -- Get information from inserted
    SELECT @AttendanceDate = ISNULL(AttendanceDate, GETDATE())
    FROM inserted;

    -- Create MonthYear as the first day of the month from AttendanceDate
    SET @MonthYear = DATEFROMPARTS(YEAR(@AttendanceDate), MONTH(@AttendanceDate), 1);
    SET @PayrollID = RIGHT('0' + CAST(MONTH(@MonthYear) AS VARCHAR(2)), 2) + '/' + CAST(YEAR(@MonthYear) AS CHAR(4));

    -- Check if payroll record already exists for this month/year
    IF NOT EXISTS (SELECT 1 FROM Payroll WHERE MonthYear = @MonthYear)
    BEGIN
        -- Create new payroll record if it does not exist
        INSERT INTO Payroll (PayrollID, MonthYear, TotalHoursWorked, TotalSalary, SalaryStatus, EmployeeID)
        VALUES (@PayrollID, @MonthYear, 0, 0.00, 'Not Calculated', @EmployeeID);
    END

    -- Calculate total hours worked and total salary
    INSERT INTO Attendance (AttendanceDate, StartTime, EndTime, TotalHours, PayrollID, EmployeeID)
    SELECT AttendanceDate, StartTime, EndTime,
           DATEDIFF(MINUTE, StartTime, EndTime) / 60.0, @PayrollID, EmployeeID
    FROM inserted;

    -- Update total hours worked in payroll
    UPDATE Payroll
    SET TotalHoursWorked = TotalHoursWorked + (SELECT SUM(DATEDIFF(MINUTE, i.StartTime, i.EndTime) / 60.0) FROM inserted i WHERE Payroll.PayrollID = @PayrollID),
        TotalSalary = TotalSalary + (SELECT SUM((DATEDIFF(MINUTE, i.StartTime, i.EndTime) / 60.0) * e.Salary) FROM inserted i INNER JOIN Employee e ON i.EmployeeID = e.EmployeeID WHERE Payroll.PayrollID = @PayrollID),
        EmployeeID = (SELECT TOP 1 EmployeeID FROM inserted)
    WHERE MonthYear = @MonthYear;
END;


CREATE TABLE Department (
    DepartmentID INT IDENTITY(1,1) PRIMARY KEY,
    DepartmentName NVARCHAR(100) NOT NULL,
    CreatedDate DATE NOT NULL DEFAULT GETDATE()
);

CREATE TABLE Position (
    PositionID INT IDENTITY(1,1) PRIMARY KEY,
    PositionName NVARCHAR(100) NOT NULL,
    DepartmentID INT,
    Salary DECIMAL(18,2),
    CreatedDate DATE NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_Position_Department FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID)
);

CREATE TABLE Benefit (
    BenefitID INT IDENTITY(1,1) PRIMARY KEY,
    BenefitName NVARCHAR(100) NOT NULL,
    BenefitCost DECIMAL(18,2) NOT NULL
);

CREATE TABLE Employee (
    EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Gender NVARCHAR(10),
    Address NVARCHAR(255),
    PhoneNumber NVARCHAR(20),
    Email NVARCHAR(100),
    DepartmentID INT,
    PositionID INT,
    BenefitID INT,
    StartDate DATE NOT NULL,
    EndDate DATE,
    Salary DECIMAL(18,2),
    Image VARBINARY(MAX),
    CONSTRAINT FK_Employee_Department FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID),
    CONSTRAINT FK_Employee_Position FOREIGN KEY (PositionID) REFERENCES Position(PositionID),
    CONSTRAINT FK_Employee_Benefit FOREIGN KEY (BenefitID) REFERENCES Benefit(BenefitID)
);

CREATE TRIGGER trg_UpdateSalary
ON Employee
AFTER INSERT, UPDATE
AS
BEGIN
    UPDATE e
    SET e.Salary = ISNULL(p.Salary, 0)
    FROM Employee e
    JOIN inserted i ON e.EmployeeID = i.EmployeeID
    LEFT JOIN Position p ON e.PositionID = p.PositionID
    WHERE e.EmployeeID = i.EmployeeID;
END;
	
DROP TRIGGER trg_UpdateSalary;





-- Insert Departments
INSERT INTO Department (DepartmentName) VALUES
(N'Phòng Kế toán'),
(N'Phòng Nhân sự'),
(N'Phòng IT'),
(N'Phòng Marketing');

-- Insert Positions
INSERT INTO Position (PositionName, DepartmentID, Salary) VALUES
(N'Kế toán trưởng', 13, 15000),
(N'Nhân viên Kế toán', 13, 10000),
(N'Trưởng phòng Nhân sự', 14, 14000),
(N'Nhân viên Nhân sự', 14, 90000),
(N'Trưởng phòng IT', 15, 16000),
(N'Lập trình viên', 15, 12000),
(N'Trưởng phòng Marketing', 16, 15000),
(N'Nhân viên Marketing', 16, 10000);

-- Insert Benefits
INSERT INTO Benefit (BenefitName, BenefitCost) VALUES
(N'Bảo hiểm y tế', 200000),
(N'Bảo hiểm xã hội', 300000),
(N'Trợ cấp đi lại', 500000);

-- Insert Employees
INSERT INTO Employee (Name, DateOfBirth, Gender, Address, PhoneNumber, Email, DepartmentID, PositionID, BenefitID, StartDate) VALUES
(N'Nguyễn Văn A', '1985-05-20', N'Nam', N'123 Lý Thường Kiệt, Hà Nội', N'0912345678', N'nguyenvana@example.com', 13, 12, 4, '2010-01-15'),
(N'Trần Thị B', '1990-08-10', N'Nữ', N'456 Trần Phú, TP.HCM', N'0918765432', N'tranthib@example.com', 14, 14, 5, '2012-03-22'),
(N'Phạm Văn C', '1987-11-30', N'Nam', N'789 Nguyễn Trãi, Đà Nẵng', N'0987654321', N'phamvanc@example.com', 15, 17, 6, '2015-07-01');



-- Insert Attendances
INSERT INTO Attendance (AttendanceDate, StartTime, EndTime, EmployeeID) VALUES
(GETDATE(), '08:00', '17:00',10);


SELECT e.Name, SUM(a.TotalHours) AS TotalHoursWorked
FROM Employee e
JOIN Attendance a ON e.EmployeeID = a.EmployeeID
WHERE e.EmployeeID = 10
GROUP BY e.Name;


SELECT e.Name, COUNT(DISTINCT a.AttendanceDate) AS TotalWorkingDays
FROM Employee e
JOIN Attendance a ON e.EmployeeID = a.EmployeeID
WHERE e.EmployeeID = 10
GROUP BY e.Name;

SELECT SUM(TotalHours) AS TongGioLam
FROM Attendance
WHERE EmployeeID = 10;



DELETE FROM Attendance;
DELETE FROM Payroll;
DELETE FROM Employee;
DELETE FROM Benefit;
DELETE FROM Position;
DELETE FROM Department;


 
SELECT * FROM Attendance;
SELECT * FROM Payroll;
SELECT * FROM Employee;
SELECT * FROM Benefit;
SELECT * FROM Position;
SELECT * FROM Department;


SELECT SUM(p.TotalSalary) AS TongLuong
FROM Payroll p
JOIN Employee e ON p.EmployeeID = e.EmployeeID
WHERE e.EmployeeID = 10
  AND MONTH(p.MonthYear) = 5
  AND YEAR(p.MonthYear) = YEAR(GETDATE());


  ALTER TABLE Payroll
ADD EmployeeID INT,
CONSTRAINT FK_Payroll_Employee FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID);



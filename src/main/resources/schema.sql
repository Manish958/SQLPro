
CREATE TABLE IF NOT EXISTS Employee(
Employee_ID int primary key,
Employee_Name char(30),
HireDate date,
JobTitle char(20),
Department char(20)
);

CREATE TABLE IF NOT EXISTS Employee_Account(
EmployeeID int REFERENCES employee(Employee_ID),
Salary double not null,
Bank char(10),
AccountNo char(20) not null
);



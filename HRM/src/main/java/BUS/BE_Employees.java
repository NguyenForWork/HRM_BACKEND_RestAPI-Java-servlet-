package BUS;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BE_Employees {
    private int employeeID;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;
    private int departmentID;
    private int positionID;
    private int benefitID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    private double salary;
    private byte[] image;

    // Constructors (omitted for brevity)
    
 // Non-parameterized constructor
    public BE_Employees() {
    }
    
    // Parameterized constructor
    public BE_Employees(int employeeID, String name, Date dateOfBirth, String gender, String address, String phoneNumber, String email, int departmentID, int positionID, int benefitID, Date startDate, Date endDate, double salary, byte[] image) {
        this.employeeID = employeeID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.departmentID = departmentID;
        this.positionID = positionID;
        this.benefitID = benefitID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.image = image;
    }
    
    
    // Parameterized constructor
    public BE_Employees( String name, Date dateOfBirth, String gender, String address, String phoneNumber, String email, int departmentID, int positionID, int benefitID, Date startDate, Date endDate, byte[] image) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.departmentID = departmentID;
        this.positionID = positionID;
        this.benefitID = benefitID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.image = image;
    }
    // Getters and Setters
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public int getBenefitID() {
        return benefitID;
    }

    public void setBenefitID(int benefitID) {
        this.benefitID = benefitID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    // toString method (omitted for brevity)
}

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import BUS.BE_Employees;

public class DAO_Employees extends DAO_connect implements interface_DAO<BE_Employees>{

    private static final String SELECT_ALL_EMPLOYEES = "SELECT EmployeeID, Name, DateOfBirth, Gender, Address, PhoneNumber, Email, DepartmentID, PositionID, BenefitID, StartDate, EndDate, Salary, Image FROM Employee";
    private static final String INSERT_EMPLOYEE = "INSERT INTO Employee (Name, DateOfBirth, Gender, Address, PhoneNumber, Email, DepartmentID, PositionID, BenefitID, StartDate, EndDate, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE Employee SET name = ?, dateOfBirth = ?, gender = ?, address = ?, phoneNumber = ?, email = ?, departmentID = ?, positionID = ?, benefitID = ?, startDate = ?, endDate = ?, image = ? WHERE employeeID = ?";
    private static final String DELETE_EMPLOYEE = "DELETE FROM Employee WHERE EmployeeID=?";
    
    // Method to close resources
    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<BE_Employees> getAllEmployees() throws ClassNotFoundException, SQLException {
        List<BE_Employees> employees = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BE_Employees employee = new BE_Employees();
                employee.setEmployeeID(resultSet.getInt("EmployeeID"));
                employee.setName(resultSet.getString("Name"));
                employee.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                employee.setGender(resultSet.getString("Gender"));
                employee.setAddress(resultSet.getString("Address"));
                employee.setPhoneNumber(resultSet.getString("PhoneNumber"));
                employee.setEmail(resultSet.getString("Email"));
                employee.setDepartmentID(resultSet.getInt("DepartmentID"));
                employee.setPositionID(resultSet.getInt("PositionID"));
                employee.setBenefitID(resultSet.getInt("BenefitID"));
                employee.setStartDate(resultSet.getDate("StartDate"));
                employee.setEndDate(resultSet.getDate("EndDate"));
                employee.setSalary(resultSet.getDouble("Salary"));
                employee.setImage(resultSet.getBytes("Image")); // Changed to getBytes() for image
                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return employees;
    }

    @Override
    public void addEmployee(BE_Employees employee) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(INSERT_EMPLOYEE);

            statement.setString(1, employee.getName());
            statement.setDate(2, employee.getDateOfBirth());
            statement.setString(3, employee.getGender());
            statement.setString(4, employee.getAddress());
            statement.setString(5, employee.getPhoneNumber());
            statement.setString(6, employee.getEmail());
            statement.setInt(7, employee.getDepartmentID());
            statement.setInt(8, employee.getPositionID());
            statement.setInt(9, employee.getBenefitID());
            statement.setDate(10, employee.getStartDate());
            statement.setDate(11, employee.getEndDate());
            statement.setBytes(12, employee.getImage()); 

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, null);
        }
    }

    @Override
    public void updateEmployee(BE_Employees employee) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(UPDATE_EMPLOYEE);

            statement.setString(1, employee.getName());
            statement.setDate(2, employee.getDateOfBirth());
            statement.setString(3, employee.getGender());
            statement.setString(4, employee.getAddress());
            statement.setString(5, employee.getPhoneNumber());
            statement.setString(6, employee.getEmail());
            statement.setInt(7, employee.getDepartmentID());
            statement.setInt(8, employee.getPositionID());
            statement.setInt(9, employee.getBenefitID());
            statement.setDate(10, employee.getStartDate());
            statement.setDate(11, employee.getEndDate());
            statement.setBytes(12, employee.getImage()); // Changed to setBytes() for image
            statement.setInt(13, employee.getEmployeeID());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, null);
        }
    }

    @Override
    public void deleteEmployee(int employeeID) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(DELETE_EMPLOYEE);
            statement.setInt(1, employeeID);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, null);
        }
    }

    public static void main(String[] args) {
        DAO_Employees daoEmployees = new DAO_Employees();
        
        try {
            // Get all employees
            List<BE_Employees> employees = daoEmployees.getAllEmployees();
            
            // Print information of all employees
            for (BE_Employees employee : employees) {
                System.out.println(employee.toString());
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}

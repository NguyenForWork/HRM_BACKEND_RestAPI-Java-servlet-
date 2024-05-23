package BUS;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

import DAO.interface_DAO;
import DAO.DAO_Employees;


public class BL_Employees implements interface_BUS<BE_Employees> {

    private interface_DAO<BE_Employees> employeesDAO = new DAO_Employees();

    @Override
    public Response getAllEmployees() {
        try {
            List<BE_Employees> employeesList = employeesDAO.getAllEmployees();
            return Response.ok(employeesList).build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching employees").build();
        }
    }

    @Override
    public Response addEmployees(BE_Employees employee) {
        try {
            employeesDAO.addEmployee(employee);
            return Response.ok().build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding employee").build();
        }
    }

    @Override
    public Response deleteEmployees(int id) {
        try {
            employeesDAO.deleteEmployee(id);
            return Response.ok().build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting employee").build();
        }
    }

    @Override
    public Response updateEmployees(int id, BE_Employees employee) {
        try {
            // Set the employee id from the path parameter
            employee.setEmployeeID(id);
            employeesDAO.updateEmployee(employee);
            return Response.ok().build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating employee").build();
        }
    }

    // Implement other methods as needed...
}

package RestAPI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import BUS.BE_Employees;
import BUS.BL_Employees;
import BUS.interface_BUS;

@Path("/employees")
public class API_Employees {

    private interface_BUS<BE_Employees> employeesBUS = new BL_Employees();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees() {
        return employeesBUS.getAllEmployees();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEmployee(BE_Employees requestBody) {
        return employeesBUS.addEmployees(requestBody);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        return employeesBUS.deleteEmployees(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int id, BE_Employees requestBody) {
        return employeesBUS.updateEmployees(id, requestBody);
    }
}

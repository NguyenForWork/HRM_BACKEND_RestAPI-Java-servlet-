package BUS;


import javax.ws.rs.core.Response;

public interface interface_BUS<Employees> {
	
//////////////////////////////////////// EMPLOYEES //////////////////////////////////////////////////////////
	
    // Phương thức để lấy tất cả các nhân viên từ cơ sở dữ liệu
    Response getAllEmployees();

    // Phương thức để thêm một nhân viên mới vào cơ sở dữ liệu
    Response addEmployees(Employees employee);

    // Phương thức để xóa một nhân viên từ cơ sở dữ liệu dựa trên ID
    Response deleteEmployees(int id);

    // Phương thức để cập nhật thông tin của một nhân viên trong cơ sở dữ liệu dựa trên ID
    Response updateEmployees(int id, Employees employee);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
}

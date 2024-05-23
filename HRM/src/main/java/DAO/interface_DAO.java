package DAO;

import java.sql.SQLException;
import java.util.List;

public interface interface_DAO<T> {
	
//////////////////////////////////////////// EMPLOYEES //////////////////////////////////////////////////////////
	
    // Phương thức để lấy tất cả các nhân viên từ cơ sở dữ liệu
    List<T> getAllEmployees() throws ClassNotFoundException, SQLException;

    // Phương thức để thêm một nhân viên mới vào cơ sở dữ liệu
    void addEmployee(T employee) throws ClassNotFoundException, SQLException;

    // Phương thức để cập nhật thông tin của một nhân viên trong cơ sở dữ liệu dựa trên ID
    void updateEmployee(T employee) throws ClassNotFoundException, SQLException;

    // Phương thức để xóa một nhân viên từ cơ sở dữ liệu dựa trên ID
    void deleteEmployee(int id) throws ClassNotFoundException, SQLException;
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}
    


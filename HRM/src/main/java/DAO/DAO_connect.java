package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO_connect {

    private static final String URL = "jdbc:sqlserver://LAPTOP-EKNIC6UN\\SQLEXPRESS:1433;databaseName=TESTCOMPLETE";
    private static final String USERNAME = "managebus";
    private static final String PASSWORD = "12345678";

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // Xử lý ngoại lệ kết nối
            e.printStackTrace();
            throw e;
        }

        return connection;
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Xử lý ngoại lệ khi đóng kết nối
                e.printStackTrace();
            }
        }
    }
//
//    // Test
//    public static void main(String[] args) throws ClassNotFoundException {
//        DAO_connect dao = new DAO_connect();
//
//        try {
//            Connection connection = dao.getConnection();
//            System.out.println("Kết nối thành công!");
//
//            // Thực hiện các thao tác với cơ sở dữ liệu ở đây (nếu cần)
//
//            // Sau khi hoàn thành, đóng kết nối
//            dao.closeConnection(connection);
//            System.out.println("Đã đóng kết nối.");
//        } catch (SQLException e) {
//            System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
//            e.printStackTrace();
//        }
//    }
}

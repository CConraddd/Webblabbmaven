package gruoppo.test.database;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbimpl{
    private static final String URL = "jdbc:mysql://localhost:3307/webshop?UseClientEnc=UTF8";
    private static final String USER = "root";
    private static final String PASSWORD = "Gaming123";
    private Connection connection;

    public void connect() throws SQLException{
        try {
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the database");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database", e);
        }
    }

    public void disconnect() throws SQLException{
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to disconnect from the database", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        dbimpl dbManager = new dbimpl();
        try {
            dbManager.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                dbManager.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

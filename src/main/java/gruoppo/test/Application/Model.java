package gruoppo.test.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Model {
    private Connection connection;

    public boolean connect(String database) throws Exception{
        database = "webshop_db";
        try {
            String connectionString = "jdbc:mysql://localhost:3306/" + database + "?user=root" + "&password=Gaming123";
            connection = DriverManager.getConnection(connectionString);
            connection.setAutoCommit(false);
            System.out.println("Connected to the database");
            return true;
        } catch (SQLException e) {
            throw new Exception("Failed to connect to the database", e);
        }
    }

    public void disconnect() throws Exception{
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database");
            }
        } catch (SQLException e) {
            throw new Exception("Failed to disconnect from the database", e);
        }
    }

    public boolean isConnected(){
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

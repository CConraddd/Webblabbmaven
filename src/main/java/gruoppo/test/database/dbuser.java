package gruoppo.test.database;

import gruoppo.test.Application.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dbuser {
    public static boolean login(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT userId FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }
    }

    public static User register(Connection connection, String username, String password) throws SQLException {
        User user;
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            connection.setAutoCommit(false);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            int userId;
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                userId = rs.getInt(1);
            }else{
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            user = new User(username, userId);
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            throw new SQLException("Creating user failed" + e.getMessage(), e);
        }finally {
            connection.setAutoCommit(true);
        }
        return user;
    }

    public static void updateUserInfo(Connection connection, int userId, String newUsername, String newPassword) throws SQLException {
        String query = "UPDATE users SET username = ?, password = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            stmt.setString(1, newUsername);
            stmt.setString(2, newPassword);
            stmt.setInt(3, userId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No user found with the given ID.");
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static void deleteUser(Connection connection, int userId) throws SQLException {
        String query = "DELETE FROM users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No user found with the given ID.");
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static int getUserId(Connection connection, String username) throws SQLException {
        String query = "SELECT userId FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userId");
            } else {
                throw new SQLException("No user found with the given username.");
            }
        }
    }
}

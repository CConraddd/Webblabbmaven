package gruoppo.test.Application;

import gruoppo.test.database.Dbimpl;
import gruoppo.test.database.dbproduct;
import gruoppo.test.database.dbuser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Model {
    private final Dbimpl dbmanager;

    public Model() {
        this.dbmanager = new Dbimpl();
    }

    public void connect() throws SQLException{
        dbmanager.connect();
    }

    public void disconnect() throws SQLException{
        dbmanager.disconnect();
    }

    public User registerUser(String username, String password) throws SQLException{
        try(Connection connection = dbmanager.getConnection()){
            return dbuser.register(connection, username, password);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean loginUser(String username, String password) throws SQLException{
        try(Connection connection = dbmanager.getConnection()){
            return dbuser.login(connection, username, password);
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public boolean updateUserInfo(int userId, String newUsername, String newPassword) throws SQLException{
        try(Connection connection = dbmanager.getConnection()){
            dbuser.updateUserInfo(connection, userId, newUsername, newPassword);
            return true;
        }catch (SQLException e){
            throw new SQLException("Failed to update user info" + e.getMessage());
        }
    }
    public boolean deleteUser(int userId) throws SQLException{
        try(Connection connection = dbmanager.getConnection()){
            dbuser.deleteUser(connection, userId);
            return true;
        }catch (SQLException e){
            throw new SQLException("Failed to delete user" + e.getMessage());
        }
    }
    public boolean addProductToCart(int userId, int productId, int quantity) throws SQLException{
        try(Connection connection = dbmanager.getConnection()){
            dbproduct.removeProductFromCart(connection, userId, productId);
            return true;
        }catch (SQLException e){
            throw new SQLException("Failed to add product to cart" + e.getMessage());
        }
    }

    public boolean removeProductFromCart(int userId, int productId) throws SQLException{
        try(Connection connection = dbmanager.getConnection()){
            dbproduct.removeProductFromCart(connection, userId, productId);
            return true;
        }catch (SQLException e){
            throw new SQLException("Failed to remove product from cart" + e.getMessage());
        }
    }

    public boolean clearCart(int userId) throws SQLException{
        try(Connection connection = dbmanager.getConnection()){
            dbproduct.clearCart(connection, userId);
            return true;
        }catch (SQLException e){
            throw new SQLException("Failed to clear cart" + e.getMessage());
        }
    }

    public List<Product> getProductsInCart(int userId) throws SQLException{
        try(Connection connection = dbmanager.getConnection()){
            return dbproduct.getProductsInCart(connection, userId);
        }catch (SQLException e){
            throw new SQLException("Failed retrieving all products in cart" + e.getMessage());
        }
    }
}

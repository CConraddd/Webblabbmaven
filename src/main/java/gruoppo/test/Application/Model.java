package gruoppo.test.Application;

import gruoppo.test.database.Dbimpl;
import gruoppo.test.database.dbproduct;
import gruoppo.test.database.dbuser;

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
        try{
            return dbuser.register(dbmanager.getConnection(), username, password);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean loginUser(String username, String password) throws SQLException{
        try{
            return dbuser.login(dbmanager.getConnection(), username, password);
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public int getUserId(String username) throws SQLException {
        try{
            return dbuser.getUserId(dbmanager.getConnection(), username);
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve user ID for username: " + username + " - " + e.getMessage());
        }
    }

    public void addProductToCart(int userId, int productId, int quantity) throws SQLException{
        try{
            dbproduct.addProductToCart(dbmanager.getConnection(), userId, productId, quantity);
        }catch (SQLException e){
            throw new SQLException("Failed to add product to cart" + e.getMessage());
        }
    }

    public void removeProductFromCart(int userId, int productId) throws SQLException{
        try{
            dbproduct.removeProductFromCart(dbmanager.getConnection(), userId, productId);
        }catch (SQLException e){
            throw new SQLException("Failed to remove product from cart" + e.getMessage());
        }
    }

    public void clearCart(int userId) throws SQLException{
        try{
            dbproduct.clearCart(dbmanager.getConnection(), userId);
        }catch (SQLException e){
            throw new SQLException("Failed to clear cart" + e.getMessage());
        }
    }

    public List<Product> getProductsInCart(int userId) throws SQLException{
        try{
            return dbproduct.getProductsInCart(dbmanager.getConnection(), userId);
        }catch (SQLException e){
            throw new SQLException("Failed retrieving all products in cart" + e.getMessage());
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        try {
            return dbproduct.getAllProducts(dbmanager.getConnection());
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve all products: " + e.getMessage());
        }
    }

}

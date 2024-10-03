package gruoppo.test.database;

import gruoppo.test.Application.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbproduct {
    /*
    // Connection to the database
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/your_database";  // Update with actual DB connection details
        String username = "root";  // Update with actual username
        String password = "";  // Update with actual password
        return DriverManager.getConnection(url, username, password);
    }

    // 1. Add a new product
    public static boolean addProduct(Product product) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO products (name, stock, price, category) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getStock());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setString(4, product.getCategory());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    */


    public static void addProductToCart(Connection connection, int userId, int productId, int quantity) throws SQLException {
        String query = "INSERT INTO shoppingCart (userId, productId, amount) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE amount = amount + ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        }
    }

    public static void removeProductFromCart(Connection connection, int userId, int productId) throws SQLException {
        String query = "DELETE FROM shoppingCart WHERE userId = ? AND productId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    public static void clearCart(Connection connection, int userId) throws SQLException {
        String query = "DELETE FROM shoppingCart WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public static List<Product> getProductsInCart(Connection connection, int userId) throws SQLException {
        String query = "SELECT p.productId, p.name, p.stock, p.price, p.category, c.amount " +
                "FROM products p " +
                "JOIN shoppingCart c ON p.productId = c.productId " +
                "WHERE c.userId = ?";
        List<Product> cart = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getBigDecimal("price"),
                        rs.getString("category"),
                        rs.getInt("amount")
                );
                cart.add(product);
            }
        }
        return cart;
    }

    public static List<Product> getAllProducts(Connection connection) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT productId, name, stock, price, category FROM products";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(

                    rs.getString("name"),
                    rs.getInt("stock"),
                    rs.getBigDecimal("price"),
                    rs.getString("category")
                );
                products.add(product);
            }
        }
        return products;
    }

    /*
    // 2. Update an existing product
    public static boolean updateProduct(Product product) {
        try (Connection conn = getConnection()) {
            String query = "UPDATE products SET name = ?, stock = ?, price = ?, category = ? WHERE productId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getStock());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            stmt.setInt(5, product.getProductId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Delete a product by ID
    public static boolean deleteProduct(int productId) {
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM products WHERE productId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Get a product by ID
    public static Product getProductById(int productId) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM products WHERE productId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getBigDecimal("price"),
                        rs.getString("category")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 5. Get all products
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getBigDecimal("price"),
                        rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

     */
}

package gruoppo.test.database;

import gruoppo.test.Application.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbproduct {

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
                        rs.getInt("productId"), // Lägg till productId
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getBigDecimal("price"),
                        rs.getString("category")
                );
                products.add(product);
            }
        }
        if (products.isEmpty()) {
            System.out.println("No products retrieved from the database.");
        }
        return products;
    }
}
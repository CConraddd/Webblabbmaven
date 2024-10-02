package database;

import application.Order;
import application.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dborder {

    // Connection to the database
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/your_database";  // Update with actual DB connection details
        String username = "root";  // Update with actual username
        String password = "";  // Update with actual password
        return DriverManager.getConnection(url, username, password);
    }

    // 1. Create a new order for a user
    public static int createOrder(int userId) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO orders (userId) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);  // Return the generated order ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void createOrderWithProducts(Connection connection, Order order) throws SQLException {
        String insertOrderQuery = "INSERT INTO orders (userId) VALUES (?)";
        String insertOrderProductQuery = "INSERT INTO orderProducts (orderId, productId, amount) VALUES (?, ?, ?)";
        if(order == null || order.getProducts() == null||order.getProducts().isEmpty()){
            throw new IllegalArgumentException("Order object is null or empty");
        }
        try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement productStmt = connection.prepareStatement(insertOrderProductQuery)) {

            connection.setAutoCommit(false);

            orderStmt.setInt(1, order.getUserId());
            int rowsAffected = orderStmt.executeUpdate();

            if(rowsAffected == 0){
                throw new SQLException("Insert order failed, no rows affected");
            }

            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained");
            }

            for(Product product : order.getProducts()){
                int productId = product.getProductId();
                int quantity = product.getAmount();
                if(quantity <=0){
                    throw new IllegalArgumentException("Product quantity is less than zero for product-id: " + productId);
                }

                productStmt.setInt(1, orderId);
                productStmt.setInt(2, productId);
                productStmt.setInt(3, quantity);

                int productRowsAffected = productStmt.executeUpdate();
                if(productRowsAffected == 0){
                    throw new SQLException("Insert product failed with id "+ productId +" no rows affected");
                }
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException("Insert order failed" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }


    // 2. Add a product to an order
    public static boolean addProductToOrder(int orderId, int productId, int amount) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO orderProducts (orderId, productId, amount) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, amount);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Remove a product from an order
    public static boolean removeProductFromOrder(int orderId, int productId) {
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM orderProducts WHERE orderId = ? AND productId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Get order details by order ID
    public static Order getOrderById(int orderId) {
        try (Connection conn = getConnection()) {
            // Fetch order details
            String orderQuery = "SELECT * FROM orders WHERE orderId = ?";
            PreparedStatement orderStmt = conn.prepareStatement(orderQuery);
            orderStmt.setInt(1, orderId);
            ResultSet orderRs = orderStmt.executeQuery();

            if (orderRs.next()) {
                // Fetch products in the order
                String productsQuery = "SELECT p.productId, p.name, op.amount, p.price FROM orderProducts op JOIN products p ON op.productId = p.productId WHERE op.orderId = ?";
                PreparedStatement productStmt = conn.prepareStatement(productsQuery);
                productStmt.setInt(1, orderId);
                ResultSet productRs = productStmt.executeQuery();

                List<Product> products = new ArrayList<>();
                while (productRs.next()) {
                    Product product = new Product(
                            productRs.getInt("productId"),
                            productRs.getString("name"),
                            productRs.getInt("amount"),
                            productRs.getBigDecimal("price"),
                            null  // Category is not retrieved here, but it can be added if needed
                    );
                    products.add(product);
                }

                return new Order(orderRs.getInt("orderId"), orderRs.getInt("userId"), products);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 5. Get all orders for a specific user
    public static List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM orders WHERE userId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                Order order = getOrderById(orderId);
                if (order != null) {
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}

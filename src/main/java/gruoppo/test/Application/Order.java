package application;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private Timestamp created;
    private Timestamp updated;
    private Timestamp delivered;
    private List<Product> products;

    // Constructor accepting product list
    public Order(int orderId, int userId, List<Product> products) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
    }

    // Constructor accepting timestamps (for completeness)
    public Order(int orderId, int userId, Timestamp created, Timestamp updated, Timestamp delivered) {
        this.orderId = orderId;
        this.userId = userId;
        this.created = created;
        this.updated = updated;
        this.delivered = delivered;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Timestamp getDelivered() {
        return delivered;
    }

    public void setDelivered(Timestamp delivered) {
        this.delivered = delivered;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", userId=" + userId + ", products=" + products + "]";
    }
}

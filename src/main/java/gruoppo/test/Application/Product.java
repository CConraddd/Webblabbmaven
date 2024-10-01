package gruoppo.test.Application;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String name;
    private int stock;
    private BigDecimal price;
    private String category;

    // Constructor for creating a new product without a product ID (e.g., for adding a new product)
    public Product(String name, int stock, BigDecimal price, String category) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }

    // Constructor for fetching an existing product (with productId)
    public Product(int productId, String name, int stock, BigDecimal price, String category) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // ToString method for displaying product information
    @Override
    public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", stock=" + stock + ", price=" + price
                + ", category=" + category + "]";
    }
}

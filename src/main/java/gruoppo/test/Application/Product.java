package gruoppo.test.Application;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String name;
    private int stock;
    private int amount;
    private BigDecimal price;
    private String category;

    public Product(int productId, String name, int stock, BigDecimal price, String category) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }

    public Product(int productId, String name, int stock, BigDecimal price, String category, int amount) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.category = category;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", stock=" + stock + ", price=" + price
                + ", category=" + category + "]";
    }
}

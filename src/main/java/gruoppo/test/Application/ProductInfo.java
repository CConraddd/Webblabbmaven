package gruoppo.test.Application;

import java.math.BigDecimal;

public final class ProductInfo {
    private int productId;
    private String name;
    private BigDecimal price;
    private int stock;
    private int amount;
    private String category;

    public ProductInfo(int productId, String name, BigDecimal price ,int stock, int amount, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.amount = amount;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

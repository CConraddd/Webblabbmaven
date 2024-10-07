package gruoppo.test.service;

import gruoppo.test.Application.Product;
import gruoppo.test.Application.ProductInfo;
import gruoppo.test.database.dbproduct;
import gruoppo.test.database.Dbimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final Dbimpl dbManager;

    public ProductService() {
        this.dbManager = new Dbimpl();
    }

    public List<ProductInfo> getAllProducts() throws SQLException {
        List<Product> products = dbproduct.getAllProducts(dbManager.getConnection());
        List<ProductInfo> productInfos = new ArrayList<>();

        for (Product product : products) {
            productInfos.add(new ProductInfo(product.getProductId(), product.getName(), product.getPrice(), product.getStock(), product.getAmount(), product.getCategory()));
        }

        return productInfos;
    }

    public void addProductToCart(int userId, int productId, int quantity) throws SQLException {
        dbproduct.addProductToCart(dbManager.getConnection(), userId, productId, quantity);
    }

    public List<ProductInfo> getProductsInCart(int userId) throws SQLException {
        List<Product> cartProducts = dbproduct.getProductsInCart(dbManager.getConnection(), userId);
        List<ProductInfo> cartProductInfos = new ArrayList<>();

        for (Product product : cartProducts) {
            cartProductInfos.add(new ProductInfo(product.getProductId(), product.getName(), product.getPrice(), product.getStock(), product.getAmount(), product.getCategory()));
        }
        return cartProductInfos;
    }

    public void removeProductFromCart(int userId, int productId) throws SQLException {
        dbproduct.removeProductFromCart(dbManager.getConnection(), userId, productId);
    }

    public void clearCart(int userId) throws SQLException {
        dbproduct.clearCart(dbManager.getConnection(), userId);
    }
}

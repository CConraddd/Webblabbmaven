-- 1-init.sql
CREATE TABLE IF NOT EXISTS users (
                                     userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );
CREATE TABLE IF NOT EXISTS products (
                                        productId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
    stock INT DEFAULT 0,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(45) DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS shoppingCart (
                                            userId INT NOT NULL,
                                            productId INT NOT NULL,
                                            amount INT DEFAULT 1,
                                            PRIMARY KEY (userId, productId),
    FOREIGN KEY (userId) REFERENCES users(userId),
    FOREIGN KEY (productId) REFERENCES products(productId)
    );
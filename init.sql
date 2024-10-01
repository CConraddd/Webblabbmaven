-- init.sql

CREATE TABLE IF NOT EXISTS users (
                                     userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS products (
                                        productId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
    stock INT DEFAULT 0,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(45) DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS orders (
                                      orderId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      userId INT NOT NULL,
                                      FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS orderProducts (
                                             orderId INT NOT NULL,
                                             productId INT NOT NULL,
                                             amount INT DEFAULT 1,
                                             PRIMARY KEY (orderId, productId),
    FOREIGN KEY (orderId) REFERENCES orders(orderId) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES products(productId) ON DELETE CASCADE
    );

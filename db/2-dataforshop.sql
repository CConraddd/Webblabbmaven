-- Insert data into users table
INSERT INTO users (username, password) VALUES
                                           ('alice_jones', 'password1'),
                                           ('bob_smith', 'password2'),
                                           ('charlie_brown', 'password3'),
                                           ('diana_white', 'password4');

-- Insert data into products table
INSERT INTO products (name, stock, price, category) VALUES
                                                        ('Gaming Laptop', 5, 1200.00, 'Electronics'),
                                                        ('Wireless Mouse', 20, 25.99, 'Accessories'),
                                                        ('Office Chair', 15, 150.00, 'Furniture'),
                                                        ('Bluetooth Headphones', 30, 75.50, 'Accessories'),
                                                        ('Coffee Maker', 10, 89.99, 'Kitchenware');

-- Insert data into shoppingCart table
INSERT INTO shoppingCart (userId, productId, amount) VALUES
                                                         (1, 1, 1), -- Alice added 1 Gaming Laptop
                                                         (1, 2, 2), -- Alice added 2 Wireless Mice
                                                         (2, 3, 1), -- Bob added 1 Office Chair
                                                         (3, 4, 1), -- Charlie added 1 Bluetooth Headphones
                                                         (4, 5, 1); -- Diana added 1 Coffee Maker
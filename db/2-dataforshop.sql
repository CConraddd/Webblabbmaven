-- Insert into users table
INSERT INTO users (username, password) VALUES
                                           ('john_doe', 'password123'),
                                           ('jane_smith', 'securepass'),
                                           ('michael_jones', 'pass456'),
                                           ('susan_brown', 'mypassword');

-- Insert into products table
INSERT INTO products (name, stock, price, category) VALUES
                                                        ('Laptop', 10, 999.99, 'Electronics'),
                                                        ('Headphones', 50, 199.99, 'Accessories'),
                                                        ('Smartphone', 25, 599.99, 'Electronics'),
                                                        ('Desk Chair', 15, 129.99, 'Furniture'),
                                                        ('Coffee Mug', 100, 9.99, 'Kitchenware');

-- Insert into orders table
INSERT INTO orders (userId) VALUES
                                (1), -- Order for john_doe
                                (2), -- Order for jane_smith
                                (3), -- Order for michael_jones
                                (1), -- Another order for john_doe
                                (4); -- Order for susan_brown

-- Insert into orderProducts table
INSERT INTO orderProducts (orderId, productId, amount) VALUES
                                                           (1, 1, 1), -- john_doe ordered 1 Laptop
                                                           (1, 2, 2), -- john_doe ordered 2 Headphones
                                                           (2, 3, 1), -- jane_smith ordered 1 Smartphone
                                                           (3, 4, 1), -- michael_jones ordered 1 Desk Chair
                                                           (4, 5, 3), -- john_doe ordered 3 Coffee Mugs
                                                           (5, 5, 5); -- susan_brown ordered 5 Coffee Mugs

<%@ page import="gruoppo.test.Application.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>





<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Web Shop - Home</title>
</head>
<body>
    <h1>Welcome to the Web Shop poopoo!</h1>
    <nav>
        <a href="login.jsp">Login</a> |
        <a href="products.jsp">View Products</a> |
        <a href="cart.jsp">View Cart</a>
    </nav>

    <h2>Featured Products</h2>
    <ul>
        <%
            // Exempel: hämta lista med produkter (detta borde egentligen hämtas från en databas via en servlet/controller)
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null) {
                for (Product product : products) {
        %>
            <li>
                <%= product.getName() %> - <%= product.getPrice() %> USD
                <form action="addProductToCart" method="post">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="number" name="quantity" value="1" min="1">
                    <button type="submit">Add to Cart</button>
                </form>
            </li>
        <%
                }
            } else {
        %>
            <p>No products available.</p>
        <%
            }
        %>
    </ul>
</body>
</html>

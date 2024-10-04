<%@ page import="gruoppo.test.Application.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Products - Web Shop</title>
    <style>
        .product-list {
            display: flex;
            flex-wrap: wrap;
        }
        .product {
            border: 1px solid #ddd;
            padding: 10px;
            margin: 10px;
            border-radius: 5px;
            width: 200px;
            text-align: center;
        }
    </style>
</head>
<body>
<h1>Available Products</h1>
<nav>
    <a href="index.jsp">Home</a> |
    <a href="cart.jsp">View Cart</a>
</nav>

<div class="product-list">
    <%
        // Hämta alla produkter från requesten (satt av controllern)
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
    %>
    <div class="product">
        <h2><%= product.getName() %></h2>
        <p>Price: $<%= product.getPrice() %></p>
        <p>Stock: <%= product.getStock() %></p>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="addToCart">
            <input type="hidden" name="productId" value="<%= product.getProductId() %>">
            <input type="number" name="quantity" value="1" min="1">
            <button type="submit">Add to Cart</button>
        </form>
    </div>
    <%
        }
    } else {
    %>
    <p>No products available.</p>
    <%
        }
    %>
</div>
</body>
</html>

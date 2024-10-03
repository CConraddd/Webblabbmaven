<%@ page import="gruoppo.test.Application.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Products - Web Shop</title>
</head>
<body>
<h1>Products</h1>
<nav>
    <a href="index.jsp">Home</a> |
    <a href="cart.jsp">View Cart</a>
</nav>

<ul>
    <%
        // Hämta alla produkter från databas (detta borde hanteras genom en servlet/controller)
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

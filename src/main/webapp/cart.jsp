<%@ page import="gruoppo.test.Application.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart - Web Shop</title>
</head>
<body>
<h1>Your Shopping Cart</h1>
<nav>
    <a href="index.jsp">Home</a> |
    <a href="products.jsp">View Products</a>
</nav>

<%
    // Exempel: hämta shoppingkorg från sessionen
    List<Product> cart = (List<Product>) session.getAttribute("cart");
    if (cart != null && !cart.isEmpty()) {
%>
<ul>
    <%
        for (Product product : cart) {
    %>
    <li>
        <%= product.getName() %> - <%= product.getPrice() %> USD - Quantity: <%= product.getAmount() %>
        <form action="removeProductFromCart" method="post">
            <input type="hidden" name="productId" value="<%= product.getProductId() %>">
            <button type="submit">Remove</button>
        </form>
    </li>
    <%
        }
    %>
</ul>
<form action="clearCart" method="post">
    <button type="submit">Clear Cart</button>
</form>
<%
} else {
%>
<p>Your cart is empty.</p>
<%
    }
%>
</body>
</html>

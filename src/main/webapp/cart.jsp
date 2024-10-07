<%@ page import="gruoppo.test.Application.ProductInfo" %>
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
    <a href="controller?action=viewProducts">View Products</a>
</nav>

<%
    List<ProductInfo> cart = (List<ProductInfo>) session.getAttribute("cart");
    if (cart != null && !cart.isEmpty()) {
%>
<ul>
    <%
        for (ProductInfo product : cart) {
    %>
    <li>
        <%= product.getName() %> - <%= product.getPrice() %> USD - Quantity: <%= product.getAmount() %>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="removeFromCart">
            <input type="hidden" name="productId" value="<%= product.getProductId() %>">
            <button type="submit">Remove</button>
        </form>
    </li>
    <%
        }
    %>
</ul>
<form action="controller" method="post">
    <input type="hidden" name="action" value="clearCart">
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

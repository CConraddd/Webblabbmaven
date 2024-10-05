<%@ page import="gruoppo.test.Application.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Web Shop - Home</title>
</head>
<body>
<h1>Welcome to the Web Shop!</h1>

<c:if test="${not empty user}">
    <div>
        Welcome, ${user.username}!
    </div>
</c:if>

<nav>
    <c:choose>
        <c:when test="${not empty user}">
            <a href="controller?action=logout">Logout</a>
        </c:when>
        <c:otherwise>
            <a href="login.jsp">Login</a> |
        </c:otherwise>
    </c:choose>
    <a href="controller?action=viewProducts">View Products</a> |
    <a href="cart.jsp">View Cart</a>
</nav>

<h2>Featured Products</h2>
<ul>
    <c:choose>
        <c:when test="${not empty products}">
            <c:forEach var="product" items="${products}">
                <li>
                        ${product.name} - ${product.price} USD
                    <form action="controller" method="post">
                        <input type="hidden" name="action" value="addToCart">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <input type="number" name="quantity" value="1" min="1">
                        <button type="submit">Add to Cart</button>
                    </form>
                </li>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>No products available.</p>
        </c:otherwise>
    </c:choose>
</ul>

</body>
</html>

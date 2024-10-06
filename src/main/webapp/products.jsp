<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:if test="${not empty products}">
        <c:forEach var="product" items="${products}">
            <div class="product">
                <h2>${product.name}</h2>
                <p>Price: $${product.price}</p>
                <p>Stock: ${product.stock}</p>
                <c:choose>
                    <c:when test="${not empty user}">
                        <form action="controller" method="post">
                            <input type="hidden" name="action" value="addToCart">
                            <input type="hidden" name="productId" value="${product.productId}">
                            <input type="number" name="quantity" value="1" min="1">
                            <button type="submit">Add to Cart</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p><a href="login.jsp">Log in to add to cart</a></p>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty products}">
        <p>No products available.</p>
    </c:if>
</div>
</body>
</html>

<%@ page import="gruoppo.test.Application.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Webshop</title>
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .product {
      border: 1px solid #ddd;
      padding: 10px;
      margin: 10px;
      border-radius: 5px;
      width: 200px;
      text-align: center;
    }
    .product-list {
      display: flex;
      flex-wrap: wrap;
    }
  </style>
</head>
<body>

<h1>Available Products</h1>

<div id="product-list" class="product-list"></div>

<script>
  // Fetch products from the backend API and display them
  function loadProducts() {
    fetch('/api/products')
            .then(response => response.json())
            .then(products => {
              const productList = document.getElementById('product-list');
              productList.innerHTML = ''; // Clear any existing content

              products.forEach(product => {
                const productDiv = document.createElement('div');
                productDiv.classList.add('product');

                productDiv.innerHTML = `
                    <h2>${product.name}</h2>
                    <p>Price: $${product.price}</p>
                    <p>Stock: ${product.stock}</p>
                    <button onclick="addToCart(${product.id})">Add to Cart</button>
                `;

                productList.appendChild(productDiv);
              });
            })
            .catch(error => {
              console.error('Error fetching products:', error);
            });
  }

  // Add product to cart (dummy function)
  function addToCart(productId) {
    alert('Product ' + productId + ' added to cart.');
  }

  // Load products when the page is loaded
  window.onload = loadProducts;
</script>

</body>
</html>
--%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Web Shop - Home</title>
</head>
<body>
<h1>Welcome to the Web Shop!</h1>
<nav>
    <a href="login.jsp">Login</a> |
    <a href="controller?action=viewProducts">View Products</a> |
    <a href="cart.jsp">View Cart</a>
</nav>

<h2>Featured Products</h2>
<ul>
    <%
        // Hämta lista med produkter (detta attribut måste sättas av controllern)
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
    %>
    <li>
        <%= product.getName() %> - <%= product.getPrice() %> USD
        <form action="controller" method="post">
            <input type="hidden" name="action" value="addToCart">
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
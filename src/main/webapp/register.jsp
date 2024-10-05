<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Web Shop</title>
</head>
<body>
<h1>Register a New Account</h1>

<form action="controller" method="post">
    <input type="hidden" name="action" value="register">

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Register</button>
</form>

<p>Already have an account? <a href="login.jsp">Login here</a></p>

<c:if test="${not empty errorMessage}">
    <div style="color: red;">
        <strong>${errorMessage}</strong>
    </div>
</c:if>
</body>
</html>

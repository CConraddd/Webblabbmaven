<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Web Shop</title>
</head>
<body>
<h1>Login</h1>
<form action="controller" method="post">
    <input type="hidden" name="action" value="login">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Login</button>
</form>
<p>Don't have an account? <a href="register.jsp">Register here</a></p>

<%-- Visar felmeddelande vid misslyckad inloggning --%>
<c:if test="${not empty errorMessage}">
    <div style="color: red;">
        <strong>${errorMessage}</strong>
    </div>
</c:if>

</body>
</html>

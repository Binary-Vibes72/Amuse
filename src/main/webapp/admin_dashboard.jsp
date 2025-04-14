<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
</head>
<body>
    <h1>Welcome to the Admin Dashboard!</h1>
    <p>This area is restricted to administrators.</p>
    <br>
    <a href="<%= request.getContextPath() %>/products">Back to Products</a>
    <br>
    <%--  Add this link to go to the product management page: --%>
    <a href="<%= request.getContextPath() %>/admin/products/manage">Manage Products</a>
</body>
</html>
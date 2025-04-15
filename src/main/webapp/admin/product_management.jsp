<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
            font-weight: 700;
            font-size: 28px;
        }

        h2 {
            margin-top: 30px;
            margin-bottom: 20px;
            color: #444;
            font-weight: 600;
            font-size: 22px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        th, td {
            padding: 15px;
            border-bottom: 1px solid #ddd;
            text-align: left;
            font-size: 15px;
        }

        th {
            background-color: #f0f0f0;
            font-weight: 600;
        }

        tbody tr:hover {
            background-color: #f5f5f5;
        }

        form {
            margin-bottom: 30px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
        }

        form label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 600;
            font-size: 14px;
        }

        form input[type="text"], form input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 16px;
            transition: border-color 0.3s ease;
        }

        form input[type="text"]:focus, form input[type="number"]:focus {
            outline: none;
            border-color: #4CAF50;
        }

        form button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        form button:hover {
            background-color: #45a049;
        }

        .error-message {
            color: red;
            margin-bottom: 15px;
            font-size: 14px;
        }

        .success-message {
            color: green;
            margin-bottom: 15px;
            font-size: 14px;
        }

        .actions {
            display: flex;
            gap: 10px;
        }

        .edit-button {
            background-color: #008CBA;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s ease;
        }

        .edit-button:hover {
            background-color: #007B95;
        }

        .delete-button {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s ease;
        }

        .delete-button:hover {
            background-color: #d32f2f;
        }

        #editForm {
            display: none;
            margin-top: 30px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
        }

        .back-button { /* Added style for the back button */
            margin-bottom: 20px;
        }

        .back-button a {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .back-button a:hover {
            background-color: #45a049;
        }

    </style>
</head>
<body>
    <div class="container">
        <div class="back-button">
            <a href="<%= request.getContextPath() %>/admin/dashboard">Back to Dashboard</a>
        </div>
        <h1>Product Management</h1>
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>
        <c:if test="${not empty successMessage}">
            <p class="success-message">${successMessage}</p>
        </c:if>

        <h2>Products</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.productId}</td>
                        <td>${product.name}</td>
                        <td>${product.description}</td>
                        <td>$${product.price}</td>
                        <td><img src="${product.imageUrl}" alt="${product.name}" width="100"></td>
                        <td>
                            <div class="actions">
                                <button class="edit-button" onclick="showEditForm(${product.productId}, '${product.name}', '${product.description}', ${product.price}, '${product.imageUrl}')">Edit</button>
                                <form action="manage" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${product.productId}">
                                    <button class="delete-button" type="submit" onclick="return confirm('Are you sure?')">Delete</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <h2>Add Product</h2>
        <form action="manage" method="post">
            <input type="hidden" name="action" value="add">
            <label for="name">Name:</label>
            <input type="text" name="name" id="name" required>
            <label for="description">Description:</label>
            <input type="text" name="description" id="description" required>
            <label for="price">Price:</label>
            <input type="number" name="price" id="price" min="0" step="0.01" required>
            <label for="imageUrl">Image URL:</label>
            <input type="text" name="imageUrl" id="imageUrl" required>
            <button type="submit">Add Product</button>
        </form>

        <div id="editForm" style="display: none;">
            <h2>Edit Product</h2>
            <form action="manage" method="post">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="id" id="editId">
                <label for="editName">Name:</label>
                <input type="text" name="name" id="editName" required>
                <label for="editDescription">Description:</label>
                <input type="text" name="description" id="editDescription" required>
                <label for="editPrice">Price:</label>
                <input type="number" name="price" id="editPrice" min="0" step="0.01" required>
                <label for="editImageUrl">Image URL:</label>
                <input type="text" name="imageUrl" id="editImageUrl" required>
                <button type="submit">Update Product</button>
            </form>
        </div>
    </div>

    <script>
        function showEditForm(id, name, description, price, imageUrl) {
            document.getElementById("editId").value = id;
            document.getElementById("editName").value = name;
            document.getElementById("editDescription").value = description;
            document.getElementById("editPrice").value = price;
            document.getElementById("editImageUrl").value = imageUrl;
            document.getElementById("editForm").style.display = "block";
        }
    </script>
</body>
</html>
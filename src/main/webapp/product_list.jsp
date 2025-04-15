<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
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
            padding: 20px;
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

        .controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .controls a {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .controls a:hover {
            background-color: #45a049;
        }

        .product-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .product-card {
            background-color: #ffffff;
            border-radius: 10px;
            padding: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
        }

        .product-card img {
            width: 100%;
            border-radius: 8px;
            margin-bottom: 15px;
            height: 200px;
            object-fit: cover;
        }

        .product-card h3 {
            font-size: 18px;
            margin-bottom: 10px;
            color: #333;
            font-weight: 600;
        }

        .product-card h3 a {
            text-decoration: none;
            color: #007BFF;
            transition: color 0.3s ease;
        }

        .product-card h3 a:hover {
            color: #0056b3;
        }

        .product-card p {
            font-size: 14px;
            color: #555;
            margin-bottom: 10px;
            line-height: 1.5;
        }

        .product-card .price {
            font-size: 20px;
            color: #4CAF50;
            font-weight: 700;
            margin-bottom: 15px;
        }

        .product-card .add-to-cart {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
            text-align: center;
        }

        .product-card .add-to-cart:hover {
            background-color: #45a049;
        }

        .error-message {
            color: red;
            margin-bottom: 15px;
            font-size: 16px;
            text-align: center;
        }

        .no-products-message {
            text-align: center;
            font-size: 18px;
            color: #555;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Our Products</h1>
        <div class="controls">
            <a href="logout">Log Out</a>
            <a href="cart">View Cart</a>
        </div>
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>

        <c:if test="${not empty products}">
            <div class="product-list">
                <c:forEach var="product" items="${products}">
                    <div class="product-card">
                        <img src="${product.imageUrl}" alt="${product.name}">
                        <h3><a href="product?id=${product.productId}">${product.name}</a></h3>
                        <p>${product.description}</p>
                        <p class="price">$${product.price}</p>
                        <a class="add-to-cart" href="add-to-cart?productId=${product.productId}">Add to Cart</a>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty products and empty errorMessage}">
            <p class="no-products-message">No products available.</p>
        </c:if>
    </div>
</body>
</html>
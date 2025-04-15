<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>
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

        .product-details {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #ffffff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .product-image {
            width: 100%;
            max-width: 400px;
            border-radius: 8px;
            margin-bottom: 20px;
            height: auto; /* Maintain aspect ratio */
            object-fit: cover;
        }

        .product-name {
            font-size: 24px;
            margin-bottom: 10px;
            color: #333;
            font-weight: 600;
            text-align: center;
        }

        .product-description {
            font-size: 16px;
            color: #555;
            margin-bottom: 20px;
            line-height: 1.7;
            text-align: justify;
        }

        .product-price {
            font-size: 26px;
            color: #4CAF50;
            font-weight: 700;
            margin-bottom: 20px;
            text-align: center;
        }

        .add-to-cart {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 18px;
            transition: background-color 0.3s ease;
            cursor: pointer;
            text-align: center;
            margin: 0 auto;
        }

        .add-to-cart:hover {
            background-color: #45a049;
        }

        .back-button {
            margin-bottom: 20px;
            text-align: left; /* Align the button to the left */
        }

        .back-button a {
            display: inline-block;
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .back-button a:hover {
            background-color: #0056b3;
        }

        .error-message {
            color: red;
            margin-top: 20px;
            font-size: 16px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="back-button">
            <a href="<%= request.getContextPath() %>/products">Back to Products</a>
        </div>
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>
        <c:if test="${not empty product}">
            <div class="product-details">
                <img class="product-image" src="${product.imageUrl}" alt="${product.name}">
                <h1 class="product-name">${product.name}</h1>
                <p class="product-description">${product.description}</p>
                <p class="product-price">$${product.price}</p>
                <a class="add-to-cart" href="add-to-cart?productId=${product.productId}">Add to Cart</a>
            </div>
        </c:if>
    </div>
</body>
</html>

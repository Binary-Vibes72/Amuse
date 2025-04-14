<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <style>
        .product {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
        }
        .product img {
            max-width: 100px;
            height: auto;
            margin-right: 10px;
        }
        .product-details {
            flex-grow: 1;
        }
    </style>
</head>
<body>
	<p><a href="logout">Log Out</a></p>
	<p><a href="cart">View Cart</a></p>
    <h1>Our Products</h1>

    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <c:if test="${not empty products}">
        <c:forEach var="product" items="${products}">
            <div class="product">
                <img src="${product.imageUrl}" alt="${product.name}">
                <div class="product-details">
                    <h3><a href="product?id=${product.productId}">${product.name}</a></h3>
                    <p>${product.description}</p>
                    <p>Price: $${product.price}</p>
                    <p><a href="add-to-cart?productId=${product.productId}">Add to Cart</a></p>
                </div>
            </div>
        </c:forEach>
    </c:if>

    <c:if test="${empty products and empty errorMessage}">
        <p>No products available.</p>
    </c:if>
</body>
</html>
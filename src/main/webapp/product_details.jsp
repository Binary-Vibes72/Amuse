<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${product.name}" default="Product Details"/></title>
    <style>
        .product-detail {
            border: 1px solid #ccc;
            padding: 20px;
            margin-bottom: 20px;
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }
        .product-detail img {
            max-width: 300px;
            height: auto;
            margin-bottom: 15px;
        }
        .product-detail h2 {
            margin-top: 0;
        }
        .product-detail p {
            margin-bottom: 10px;
        }
        .price {
            font-size: 1.2em;
            font-weight: bold;
            color: green;
        }
    </style>
</head>
<body>
    <h1>Product Details</h1>

    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <c:if test="${not empty product}">
        <div class="product-detail">
            <h2>${product.name}</h2>
            <img src="${product.imageUrl}" alt="${product.name}">
            <p>${product.description}</p>
            <p class="price">Price: $${product.price}</p>
            </div>
    </c:if>

    <c:if test="${empty product and empty errorMessage}">
        <p>No product details found.</p>
    </c:if>

    <br>
    <a href="products">Back to Product List</a>
</body>
</html>
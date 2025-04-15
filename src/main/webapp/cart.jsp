<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
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

        form {
            margin-bottom: 30px;
        }

        .cart-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid #ddd;
            margin-bottom: 15px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .cart-item:last-child {
            border-bottom: none;
        }

        .cart-item img {
            width: 100px;
            height: 100px;
            border-radius: 8px;
            margin-right: 20px;
            object-fit: cover;
        }

        .cart-item-details {
            flex: 1;
        }

        .cart-item-details h3 {
            font-size: 18px;
            color: #333;
            font-weight: 600;
            margin-bottom: 5px;
        }

        .cart-item-details p {
            font-size: 16px;
            color: #555;
            margin-bottom: 10px;
        }

        .quantity-input {
            width: 60px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            text-align: center;
            font-size: 16px;
            margin-left: 10px;
            box-sizing: border-box;
            transition: border-color 0.3s ease;
        }

        .quantity-input:focus {
            outline: none;
            border-color: #4CAF50;
        }

        .remove-item {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .remove-item:hover {
            background-color: #d32f2f;
        }

        .cart-summary {
            margin-top: 30px;
            text-align: right;
        }

        .cart-summary p {
            font-size: 20px;
            color: #333;
            margin-bottom: 10px;
            font-weight: 600;
        }

        .checkout-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 18px;
            transition: background-color 0.3s ease;
            width: auto;
            display: inline-block;
            margin-top: 10px;
        }

        .checkout-button:hover {
            background-color: #45a049;
        }

        .empty-cart-message {
            text-align: center;
            font-size: 18px;
            color: #555;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
            margin-top: 20px;
        }

        .continue-shopping {
            display: inline-block;
            margin-top: 20px;
            color: #007BFF;
            text-decoration: none;
            font-size: 16px;
        }

        .continue-shopping:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Your Shopping Cart</h1>

        <c:if test="${not empty cart}">
            <form action="update-cart" method="post">
                <c:forEach var="item" items="${cart}">
                    <div class="cart-item">
                        <img src="${item.product.imageUrl}" alt="${item.product.name}">
                        <div class="cart-item-details">
                            <h3>${item.product.name}</h3>
                            <p>Price: $${item.product.price}</p>
                            <p>
                                Quantity:
                                <input type="number" class="quantity-input" name="quantity-${item.product.productId}" value="${item.quantity}" min="1">
                            </p>
                        </div>
                        <button class="remove-item" onclick="removeItem(${item.product.productId})">Remove</button>
                    </div>
                </c:forEach>
                <button type="submit" class="checkout-button">Update Cart</button>
                <hr>
                <div class="cart-summary">
                    <p><strong>Total Items:</strong> ${totalQuantity}</p>
                    <p><strong>Total Price:</strong> $${totalPrice}</p>
                    <a href="shipping-info" class="checkout-button">Proceed to Checkout</a>
                </div>
            </form>
        </c:if>

        <c:if test="${empty cart}">
            <p class="empty-cart-message">Your cart is empty.</p>
        </c:if>

        <a href="products" class="continue-shopping">Continue Shopping</a>
    </div>
    <script>
        function removeItem(productId) {
            // Implement logic to remove item from cart using AJAX
            console.log("Remove product with ID: " + productId);
        }
    </script>
</body>
</html>

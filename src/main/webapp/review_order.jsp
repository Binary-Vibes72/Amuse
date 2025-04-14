<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Review Your Order</title>
    <style>
        .container {
            width: 600px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        h1, h2 {
            margin-bottom: 20px;
        }
        .order-summary {
            margin-bottom: 20px;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 5px;
        }
        .order-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 5px;
        }
        .shipping-info, .payment-info {
            margin-bottom: 20px;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 5px;
        }
        button[type="submit"] {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            font-size: 1em;
        }
        button[type="submit"]:hover {
            background-color: #218838;
        }
        .back-button {
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            font-size: 1em;
            margin-right: 10px;
        }
        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Review Your Order</h1>

        <div class="order-summary">
            <h2>Order Summary</h2>
            <c:forEach var="item" items="${cart}">
                <div class="order-item">
                    <p>${item.product.name} x ${item.quantity}</p>
                    <p>$${item.product.price * item.quantity}</p>
                </div>
            </c:forEach>
            <hr>
            <p><strong>Total Items:</strong> ${totalQuantity}</p>
            <p><strong>Total Price:</strong> $${totalPrice}</p>
        </div>

        <div class="shipping-info">
            <h2>Shipping Information</h2>
            <p><strong>Name:</strong> ${shippingName}</p>
            <p><strong>Address:</strong> ${shippingAddress}, ${shippingCity}, ${shippingState} ${shippingZipCode}, ${shippingCountry}</p>
            <p><strong>Email:</strong> ${shippingEmail}</p>
        </div>

        <div class="payment-info">
            <h2>Payment Information</h2>
            <p><strong>Card Number:</strong> ${paymentCardNumber}</p>
            <p><strong>Expiry Date:</strong> ${paymentExpiryDate}</p>
        </div>

        <form action="confirm-order" method="post">
            <button type="submit">Confirm Order</button>
        </form>
        <a href="payment-info" class="back-button">Back to Payment</a>
    </div>
</body>
</html>
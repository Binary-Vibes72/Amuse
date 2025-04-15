<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Review Your Order</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        .container {
            max-width: 800px;
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
            margin-bottom: 20px;
            color: #444;
            font-weight: 600;
            font-size: 22px;
        }

        .order-summary {
            margin-bottom: 30px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
        }

        .order-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
            font-size: 16px;
            color: #555;
        }

        .order-item p {
            margin: 0;
        }

        .order-item strong {
            color: #333;
        }

        .shipping-info,
        .payment-info {
            margin-bottom: 30px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
        }

        .info-title {
            font-size: 18px;
            color: #333;
            font-weight: 600;
            margin-bottom: 15px;
        }

        .info-details p {
            font-size: 16px;
            color: #555;
            line-height: 1.7;
        }

        .confirm-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 18px;
            transition: background-color 0.3s ease;
            width: 100%;
            text-align: center;
            margin-top: 20px;
        }

        .confirm-button:hover {
            background-color: #45a049;
        }

        .back-button {
            margin-top: 20px;
            text-align: left;
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
                    <p><strong>$${item.product.price * item.quantity}</strong></p>
                </div>
            </c:forEach>
            <hr>
            <p><strong>Total Items:</strong> ${totalQuantity}</p>
            <p><strong>Total Price:</strong> $${totalPrice}</p>
        </div>

        <div class="shipping-info">
            <h2 class="info-title">Shipping Information</h2>
            <div class="info-details">
                <p><strong>Name:</strong> ${shippingName}</p>
                <p><strong>Address:</strong> ${shippingAddress}, ${shippingCity}, ${shippingState} ${shippingZipCode}, ${shippingCountry}</p>
                <p><strong>Email:</strong> ${shippingEmail}</p>
            </div>
        </div>

        <div class="payment-info">
            <h2 class="info-title">Payment Information</h2>
            <div class="info-details">
                <p><strong>Card Number:</strong> ${paymentCardNumber}</p>
                <p><strong>Expiry Date:</strong> ${paymentExpiryDate}</p>
            </div>
        </div>

        <form action="confirm-order" method="post">
            <button type="submit" class="confirm-button">Confirm Order</button>
        </form>
        <div class="back-button">
            <a href="payment-info">Back to Payment</a>
        </div>
    </div>
</body>
</html>

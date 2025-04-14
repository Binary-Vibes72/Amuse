<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <style>
        .container {
            width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            text-align: center;
        }
        h1 {
            color: green;
            margin-bottom: 20px;
        }
        p {
            margin-bottom: 10px;
        }
        .home-link {
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            font-size: 1em;
            text-decoration: none;
            margin-top: 20px;
        }
        .home-link:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Thank You!</h1>
        <p>Your order has been placed successfully.</p>
        <p>You will receive an email confirmation shortly.</p>
        <a href="products" class="home-link">Continue Shopping</a>
    </div>
</body>
</html>
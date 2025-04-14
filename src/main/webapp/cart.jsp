<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <style>
        .cart-item {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
        }
        .cart-item img {
            max-width: 80px;
            height: auto;
            margin-right: 10px;
        }
        .cart-item-details {
            flex-grow: 1;
        }
        .quantity-input {
            width: 50px;
            padding: 5px;
            margin-left: 10px;
        }
    </style>
</head>
<body>
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
                        <p><a href="remove-from-cart?productId=${item.product.productId}">Remove</a></p>
                    </div>
                </div>
            </c:forEach>
            <button type="submit">Update Cart</button>
            <hr>
            <p><strong>Total Items:</strong> ${totalQuantity}</p>
            <p><strong>Total Price:</strong> $${totalPrice}</p>
            <br>
            <a href="shipping-info">Proceed to Checkout</a>
        </form>
    </c:if>

    <c:if test="${empty cart}">
        <p>Your cart is empty.</p>
    </c:if>

    <br>
    <a href="products">Continue Shopping</a>
</body>
</html>
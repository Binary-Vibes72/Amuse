package com.example.controller;

import com.example.model.CartItem;
import com.example.model.Order; // We'll create this model class later
import com.example.model.OrderItem; // We'll create this model class later
import com.example.model.User; // Assuming you have a User model
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/confirm-order")
public class ConfirmOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null && session.getAttribute("cart") != null &&
            session.getAttribute("shippingName") != null && session.getAttribute("paymentCardNumber") != null) {

            User loggedInUser = (User) session.getAttribute("loggedInUser");
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");
            String shippingName = (String) session.getAttribute("shippingName");
            String shippingAddress = (String) session.getAttribute("shippingAddress");
            String shippingCity = (String) session.getAttribute("shippingCity");
            String shippingState = (String) session.getAttribute("shippingState");
            String shippingZipCode = (String) session.getAttribute("shippingZipCode");
            String shippingCountry = (String) session.getAttribute("shippingCountry");
            String shippingEmail = (String) session.getAttribute("shippingEmail");
            String paymentCardNumber = (String) session.getAttribute("paymentCardNumber");

            // In a real application, you would:
            // 1. Save the order details to the database.
            // 2. Process the payment (if not already done).
            // 3. Potentially send confirmation emails.

            // For this example, we'll just simulate saving and clear the cart
            boolean orderSaved = simulateSaveOrder(loggedInUser, cartItems, shippingName, shippingAddress, shippingCity, shippingState, shippingZipCode, shippingCountry, shippingEmail, paymentCardNumber);

            if (orderSaved) {
                session.removeAttribute("cart"); // Clear the cart
                // Optionally remove other session attributes related to the order
                response.sendRedirect("order-confirmation");
            } else {
                request.setAttribute("errorMessage", "Error confirming your order. Please try again.");
                request.getRequestDispatcher("/review_order.jsp").forward(request, response);
            }

        } else {
            // Redirect back to the cart if necessary information is missing
            response.sendRedirect("cart");
        }
    }

    // Simulate saving the order to the database
    private boolean simulateSaveOrder(User user, List<CartItem> cartItems, String shippingName, String shippingAddress, String shippingCity, String shippingState, String shippingZipCode, String shippingCountry, String shippingEmail, String paymentCardNumber) {
        Order order = new Order(user, new Date(), shippingName, shippingAddress, shippingCity, shippingState, shippingZipCode, shippingCountry, shippingEmail, paymentCardNumber, calculateTotal(cartItems));
        System.out.println("Order simulated for user: " + order.getUser().getUsername() + " on " + order.getOrderDate());
        System.out.println("Shipping to: " + order.getShippingName() + ", " + order.getShippingAddress());
        System.out.println("Payment with: " + order.getPaymentCardNumber());
        System.out.println("Items:");
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem(order, item.getProduct(), item.getQuantity(), item.getProduct().getPrice());
            System.out.println("- " + orderItem.getProduct().getName() + " (Qty: " + orderItem.getQuantity() + ", Price: $" + orderItem.getUnitPrice() + ")");
        }
        return true;
    }

    private double calculateTotal(List<CartItem> cartItems) {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }
}
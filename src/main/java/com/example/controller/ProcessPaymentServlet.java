package com.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/process-payment")
public class ProcessPaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");

        // Basic payment validation (in a real app, you'd integrate with a payment gateway)
        if (cardNumber == null || cardNumber.trim().isEmpty() || expiryDate == null || expiryDate.trim().isEmpty() || cvv == null || cvv.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All payment information fields are required.");
            request.getRequestDispatcher("/payment_info.jsp").forward(request, response);
            return;
        }

        // For simplicity, we'll just store the payment info in the session (DO NOT do this in a real application)
        HttpSession session = request.getSession();
        session.setAttribute("paymentCardNumber", "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4)); // Masking for display
        session.setAttribute("paymentExpiryDate", expiryDate);

        // Redirect to the order review page
        response.sendRedirect("review-order");
    }
}
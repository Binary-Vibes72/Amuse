package com.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/payment-info")
public class PaymentInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null && session.getAttribute("cart") != null && !((java.util.List) session.getAttribute("cart")).isEmpty() &&
            session.getAttribute("shippingName") != null) {
            // User has shipping info, forward to payment info page
            request.getRequestDispatcher("/payment_info.jsp").forward(request, response);
        } else {
            // Redirect to shipping info page if shipping details are missing
            response.sendRedirect("shipping-info");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
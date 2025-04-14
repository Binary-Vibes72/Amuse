package com.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/shipping-info")
public class ShippingInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null && session.getAttribute("cart") != null && !((java.util.List) session.getAttribute("cart")).isEmpty()) {
            // User is logged in and has items in the cart, forward to shipping info page
            request.getRequestDispatcher("/shipping_info.jsp").forward(request, response);
        } else {
            // Redirect to login or cart page if conditions are not met
            response.sendRedirect("cart"); // Or you could redirect to login
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
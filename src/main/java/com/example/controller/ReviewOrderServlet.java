package com.example.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.example.model.CartItem;

@WebServlet("/review-order")
public class ReviewOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null && session.getAttribute("cart") != null &&
            session.getAttribute("shippingName") != null && session.getAttribute("paymentCardNumber") != null) {

            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            int totalQuantity = 0;
            double totalPrice = 0.0;
            if (cart != null) {
                for (CartItem item : cart) {
                    totalQuantity += item.getQuantity();
                    totalPrice += item.getQuantity() * item.getProduct().getPrice();
                }
            }

            request.setAttribute("cart", cart);
            request.setAttribute("totalQuantity", totalQuantity);
            request.setAttribute("totalPrice", String.format("%.2f", totalPrice));
            request.setAttribute("shippingName", session.getAttribute("shippingName"));
            request.setAttribute("shippingAddress", session.getAttribute("shippingAddress"));
            request.setAttribute("shippingCity", session.getAttribute("shippingCity"));
            request.setAttribute("shippingState", session.getAttribute("shippingState"));
            request.setAttribute("shippingZipCode", session.getAttribute("shippingZipCode"));
            request.setAttribute("shippingCountry", session.getAttribute("shippingCountry"));
            request.setAttribute("shippingEmail", session.getAttribute("shippingEmail"));
            request.setAttribute("paymentCardNumber", session.getAttribute("paymentCardNumber"));
            request.setAttribute("paymentExpiryDate", session.getAttribute("paymentExpiryDate"));

            request.getRequestDispatcher("/review_order.jsp").forward(request, response);
        } else {
            response.sendRedirect("cart"); // Redirect if necessary information is missing
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
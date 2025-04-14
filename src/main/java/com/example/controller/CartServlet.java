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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
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
        request.setAttribute("totalPrice", String.format("%.2f", totalPrice)); // Format to 2 decimal places
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
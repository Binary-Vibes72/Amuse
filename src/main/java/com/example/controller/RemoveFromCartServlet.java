package com.example.controller;

import com.example.model.CartItem;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        if (productIdStr != null && !productIdStr.isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdStr);
                HttpSession session = request.getSession();
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

                if (cart != null) {
                    // Use an Iterator to safely remove items while iterating
                    Iterator<CartItem> iterator = cart.iterator();
                    while (iterator.hasNext()) {
                        CartItem item = iterator.next();
                        if (item.getProduct().getProductId() == productId) {
                            iterator.remove();
                            break; // Assuming only one item with the same productId
                        }
                    }
                    // Redirect back to the cart page
                    response.sendRedirect("cart");
                    return;
                }
            } catch (NumberFormatException e) {
                // Invalid productId, can log this or show an error
                e.printStackTrace();
            }
        }
        // If productId is missing or invalid, redirect back to the cart page
        response.sendRedirect("cart");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
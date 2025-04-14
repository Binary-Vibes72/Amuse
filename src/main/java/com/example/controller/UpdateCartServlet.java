package com.example.controller;

import com.example.model.CartItem;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (CartItem item : cart) {
                String quantityParamName = "quantity-" + item.getProduct().getProductId();
                if (parameterMap.containsKey(quantityParamName)) {
                    String[] quantities = parameterMap.get(quantityParamName);
                    if (quantities != null && quantities.length > 0) {
                        try {
                            int newQuantity = Integer.parseInt(quantities[0]);
                            if (newQuantity > 0) {
                                item.setQuantity(newQuantity);
                            } else {
                                // If quantity is 0 or negative, you might want to remove the item
                                // For now, we'll just set it to 1 (or you could implement removal here)
                                item.setQuantity(1);
                            }
                        } catch (NumberFormatException e) {
                            // Handle invalid quantity input (e.g., log it)
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // Redirect back to the cart page to display the updated cart
        response.sendRedirect("cart");
    }
}
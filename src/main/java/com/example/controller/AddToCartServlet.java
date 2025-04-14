package com.example.controller;

import com.example.model.CartItem;
import com.example.model.Product;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        if (productIdStr != null && !productIdStr.isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdStr);
                Product product = getProductDetails(productId);

                if (product != null) {
                    HttpSession session = request.getSession();
                    // Get the current cart from the session or create a new one
                    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
                    if (cart == null) {
                        cart = new ArrayList<>();
                        session.setAttribute("cart", cart);
                    }

                    // Add the product to the cart (for simplicity, we add a new CartItem with quantity 1)
                    CartItem newItem = new CartItem(product, 1);
                    cart.add(newItem);

                    // Redirect back to the product list page
                    response.sendRedirect("products");
                    return;
                } else {
                    request.setAttribute("errorMessage", "Product not found.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }

            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid product ID.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        } else {
            request.setAttribute("errorMessage", "Product ID not provided.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
    }

    private Product getProductDetails(int productId) {
        Product product = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT product_id, name, description, price, image_url FROM products WHERE product_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String imageUrl = rs.getString("image_url");
                product = new Product(id, name, description, price, imageUrl);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException se2) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException se2) {}
            try { if (conn != null) conn.close(); } catch (SQLException se) {}
        }
        return product;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
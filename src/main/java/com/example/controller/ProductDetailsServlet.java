package com.example.controller;

import com.example.model.Product;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class ProductDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("id");
        if (productIdStr != null && !productIdStr.isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdStr);
                Product product = getProductDetails(productId);

                if (product != null) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/product_details.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Product not found.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }

            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid product ID.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Product ID not provided.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
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
                product = new Product(id, name, description, price, imageUrl); // Use the constructor with productId
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
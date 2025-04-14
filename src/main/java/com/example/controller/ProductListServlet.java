package com.example.controller;

import java.sql.Statement; // Add this import
import com.example.model.Product;
import com.example.model.User; // Import the User model
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            // User is logged in, proceed to fetch and display products
            List<Product> productList = new ArrayList<>();
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String sql = "SELECT product_id, name, description, price, image_url FROM products";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("product_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    String imageUrl = rs.getString("image_url");
                    Product product = new Product(id, name, description, price, imageUrl);
                    productList.add(product);
                }

                request.setAttribute("products", productList);
                request.getRequestDispatcher("/product_list.jsp").forward(request, response);

            } catch (SQLException se) {
                se.printStackTrace();
                request.setAttribute("errorMessage", "Error fetching products from the database.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "MySQL JDBC Driver not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } finally {
                try { if (rs != null) rs.close(); } catch (SQLException se2) {}
                try { if (stmt != null) stmt.close(); } catch (SQLException se2) {}
                try { if (conn != null) conn.close(); } catch (SQLException se) {}
            }
        } else {
            // User is not logged in, redirect to the login page
            response.sendRedirect("login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
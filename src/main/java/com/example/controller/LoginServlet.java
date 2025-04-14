package com.example.controller;

import com.example.model.User; // We might create a User model later
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
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMessage = null;

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            errorMessage = "Please enter both username and password.";
        } else {
            User user = authenticateUser(username, password);
            if (user != null) {
                // Authentication successful, create a session
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", user); // Store user object in session
                response.sendRedirect("products"); // Redirect to the product list page
                return;
            } else {
                errorMessage = "Invalid username or password.";
            }
        }

        // If authentication failed, redirect back to the login page with an error message
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private User authenticateUser(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT user_id, username, password, email FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    // Passwords match, create a User object
                    user = new User(); // For now, a basic User object
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    // We don't store the password in the User object for security
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException se2) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException se2) {}
            try { if (conn != null) conn.close(); } catch (SQLException se) {}
        }
        return user;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For now, just forward to the login page for GET requests
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
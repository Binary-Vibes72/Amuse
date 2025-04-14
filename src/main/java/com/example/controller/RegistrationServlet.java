package com.example.controller;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt; // For password hashing

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String errorMessage = null;

        // Basic input validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            errorMessage = "All fields are required.";
        } else if (password.length() < 6) {
            errorMessage = "Password must be at least 6 characters long.";
        } else if (!email.contains("@")) {
            errorMessage = "Invalid email format.";
        } else {
            // Check if username or email already exists
            if (isUsernameOrEmailTaken(username, email)) {
                errorMessage = "Username or email already exists.";
            } else {
                // Hash the password securely using bcrypt
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                // Insert user into the database
                if (registerUser(username, hashedPassword, email)) {
                    // Registration successful, redirect to login page (we'll create this later)
                	response.sendRedirect("login?registrationSuccess=true");
                    return;
                } else {
                    errorMessage = "Registration failed. Please try again.";
                }
            }
        }

        // If there was an error, redirect back to the registration page with an error message
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    private boolean isUsernameOrEmailTaken(String username, String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT user_id FROM users WHERE username = ? OR email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a user with the username or email exists
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return true; // Assume taken to prevent registration in case of error
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException se2) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException se2) {}
            try { if (conn != null) conn.close(); } catch (SQLException se) {}
        }
    }

    private boolean registerUser(String username, String hashedPassword, String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)"; // Added 'role'
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, email);
            pstmt.setString(4, "admin"); // Set the default role
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException se2) { se2.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException se) { se.printStackTrace(); }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For now, we'll just forward to the registration page if a GET request is made
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
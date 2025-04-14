package com.example.controller;

import com.example.model.User;
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

                // Check the user's role and redirect accordingly
                if ("admin".equals(user.getRole())) {
                    response.sendRedirect("admin/dashboard"); // Redirect to the admin dashboard
                } else {
                    response.sendRedirect("products"); // Redirect to the product list page for other users
                }
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
        User user = null;
        String sql = "SELECT user_id, username, password, email, role FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");
                    String roleFromDB = rs.getString("role");
                    System.out.println("Role retrieved from DB for user " + username + ": " + roleFromDB);
                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        // Passwords match, create a User object with the role
                        user = new User(
                                rs.getInt("user_id"),
                                rs.getString("username"),
                                storedHashedPassword, // We don't need the plain password anymore
                                rs.getString("email"),
                                rs.getString("role")
                        );
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For now, just forward to the login page for GET requests
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
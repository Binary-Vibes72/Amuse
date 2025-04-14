package com.example.controller;

import com.example.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        System.out.println("Session in AdminDashboardServlet: " + session);
        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            System.out.println("loggedInUser in AdminDashboardServlet: " + loggedInUser);
            if (loggedInUser != null) {
                System.out.println("loggedInUser Role: " + loggedInUser.getRole());
                if ("admin".equals(loggedInUser.getRole())) {
                    System.out.println("Admin role detected, forwarding to dashboard.");
                    request.getRequestDispatcher("/admin_dashboard.jsp").forward(request, response);
                    return; // Important to return after forwarding
                } else {
                    System.out.println("Non-admin role (" + loggedInUser.getRole() + ") detected, redirecting to products.");
                    response.sendRedirect("../products");
                    return; // Important to return after redirecting
                }
            } else {
                System.out.println("loggedInUser is null, redirecting to login.");
                response.sendRedirect("../login.jsp");
                return; // Important to return after redirecting
            }
        } else {
            System.out.println("No session found, redirecting to login.");
            response.sendRedirect("../login.jsp");
            return; // Important to return after redirecting
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
package com.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/process-shipping")
public class ProcessShippingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");
        String country = request.getParameter("country");
        String email = request.getParameter("email");

        // Basic validation (you might want to add more robust validation)
        if (name == null || name.trim().isEmpty() || address == null || address.trim().isEmpty() ||
            city == null || city.trim().isEmpty() || state == null || state.trim().isEmpty() ||
            zipCode == null || zipCode.trim().isEmpty() || country == null || country.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All shipping information fields are required.");
            request.getRequestDispatcher("/shipping_info.jsp").forward(request, response);
            return;
        }

        // Store the shipping information in the session
        HttpSession session = request.getSession();
        session.setAttribute("shippingName", name);
        session.setAttribute("shippingAddress", address);
        session.setAttribute("shippingCity", city);
        session.setAttribute("shippingState", state);
        session.setAttribute("shippingZipCode", zipCode);
        session.setAttribute("shippingCountry", country);
        session.setAttribute("shippingEmail", email);

        // Redirect to the payment information page (which we will create next)
        response.sendRedirect("payment-info");
    }
}
package com.example.controller;

import com.example.model.Product;
import com.example.model.User;
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
import org.apache.commons.dbutils.DbUtils;

@WebServlet("/admin/products/manage")
public class ProductManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if ("admin".equals(loggedInUser.getRole())) {
                // Fetch products for display in the management page
                List<Product> products = getProductsForManagement(request, response);
                if (products != null) {
                    request.setAttribute("products", products);
                    request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("../../products"); // Redirect non-admins to product list
            }
        } else {
            response.sendRedirect("../../login.jsp"); // Redirect to login if no session
        }
    }

    private List<Product> getProductsForManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> productList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT product_id, name, description, price, image_url FROM products";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                productList.add(product);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching products: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return null;
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(conn);
        }
        return productList;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if ("admin".equals(loggedInUser.getRole())) {
                String action = request.getParameter("action");
                if ("add".equalsIgnoreCase(action)) {
                    addProduct(request, response);
                } else if ("edit".equalsIgnoreCase(action)) {
                    editProduct(request, response);
                } else if ("delete".equalsIgnoreCase(action)) {
                    deleteProduct(request, response);
                } else {
                    // Handle invalid action
                    request.setAttribute("errorMessage", "Invalid action: " + action);
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("../../products"); //redirect non admin
            }
        } else {
            response.sendRedirect("../../login.jsp");
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String imageUrl = request.getParameter("imageUrl");

        if (name == null || name.trim().isEmpty() || description == null || description.trim().isEmpty()
                || priceStr == null || priceStr.trim().isEmpty() || imageUrl == null || imageUrl.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                request.setAttribute("errorMessage", "Price must be a positive number.");
                request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid price format.");
            request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO products (name, description, price, image_url) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setString(4, imageUrl);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                response.sendRedirect("manage"); // Redirect to product management page
            } else {
                request.setAttribute("errorMessage", "Failed to add product.");
                request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error adding product: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(conn);
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String imageUrl = request.getParameter("imageUrl");

        if (idStr == null || idStr.trim().isEmpty() || name == null || name.trim().isEmpty() || description == null || description.trim().isEmpty()
                || priceStr == null || priceStr.trim().isEmpty() || imageUrl == null || imageUrl.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid product ID.");
            request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                request.setAttribute("errorMessage", "Price must be a positive number.");
                request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid price format.");
            request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "UPDATE products SET name=?, description=?, price=?, image_url=? WHERE product_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setString(4, imageUrl);
            pstmt.setInt(5, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                response.sendRedirect("manage"); // Redirect to product management page
            } else {
                request.setAttribute("errorMessage", "Failed to edit product. Product may not exist.");
                request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error editing product: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(conn);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Product ID is required.");
            request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid product ID.");
            request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "DELETE FROM products WHERE product_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                response.sendRedirect("manage"); // Redirect to product management page
            } else {
                request.setAttribute("errorMessage", "Failed to delete product. Product may not exist.");
                request.getRequestDispatcher("/admin/product_management.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error deleting product: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(conn);
        }
    }
}
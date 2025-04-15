<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .dashboard-container {
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 500px;
            max-width: 100%;
            box-sizing: border-box;
            text-align: center;
        }

        .dashboard-title {
            margin-bottom: 30px;
            color: #333;
            font-weight: 700;
            font-size: 28px;
        }

        .dashboard-menu {
            list-style: none;
            padding: 0;
            margin-bottom: 20px;
        }

        .dashboard-menu li {
            margin-bottom: 15px;
        }

        .dashboard-menu a {
            display: block;
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 18px;
            transition: background-color 0.3s ease;
        }

        .dashboard-menu a:hover {
            background-color: #45a049;
        }

        .logout-button { /* Added style for the logout button */
            margin-top: 20px;
        }

        .logout-button a {
            display: inline-block;
            background-color: #f44336;
            color: white;
            padding: 12px 20px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 18px;
            transition: background-color 0.3s ease;
        }

        .logout-button a:hover {
            background-color: #d32f2f;
        }

    </style>
</head>
<body>
    <div class="dashboard-container">
        <h1 class="dashboard-title">Admin Dashboard</h1>
        <ul class="dashboard-menu">
            <li><a href="<%= request.getContextPath() %>/products">View Products</a></li>
            <li><a href="<%= request.getContextPath() %>/admin/products/manage">Manage Products</a></li>
        </ul>
        <div class="logout-button">
             <a href="<%= request.getContextPath() %>/logout">Logout</a>
        </div>
    </div>
</body>
</html>
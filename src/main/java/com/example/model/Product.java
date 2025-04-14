package com.example.model;

public class Product {
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int productId;
    
    // Constructor
    public Product(int productId, String name, String description, double price, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters (we'll need these to access the product data)
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getProductId() {
    	return productId;
    }

    // Setters (optional, depending on whether you need to modify product data after creation)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
}
package com.ecommerce;

public class Product {
    private final int productID;
    private final String name;
    private final double price;
    private final String description;
    private final int stockQuantity;

    public Product(int productID, String name, double price, String description, int stockQuantity) {
        if (productID <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        this.productID = productID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    // Getters
    public int getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public boolean isAvailable() {
        return stockQuantity > 0;
    }

    @Override
    public String toString() {
        return String.format("Product[ID=%d, Name='%s', Price=$%.2f, Stock=%d, Description='%s']",
                productID, name, price, stockQuantity, description);
    }
}

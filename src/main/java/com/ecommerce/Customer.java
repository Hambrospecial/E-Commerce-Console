package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final int customerID;
    private final String name;
    private final List<Product> shoppingCart;
    private static final int MAX_CART_ITEMS = 10;

    public Customer(int customerID, String name) {
        if (customerID <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        this.customerID = customerID;
        this.name = name;
        this.shoppingCart = new ArrayList<>();
    }

    public void addToCart(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (!product.isAvailable()) {
            throw new IllegalStateException("Product is out of stock: " + product.getName());
        }
        if (shoppingCart.size() >= MAX_CART_ITEMS) {
            throw new IllegalStateException("Shopping cart cannot contain more than " + MAX_CART_ITEMS + " items");
        }

        shoppingCart.add(product);
    }

    public void removeFromCart(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (!shoppingCart.remove(product)) {
            throw new IllegalStateException("Product not found in cart: " + product.getName());
        }
    }

    public double calculateCartTotal() {
        return shoppingCart.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public List<Product> getShoppingCart() {
        return new ArrayList<>(shoppingCart);
    }

    public void clearCart() {
        shoppingCart.clear();
    }

    public int getCartSize() {
        return shoppingCart.size();
    }

    // Getters
    public int getCustomerID() { return customerID; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("Customer[ID=%d, Name='%s', Cart Items=%d]",
                customerID, name, shoppingCart.size());
    }
}

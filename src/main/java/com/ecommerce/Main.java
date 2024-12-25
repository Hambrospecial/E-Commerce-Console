package com.ecommerce;

import com.ecommerce.orders.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ecommerce.orders.OrderStatus.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Product> products = new ArrayList<>();
    private static Customer customer;

    public static void main(String[] args) {
        initializeProducts();
        try {
            createCustomer();
            while (true) {
                System.out.println("\n======= Menu =======");
                System.out.println("1. 📚 Browse Products");
                System.out.println("2. 🛒 Add to Cart");
                System.out.println("3. 🗂 View Cart");
                System.out.println("4. 📦 Place Order");
                System.out.println("5. ⏹ Exit");
                System.out.print("Select an option: ");

                int choice = readInt();
                switch (choice) {
                    case 1 -> browseProducts();
                    case 2 -> addToCart();
                    case 3 -> viewCart();
                    case 4 -> placeOrder();
                    case 5 -> {
                        System.out.println("Thank you for shopping with us!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void initializeProducts() {
        products.add(new Product(1, "Gaming Laptop", 1299.99, "High-performance gaming laptop with RTX 3080", 10));
        products.add(new Product(2, "iPhone 15 Pro", 999.99, "Latest Apple smartphone", 20));
        products.add(new Product(3, "iPad Pro", 799.99, "12.9-inch iPad Pro with M2 chip", 15));
    }

    private static void createCustomer() {
        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();
        customer = new Customer(1, name);
        System.out.println("Customer created successfully: " + customer);
    }

    private static void browseProducts() {
        System.out.println("\n=== Available Products ===");
        products.forEach(System.out::println);
    }

    private static void addToCart() {
        browseProducts();
        System.out.print("Enter Product ID to add to cart: ");
        int productID = readInt();
        Product product = products.stream()
                .filter(p -> p.getProductID() == productID)
                .findFirst()
                .orElse(null);

        if (product == null) {
            System.out.println("Invalid Product ID.");
            return;
        }

        try {
            customer.addToCart(product);
            System.out.println("Added to cart: " + product.getName());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void viewCart() {
        System.out.println("\n=== Shopping Cart ===");
        if (customer.getCartSize() == 0) {
            System.out.println("Your cart is empty.");
            return;
        }

        customer.getShoppingCart().forEach(item ->
                System.out.printf("- %s: $%.2f%n", item.getName(), item.getPrice()));
        System.out.printf("Cart Total: $%.2f%n", customer.calculateCartTotal());
    }

    private static void placeOrder() {
        if (customer.getCartSize() == 0) {
            System.out.println("Your cart is empty. Add items before placing an order.");
            return;
        }

        Order order = new Order(1, customer, customer.getShoppingCart());
        order.updateStatus(CONFIRMED.name());
        System.out.println(order.generateOrderSummary());

        customer.clearCart();
        System.out.println("\nOrder placed successfully. Your cart has been cleared.");
    }

    private static int readInt() {
        int attempts = 0;
        int maxAttempts = 3;
        do {
            if (!scanner.hasNextInt()) {
                attempts++;
                System.out.printf("Invalid input. Please enter a valid number (Attempt %d of %d): ", attempts, maxAttempts);
                scanner.next();
            } else {
                return scanner.nextInt();
            }
        } while (attempts < maxAttempts);

        throw new IllegalStateException("Maximum attempts reached. No valid input provided.");
    }
}
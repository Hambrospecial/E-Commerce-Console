package com.ecommerce.orders;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static com.ecommerce.orders.OrderStatus.*;

public class Order {
    private final int orderID;
    private final Customer customer;
    private final List<Product> products;
    private final double totalAmount;
    private final LocalDateTime orderDate;
    private String status;

    public Order(int orderID, Customer customer, List<Product> products) {
        if (orderID <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product");
        }

        this.orderID = orderID;
        this.customer = customer;
        this.products = new ArrayList<>(products);
        this.totalAmount = calculateTotal();
        this.orderDate = LocalDateTime.now();
        this.status = CANCELLED.name();
    }

    private double calculateTotal() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public void updateStatus(String newStatus) {
        if (!isValidStatus(newStatus)) {
            throw new IllegalArgumentException("Invalid order status: " + newStatus);
        }
        this.status = newStatus;
    }

    private static boolean isValidStatus(String status) {
        return Arrays.stream(OrderStatus.values())
                .anyMatch(item -> item.name().equalsIgnoreCase(status));
    }

    public String generateOrderSummary() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder summary = new StringBuilder();
        summary.append("\n=================================\n");
        summary.append("           ORDER SUMMARY          \n");
        summary.append("=================================\n");
        summary.append(String.format("Order ID: %d\n", orderID));
        summary.append(String.format("Customer: %s (ID: %d)\n",
                customer.getName(), customer.getCustomerID()));
        summary.append("---------------------------------\n");
        summary.append("Products:\n");

        products.forEach(product ->
                summary.append(String.format("  - %-20s $%8.2f\n",
                        product.getName(), product.getPrice()))
        );

        summary.append("---------------------------------\n");
        summary.append(String.format("Total Amount: $%.2f\n", totalAmount));
        summary.append(String.format("Status: %s\n", status));
        summary.append(String.format("Order Date: %s\n", orderDate.format(formatter)));
        summary.append("=================================\n");

        return summary.toString();
    }
}


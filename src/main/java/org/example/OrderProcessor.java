package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessor {
    private ShippingService shippingService;

    public OrderProcessor(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.err.println("Error: Cart is empty.");
            return;
        }

        double subtotal = 0.0;
        double totalShippingWeight = 0.0;

        List<Shippable> itemsToShip = new ArrayList<>();
        Map<Product, Integer> currentCartItems = new HashMap<>(cart.getItems());

        for (Map.Entry<Product, Integer> entry : currentCartItems.entrySet()) {
            Product product = entry.getKey();
            int quantityInCart = entry.getValue();

            if (product.getQuantity() < quantityInCart) {
                System.err.println("Error: " + product.getName() + " is out of stock. Available: " + product.getQuantity());
                return;
            }

            if (product.isExpired()) {
                System.err.println("Error: " + product.getName() + " is expired.");
                return;
            }

            subtotal += product.getPrice() * quantityInCart;

            if (product instanceof Shippable) {
                itemsToShip.add((Shippable) product);
                totalShippingWeight += ((Shippable) product).getWeight() * quantityInCart;
            }
        }

        double shippingFees = shippingService.calculateShippingFee(totalShippingWeight);
        double paidAmount = subtotal + shippingFees;

        if (customer.getBalance() < paidAmount) {
            System.err.println("Error: Insufficient balance. Customer has " + String.format("%.2f", customer.getBalance()) + "EGP, needs " + String.format("%.2f", paidAmount));
        }

        try {
            for (java.util.Map.Entry<Product, Integer> entry : currentCartItems.entrySet()) {
                Product product = entry.getKey();
                int quantityInCart = entry.getValue();
                product.decreaseQuantity(quantityInCart);
            }

            customer.deductBalance(paidAmount);
        } catch (IllegalArgumentException e) {
            System.err.println("An unexpected error occurred during payment processing: " + e.getMessage());
        }

        System.out.println("\n** Checkout receipt **");
        for (java.util.Map.Entry<Product, Integer> entry : currentCartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("  %dx %s $%.2f%n", quantity, product.getName(), product.getPrice() * quantity);
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal $%.2f%n", subtotal);
        System.out.printf("Shipping $%.2f%n", shippingFees);
        System.out.printf("Amount $%.2f%n", paidAmount);
        System.out.printf("Customer current balance after payment: $%.2f%n", customer.getBalance());

        // Send shippable items to ShippingService
        if (!itemsToShip.isEmpty()) {
            shippingService.shipItems(itemsToShip);
        }

        cart.clear();
    }
}
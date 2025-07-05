package org.example;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Product cheese = new ExpirableShippableProduct("Cheese", 100.0, 5, LocalDate.now().plusDays(10), 0.2);
        Product biscuits = new ExpirableShippableProduct("Biscuits", 75.0, 10, LocalDate.now().plusDays(5), 0.7);
        Product tv = new NonExpirableShippableProduct("Smart TV", 1200.0, 2, 15.0);
        Product mobileScratchCard = new NonExpirableProduct("Mobile Scratch Card", 50.0, 20);

        Customer john = new Customer("John Doe", 2000.0);
        Customer jane = new Customer("Jane Smith", 100.0);

        ShippingService shippingService = new ShippingService();
        OrderProcessor orderProcessor = new OrderProcessor(shippingService);

        System.out.println("--- Test Case 1: Successful Checkout ---");
        Cart cart1 = new Cart();

        try {
            cart1.addProduct(cheese, 2);
            cart1.addProduct(tv, 1);
            cart1.addProduct(mobileScratchCard, 1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding to cart: " + e.getMessage());
        }
        orderProcessor.checkout(john, cart1);
        System.out.println("\nCustomer John's balance after checkout: $" + String.format("%.2f", john.getBalance()));
        System.out.println("Cheese remaining: " + cheese.getQuantity());
        System.out.println("Smart TV remaining: " + tv.getQuantity());
        System.out.println("Mobile Scratch Card remaining: " + mobileScratchCard.getQuantity());

        System.out.println("\n--- Test Case 2: Insufficient Balance ---");
        Cart cart2 = new Cart();

        try {
            cart2.addProduct(tv, 2);
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding to cart: " + e.getMessage());
        }

        orderProcessor.checkout(jane, cart2);

        System.out.println("\n--- Test Case 3: Product Out of Stock ---");
        Cart cart3 = new Cart();

        try {
            cart3.addProduct(cheese, 10); // Only 3 left after first checkout
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding to cart: " + e.getMessage());
        }

        cart3.addProduct(cheese, 10);

        System.out.println("Manually setting TV quantity to 0 for out of stock test.");
        tv.decreaseQuantity(tv.getQuantity());
        Cart cart4 = new Cart();

        try {
            cart4.addProduct(tv, 1);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught Expected Error: " + e.getMessage());
        }

        Product limitedEdition = new NonExpirableProduct("Limited Edition Item", 500.0, 1);
        Cart cart5 = new Cart();

        try {
            cart5.addProduct(limitedEdition, 1);
            limitedEdition.decreaseQuantity(1);
            cart5.addProduct(limitedEdition, 1);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught Expected Error when adding to cart: " + e.getMessage());
        }

        Cart cart6 = new Cart();

        try {
            cart6.addProduct(limitedEdition, 1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding to cart: " + e.getMessage());
        }

        limitedEdition.decreaseQuantity(1);
        System.out.println("\n--- Test Case 3 (Revised): Product Out of Stock at Checkout ---");
        orderProcessor.checkout(john, cart6);

        System.out.println("\n--- Test Case 4: Expired Product ---");
        Product expiredMilk = new ExpirableProduct("Milk", 20.0, 5, LocalDate.now().minusDays(1));
        Cart cart7 = new Cart();

        try {
            cart7.addProduct(expiredMilk, 1);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught Expected Error: " + e.getMessage());
        }

        System.out.println("\n--- Test Case 5: Empty Cart ---");
        Cart cart8 = new Cart();
        orderProcessor.checkout(john, cart8);

        System.out.println("\n--- Test Case 6: Mixed Shippable and Non-Shippable Items (similar to first test but explicit) ---");

//         Reset quantities for demonstration
        cheese.increaseQuantity(2);
        tv.increaseQuantity(tv.getQuantity());
        mobileScratchCard.increaseQuantity(0);
        biscuits.increaseQuantity(0);

        Cart cart9 = new Cart();

        try {
            cart9.addProduct(cheese, 2);
            cart9.addProduct(biscuits, 1);
            cart9.addProduct(mobileScratchCard, 3);
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding to cart: " + e.getMessage());
        }
        orderProcessor.checkout(john, cart9);
        System.out.println("\nCustomer John's balance after checkout: $" + String.format("%.2f", john.getBalance()));
        System.out.println("Cheese remaining: " + cheese.getQuantity());
        System.out.println("Biscuits remaining: " + biscuits.getQuantity());
        System.out.println("Mobile Scratch Card remaining: " + mobileScratchCard.getQuantity());
    }
}
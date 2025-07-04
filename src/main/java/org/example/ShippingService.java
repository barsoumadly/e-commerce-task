package org.example;

import java.util.List;

public class ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 30.0;

    public void shipItems(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return;
        }

        System.out.println("** Shipment notice **");
        double totalWeight = 0.0;

        for (Shippable item : shippableItems) {
            System.out.println(String.format("  1x %s %.2fg", item.getName(), item.getWeight() * 1000)); // Display in grams
            totalWeight += item.getWeight();
        }

        System.out.println(String.format("Total package weight %.2fkg", totalWeight));
    }

    public double calculateShippingFee(double totalWeight) {
        return totalWeight * SHIPPING_RATE_PER_KG;
    }
}
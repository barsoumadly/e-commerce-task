package org.example;

public class NonExpirableShippableProduct extends NonExpirableProduct implements Shippable {
    private double weight;

    public NonExpirableShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getShippingWeight() {
        return weight;
    }
}

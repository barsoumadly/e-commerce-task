package org.example;

public abstract class Product {
    private String name;

    private Double price;

    private Integer quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void decreaseQuantity(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
        } else {
            throw new IllegalArgumentException("Cannot decrease quantity beyond available stock.");
        }
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public abstract boolean isExpired();

    public double getShippingWeight() {
        return 0.0;
    }

    @Override
    public String toString() {
        return name + " (Price: " + price + " EGP)";
    }
}

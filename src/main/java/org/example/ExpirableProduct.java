package org.example;

public class ExpirableProduct extends Product {
    private java.time.LocalDate expiryDate;

    public ExpirableProduct(String name, double price, int quantity, java.time.LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean isExpired() {
        return java.time.LocalDate.now().isAfter(expiryDate);
    }

    public java.time.LocalDate getExpiryDate() {
        return expiryDate;
    }
}

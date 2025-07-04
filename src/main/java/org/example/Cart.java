package org.example;

public class Cart {
    private java.util.Map<Product, Integer> items;

    public Cart() {
        this.items = new java.util.HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException(product.getName() + " is out of stock. Available: " + product.getQuantity());
        }
        if (product.isExpired()) {
            throw new IllegalArgumentException(product.getName() + " is expired and cannot be added to cart.");
        }

        items.merge(product, quantity, Integer::sum);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public java.util.Map<Product, Integer> getItems() {
        return java.util.Collections.unmodifiableMap(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}

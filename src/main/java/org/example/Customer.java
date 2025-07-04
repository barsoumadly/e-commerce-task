package org.example;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deductBalance(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance.");
        }
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "Customer: " + name + " (Balance: " + String.format("%.2f", balance) + " EGP)";
    }
}

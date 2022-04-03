package com.enumerators;

public enum OrderStatus {
    ORDERED("Ordered"),
    CANCELED("Order cancelled");

    private String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }
}

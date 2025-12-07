package org.access.entity;

public class ShoppingCart {
    private int shoppingCart_id;
    private int taxRate;
    private double deliveryFee;
    private Receipt receipt;

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public int getShoppingCart_id() {
        return shoppingCart_id;
    }

    public void setShoppingCart_id(int shoppingCart_id) {
        this.shoppingCart_id = shoppingCart_id;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }
}

package org.databases.entity;

public class CartProduct {
    private int cartProduct_id;
    private ShoppingCart shoppingCart;
    private Product product;
    private int quantity;
    private double price;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCartProduct_id() {
        return cartProduct_id;
    }

    public void setCartProduct_id(int cartProduct_id) {
        this.cartProduct_id = cartProduct_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

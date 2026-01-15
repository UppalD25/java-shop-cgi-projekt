package org.interfaces;

import org.databases.entity.ShoppingCart;
import java.util.List;

public interface IShoppingCart {
    // CREATE
    void createShoppingCart(ShoppingCart shoppingCart);

    // READ
    ShoppingCart getShoppingCartById(int shoppingCartId);
    List<ShoppingCart> getAllShoppingCarts();
    List<ShoppingCart> getShoppingCartsByAccountId(int accountId);
    ShoppingCart getActiveShoppingCartByAccountId(int accountId);

    // UPDATE
    void updateShoppingCart(ShoppingCart shoppingCart);
    void updateTaxRate(int shoppingCartId, int taxRate);
    void updateDeliveryFee(int shoppingCartId, double deliveryFee);

    // DELETE
    void deleteShoppingCart(int shoppingCartId);

    // BUSINESS LOGIC
    double calculateTotal(int shoppingCartId);
}
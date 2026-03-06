package org.interfaces;

import org.databases.entity.CartProduct;
import java.util.List;

public interface ICartProduct {
    // CREATE
    void addProductToCart(CartProduct cartProduct);

    CartProduct getCartProductById(int cartProductId);

    // UPDATE
    void updateCartProduct(CartProduct cartProduct);
    void updateQuantity(int cartProductId, int quantity);

    // DELETE
    void removeProductFromCart(int cartProductId);
    void clearCart(int shoppingCartId);

    /*
    später halt

    int getTotalItemsInCart(int shoppingCartId);
    double getCartSubtotal(int shoppingCartId);
    // READ
    List<CartProduct> getCartProductsByShoppingCartId(int shoppingCartId);
    CartProduct getCartProductByShoppingCartAndProduct(int shoppingCartId, int productId);


     */
}

package org.interfaces;

import org.databases.entity.CartProduct;
import java.util.List;

public interface ICartProduct {
    // CREATE
    void addProductToCart(CartProduct cartProduct);

    // READ
    CartProduct getCartProductById(int cartProductId);
    List<CartProduct> getCartProductsByShoppingCartId(int shoppingCartId);
    CartProduct getCartProductByShoppingCartAndProduct(int shoppingCartId, int productId);

    // UPDATE
    void updateCartProduct(CartProduct cartProduct);
    void updateQuantity(int cartProductId, int quantity);

    // DELETE
    void removeProductFromCart(int cartProductId);
    void clearCart(int shoppingCartId);

    // BUSINESS LOGIC
    int getTotalItemsInCart(int shoppingCartId);
    double getCartSubtotal(int shoppingCartId);
}

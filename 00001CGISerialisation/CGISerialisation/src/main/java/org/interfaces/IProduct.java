package org.interfaces;

import org.databases.entity.Product;
import java.util.List;

public interface IProduct {
    // CREATE
    void createProduct(Product product);
    // UPDATE
    void updateProduct(Product product);
    void updateProductPrice(int productId, double newPrice);
    // DELETE
    void deleteProduct(int productId);
    List<Product> searchProductsByName(String name);
    List<Product> getAllProducts();
    Product getProductById(int productId);

    /* später vielleicht

    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);
     */
}
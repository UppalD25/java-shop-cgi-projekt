package org.interfaces;

import org.databases.entity.Product;
import java.util.List;

public interface IProduct {
    // CREATE
    void createProduct(Product product);

    // READ
    Product getProductById(int productId);
    List<Product> getAllProducts();
    List<Product> searchProductsByName(String name);
    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);

    // UPDATE
    void updateProduct(Product product);
    void updateProductPrice(int productId, double newPrice);

    // DELETE
    void deleteProduct(int productId);
}
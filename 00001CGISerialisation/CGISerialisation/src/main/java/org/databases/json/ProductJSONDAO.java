package org.databases.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.databases.entity.Account;
import org.databases.entity.Product;
import org.interfaces.IProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ProductJSONDAO implements IProduct {

    private static final String FILE_PATH = "data/json/products.json";
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createProduct(Product product){
        try {
            List<Product> products = loadAll();

            // Auto-increment der ID so wie bei mysql ..
            int newId = products.isEmpty() ? 1 :
                    products.stream()
                            .mapToInt(Product::getProduct_id)
                            .max().getAsInt() + 1;

            product.setProduct_id(newId);
            products.add(product);

            saveAll(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ
    public Product getProductById(int productId){
        try{
            return loadAll().stream()
                    .filter(p -> p.getProduct_id() == productId)
                    .findFirst()
                    .orElse(null);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> getAllProducts(){
        return loadAll();
    }
    public List<Product> searchProductsByName(String name) {
        if (name == null || name.isEmpty()) {
            return new ArrayList<>();
        }

        return loadAll().stream()
                .filter(p -> p.getName() != null &&
                        p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // UPDATE
    public void updateProduct(Product product){
        try{
            List<Product> products = loadAll();

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProduct_id() == product.getProduct_id()) {
                    products.set(i, product);
                    break;
                }
            }
            saveAll(products);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateProductPrice(int productId, double newPrice){
        try {
            Product product = getProductById(productId);
            if (product != null) {
                product.setPrice(newPrice);
                updateProduct(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // DELETE
    public void deleteProduct(int productId){
        try{
            List<Product> products = loadAll();
            products.removeIf(p -> p.getProduct_id() == productId);
            saveAll(products);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private List<Product> loadAll() {
        try {
            java.io.File file = new java.io.File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            // Liest die Datei und wandelt sie in eine Liste von Product-Objekten um
            return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Product.class));
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der Produkte: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveAll(List<Product> products) {
        try {
            // Schreibt die gesamte Liste zurück in die Datei (formatiert für bessere Lesbarkeit)
            mapper.writerWithDefaultPrettyPrinter().writeValue(new java.io.File(FILE_PATH), products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


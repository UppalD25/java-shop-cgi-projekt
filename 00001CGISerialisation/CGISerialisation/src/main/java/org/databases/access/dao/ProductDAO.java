package org.databases.access.dao;

import org.interfaces.IProduct;

import org.databases.entity.Product;
import org.databases.access.setup.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für Product-Entität
 * Verwaltet Produkte in der MySQL-Datenbank
 */
public class ProductDAO implements IProduct {

    /**
     * Erstellt ein neues Produkt in der Datenbank
     * @param product Das zu speichernde Produkt
     */
    public void createProduct(Product product) {
        String sql = "INSERT INTO Product (Name, Comment, Price) VALUES (?, ?, ?)";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            // Comment ist die Spalte für Produktbeschreibung
            stmt.setString(2, product.getDescriptionText());
            stmt.setDouble(3, product.getPrice());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen des Produkts");
            e.printStackTrace();
        }
    }

    /**
     * Lädt ein Produkt anhand seiner ID
     * @param productId Die ID des gesuchten Produkts
     * @return Product-Objekt oder null wenn nicht gefunden
     */
    public Product getProductById(int productId) {
        String sql = "SELECT * FROM Product WHERE Product_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getInt("Product_ID"));
                product.setName(rs.getString("Name"));
                product.setDescriptionText(rs.getString("Comment"));
                product.setPrice(rs.getDouble("Price"));
                return product;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen des Produkts mit ID: " + productId);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lädt alle Produkte aus der Datenbank
     * @return Liste aller Produkte (leer wenn keine vorhanden)
     */
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM Product";
        List<Product> products = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getInt("Product_ID"));
                product.setName(rs.getString("Name"));
                product.setDescriptionText(rs.getString("Comment"));
                product.setPrice(rs.getDouble("Price"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen aller Produkte");
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Sucht Produkte nach Namen (auch Teilübereinstimmung)
     * VERWENDET: LIKE-Operator mit Wildcards (%)
     * Beispiel: "Laptop" findet "Gaming Laptop", "Laptop Pro", etc.
     *
     * @param name Der Suchbegriff (Groß-/Kleinschreibung wird je nach DB ignoriert)
     * @return Liste der gefundenen Produkte (leer wenn keine Treffer)
     */
    public List<Product> searchProductsByName(String name) {
        // LIKE mit % = findet Namen die den Suchbegriff enthalten
        String sql = "SELECT * FROM Product WHERE Name LIKE ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // % vor und nach dem Suchbegriff = findet Übereinstimmung überall im Text
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getInt("Product_ID"));
                product.setName(rs.getString("Name"));
                product.setDescriptionText(rs.getString("Comment"));
                product.setPrice(rs.getDouble("Price"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Suchen von Produkten mit Name: " + name);
            e.printStackTrace();
        }
        return products;
    }
    /**
     * Aktualisiert ein bestehendes Produkt
     * @param product Das Product-Objekt mit neuen Daten (muss ID haben)
     */
    public void updateProduct(Product product) {
        String sql = "UPDATE Product SET Name = ?, Comment = ?, Price = ? WHERE Product_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescriptionText());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getProduct_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten des Produkts");
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert nur den Preis eines Produkts (schneller als komplettes Update)
     * @param productId Die ID des Produkts
     * @param newPrice Der neue Preis
     */
    public void updateProductPrice(int productId, double newPrice) {
        String sql = "UPDATE Product SET Price = ? WHERE Product_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newPrice);
            stmt.setInt(2, productId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten des Preises für Produkt ID: " + productId);
            e.printStackTrace();
        }
    }

    /**
     * Löscht ein Produkt aus der Datenbank (Hard Delete)
     * ACHTUNG: Sollte vorher Reviews und CartProducts löschen (Foreign Keys)
     * @param productId Die ID des zu löschenden Produkts
     */
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM Product WHERE Product_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen des Produkts mit ID: " + productId);
            e.printStackTrace();
        }
    }
}
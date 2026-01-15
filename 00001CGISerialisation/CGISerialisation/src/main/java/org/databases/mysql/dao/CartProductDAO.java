package org.databases.mysql.dao;

import org.databases.entity.CartProduct;
import org.databases.mysql.setup.Connector;
import org.interfaces.ICartProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für CartProduct-Entität (Junction Table)
 * Verwaltet Produkte in Warenkörben (n:m Beziehung)
 */
public class CartProductDAO implements ICartProduct {

    /**
     * Fügt ein Produkt zu einem Warenkorb hinzu
     * @param cartProduct Das hinzuzufügende CartProduct (muss ShoppingCart und Product haben)
     */
    public void addProductToCart(CartProduct cartProduct) {
        String sql = "INSERT INTO CartProduct (Quantity, Price, ShoppingCart_ShoppingCart_ID, Product_Product_ID) VALUES (?, ?, ?, ?)";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartProduct.getQuantity());
            stmt.setDouble(2, cartProduct.getPrice());
            stmt.setInt(3, cartProduct.getShoppingCart().getShoppingCart_id());
            stmt.setInt(4, cartProduct.getProduct().getProduct_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Hinzufügen des Produkts zum Warenkorb");
            e.printStackTrace();
        }
    }

    /**
     * Lädt ein CartProduct anhand seiner ID
     * @param cartProductId Die ID des gesuchten CartProducts
     * @return CartProduct-Objekt oder null wenn nicht gefunden
     */
    public CartProduct getCartProductById(int cartProductId) {
        String sql = "SELECT * FROM CartProduct WHERE CartProduct_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartProductId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CartProduct cp = new CartProduct();
                cp.setCartProduct_id(rs.getInt("CartProduct_ID"));
                cp.setQuantity(rs.getInt("Quantity"));
                cp.setPrice(rs.getDouble("Price"));
                return cp;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen des CartProducts mit ID: " + cartProductId);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lädt alle Produkte in einem bestimmten Warenkorb
     * Nützlich um Warenkorb-Inhalt anzuzeigen
     * @param shoppingCartId Die ID des Warenkorbs
     * @return Liste aller CartProducts in diesem Warenkorb
     */
    public List<CartProduct> getCartProductsByShoppingCartId(int shoppingCartId) {
        String sql = "SELECT * FROM CartProduct WHERE ShoppingCart_ShoppingCart_ID = ?";
        List<CartProduct> cartProducts = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartProduct cp = new CartProduct();
                cp.setCartProduct_id(rs.getInt("CartProduct_ID"));
                cp.setQuantity(rs.getInt("Quantity"));
                cp.setPrice(rs.getDouble("Price"));
                cartProducts.add(cp);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der CartProducts für Warenkorb ID: " + shoppingCartId);
            e.printStackTrace();
        }
        return cartProducts;
    }

    /**
     * Sucht ein bestimmtes Produkt in einem Warenkorb
     * Nützlich um zu prüfen ob Produkt bereits im Warenkorb ist
     * @param shoppingCartId Die ID des Warenkorbs
     * @param productId Die ID des Produkts
     * @return CartProduct-Objekt oder null wenn Kombination nicht existiert
     */
    public CartProduct getCartProductByShoppingCartAndProduct(int shoppingCartId, int productId) {
        String sql = "SELECT * FROM CartProduct WHERE ShoppingCart_ShoppingCart_ID = ? AND Product_Product_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CartProduct cp = new CartProduct();
                cp.setCartProduct_id(rs.getInt("CartProduct_ID"));
                cp.setQuantity(rs.getInt("Quantity"));
                cp.setPrice(rs.getDouble("Price"));
                return cp;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen des CartProducts");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aktualisiert ein bestehendes CartProduct
     * @param cartProduct Das CartProduct-Objekt mit neuen Daten (muss ID haben)
     */
    public void updateCartProduct(CartProduct cartProduct) {
        String sql = "UPDATE CartProduct SET Quantity = ?, Price = ?, ShoppingCart_ShoppingCart_ID = ?, Product_Product_ID = ? WHERE CartProduct_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartProduct.getQuantity());
            stmt.setDouble(2, cartProduct.getPrice());
            stmt.setInt(3, cartProduct.getShoppingCart().getShoppingCart_id());
            stmt.setInt(4, cartProduct.getProduct().getProduct_id());
            stmt.setInt(5, cartProduct.getCartProduct_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten des CartProducts");
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert nur die Menge eines CartProducts
     * Nützlich für +/- Buttons im Warenkorb
     * @param cartProductId Die ID des CartProducts
     * @param quantity Die neue Menge
     */
    public void updateQuantity(int cartProductId, int quantity) {
        String sql = "UPDATE CartProduct SET Quantity = ? WHERE CartProduct_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, cartProductId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten der Menge für CartProduct ID: " + cartProductId);
            e.printStackTrace();
        }
    }

    /**
     * Entfernt ein Produkt aus dem Warenkorb (Hard Delete)
     * @param cartProductId Die ID des zu entfernenden CartProducts
     */
    public void removeProductFromCart(int cartProductId) {
        String sql = "DELETE FROM CartProduct WHERE CartProduct_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartProductId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Entfernen des Produkts aus dem Warenkorb");
            e.printStackTrace();
        }
    }

    /**
     * Leert einen kompletten Warenkorb (löscht ALLE CartProducts)
     * Nützlich nach erfolgreichem Checkout oder zum Zurücksetzen
     * @param shoppingCartId Die ID des zu leerenden Warenkorbs
     */
    public void clearCart(int shoppingCartId) {
        String sql = "DELETE FROM CartProduct WHERE ShoppingCart_ShoppingCart_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Leeren des Warenkorbs");
            e.printStackTrace();
        }
    }

    /**
     * Zählt die Gesamtanzahl aller Artikel im Warenkorb
     * VERWENDET: COALESCE und SUM
     *
     * Beispiel: 2x Laptop + 3x Maus = 5 Artikel gesamt
     * - SUM(Quantity) = addiert alle Mengen
     * - COALESCE(..., 0) = gibt 0 zurück wenn Warenkorb leer
     *
     * @param shoppingCartId Die ID des Warenkorbs
     * @return Anzahl aller Artikel (Summe der Mengen)
     */
    public int getTotalItemsInCart(int shoppingCartId) {
        String sql = "SELECT COALESCE(SUM(Quantity), 0) as total FROM CartProduct WHERE ShoppingCart_ShoppingCart_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Berechnen der Gesamtanzahl der Artikel");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Berechnet die Zwischensumme des Warenkorbs (ohne Steuern/Versand)
     * VERWENDET: COALESCE und SUM
     *
     * Berechnung: Summe(Preis × Menge) für alle Produkte
     * - SUM(Price * Quantity) = summiert Einzelpreise
     * - COALESCE(..., 0.0) = gibt 0.0 zurück wenn leer
     *
     * @param shoppingCartId Die ID des Warenkorbs
     * @return Zwischensumme (Preis aller Produkte ohne Extras)
     */
    public double getCartSubtotal(int shoppingCartId) {
        String sql = "SELECT COALESCE(SUM(Price * Quantity), 0) as subtotal FROM CartProduct WHERE ShoppingCart_ShoppingCart_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("subtotal");
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Berechnen der Zwischensumme");
            e.printStackTrace();
        }
        return 0.0;
    }
}
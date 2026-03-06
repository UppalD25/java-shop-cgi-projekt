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


}
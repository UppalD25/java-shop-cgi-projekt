package org.databases.access.dao;

import org.interfaces.IShoppingCart;

import org.databases.entity.ShoppingCart;
import org.databases.access.setup.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für ShoppingCart-Entität
 * Verwaltet Warenkörbe in der MySQL-Datenbank
 */
public class ShoppingCartDAO implements IShoppingCart {

    /**
     * Erstellt einen neuen Warenkorb in der Datenbank
     * @param shoppingCart Der zu speichernde Warenkorb (muss einen Account haben)
     */
    public void createShoppingCart(ShoppingCart shoppingCart) {
        String sql = "INSERT INTO ShoppingCart (taxRate, deliveryFee, Account_Account_ID) VALUES (?, ?, ?)";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCart.getTaxRate());
            stmt.setDouble(2, shoppingCart.getDeliveryFee());
            stmt.setInt(3, shoppingCart.getAccount().getAccount_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen des Warenkorbs");
            e.printStackTrace();
        }
    }


    /**
     * Lädt alle Warenkörbe aus der Datenbank
     * @return Liste aller Warenkörbe (leer wenn keine vorhanden)
     */
    public List<ShoppingCart> getAllShoppingCarts() {
        String sql = "SELECT * FROM ShoppingCart";
        List<ShoppingCart> carts = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ShoppingCart cart = new ShoppingCart();
                cart.setShoppingCart_id(rs.getInt("ShoppingCart_ID"));
                cart.setTaxRate(rs.getInt("taxRate"));
                cart.setDeliveryFee(rs.getDouble("deliveryFee"));
                carts.add(cart);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen aller Warenkörbe");
            e.printStackTrace();
        }
        return carts;
    }
    /**
     * Aktualisiert einen bestehenden Warenkorb
     * @param shoppingCart Das ShoppingCart-Objekt mit neuen Daten (muss ID haben)
     */
    public void updateShoppingCart(ShoppingCart shoppingCart) {
        String sql = "UPDATE ShoppingCart SET taxRate = ?, deliveryFee = ?, Account_Account_ID = ? WHERE ShoppingCart_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCart.getTaxRate());
            stmt.setDouble(2, shoppingCart.getDeliveryFee());
            stmt.setInt(3, shoppingCart.getAccount().getAccount_id());
            stmt.setInt(4, shoppingCart.getShoppingCart_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten des Warenkorbs");
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert nur den Steuersatz eines Warenkorbs
     * Nützlich wenn sich Steuerregeln ändern
     * @param shoppingCartId Die ID des Warenkorbs
     * @param taxRate Der neue Steuersatz (z.B. 19 für 19%)
     */
    public void updateTaxRate(int shoppingCartId, int taxRate) {
        String sql = "UPDATE ShoppingCart SET taxRate = ? WHERE ShoppingCart_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, taxRate);
            stmt.setInt(2, shoppingCartId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten der Steuer für Warenkorb ID: " + shoppingCartId);
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert nur die Versandkosten eines Warenkorbs
     * Nützlich wenn sich Versandoptionen ändern
     * @param shoppingCartId Die ID des Warenkorbs
     * @param deliveryFee Die neuen Versandkosten
     */
    public void updateDeliveryFee(int shoppingCartId, double deliveryFee) {
        String sql = "UPDATE ShoppingCart SET deliveryFee = ? WHERE ShoppingCart_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, deliveryFee);
            stmt.setInt(2, shoppingCartId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten der Versandkosten für Warenkorb ID: " + shoppingCartId);
            e.printStackTrace();
        }
    }

    /**
     * Löscht einen Warenkorb aus der Datenbank (Hard Delete)
     * ACHTUNG: Sollte vorher CartProducts löschen (Foreign Keys)
     * @param shoppingCartId Die ID des zu löschenden Warenkorbs
     */
    public void deleteShoppingCart(int shoppingCartId) {
        String sql = "DELETE FROM ShoppingCart WHERE ShoppingCart_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen des Warenkorbs mit ID: " + shoppingCartId);
            e.printStackTrace();
        }
    }

}

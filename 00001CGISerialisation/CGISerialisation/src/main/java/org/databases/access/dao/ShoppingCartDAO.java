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
     * Lädt einen Warenkorb anhand seiner ID
     * @param shoppingCartId Die ID des gesuchten Warenkorbs
     * @return ShoppingCart-Objekt oder null wenn nicht gefunden
     */
    public ShoppingCart getShoppingCartById(int shoppingCartId) {
        String sql = "SELECT * FROM ShoppingCart WHERE ShoppingCart_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ShoppingCart cart = new ShoppingCart();
                cart.setShoppingCart_id(rs.getInt("ShoppingCart_ID"));
                cart.setTaxRate(rs.getInt("taxRate"));
                cart.setDeliveryFee(rs.getDouble("deliveryFee"));
                return cart;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen des Warenkorbs mit ID: " + shoppingCartId);
            e.printStackTrace();
        }
        return null;
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
     * Lädt alle Warenkörbe eines bestimmten Accounts
     * Beinhaltet aktive UND abgeschlossene Warenkörbe
     * @param accountId Die ID des Accounts
     * @return Liste der Warenkörbe dieses Accounts
     */
    public List<ShoppingCart> getShoppingCartsByAccountId(int accountId) {
        String sql = "SELECT * FROM ShoppingCart WHERE Account_Account_ID = ?";
        List<ShoppingCart> carts = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ShoppingCart cart = new ShoppingCart();
                cart.setShoppingCart_id(rs.getInt("ShoppingCart_ID"));
                cart.setTaxRate(rs.getInt("taxRate"));
                cart.setDeliveryFee(rs.getDouble("deliveryFee"));
                carts.add(cart);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Warenkörbe für Account ID: " + accountId);
            e.printStackTrace();
        }
        return carts;
    }

    /**
     * Holt den aktiven (noch nicht abgeschlossenen) Warenkorb eines Accounts
     * VERWENDET: LEFT JOIN mit IS NULL Check
     *
     * Logik: Ein Warenkorb ist aktiv, wenn er KEINE Rechnung hat
     * - LEFT JOIN verbindet ShoppingCart mit Receipt
     * - WHERE r.Receipt_ID IS NULL = keine Rechnung vorhanden
     * - ORDER BY sc.ShoppingCart_ID DESC = neuesten zuerst
     * - LIMIT 1 = nur den neuesten aktiven Warenkorb
     *
     * @param accountId Die ID des Accounts
     * @return Der aktive Warenkorb oder null wenn keiner vorhanden
     */
    public ShoppingCart getActiveShoppingCartByAccountId(int accountId) {
        String sql = "SELECT TOP 1 sc.* FROM ShoppingCart sc " +
                "LEFT JOIN Receipt r ON sc.ShoppingCart_ID = r.ShoppingCart_ShoppingCart_ID " +
                "WHERE sc.Account_Account_ID = ? AND r.Receipt_ID IS NULL " +
                "ORDER BY sc.ShoppingCart_ID DESC";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ShoppingCart cart = new ShoppingCart();
                cart.setShoppingCart_id(rs.getInt("ShoppingCart_ID"));
                cart.setTaxRate(rs.getInt("taxRate"));
                cart.setDeliveryFee(rs.getDouble("deliveryFee"));
                return cart;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden des aktiven Warenkorbs für Account ID: " + accountId);
            e.printStackTrace();
        }
        return null;
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

    /**
     * Berechnet den Gesamtpreis eines Warenkorbs
     * VERWENDET: COALESCE, SUM, LEFT JOIN
     *
     * Berechnung: Summe(Preis × Menge) + Steuern + Versandkosten
     * - COALESCE(SUM(...), 0) = gibt 0 zurück wenn Warenkorb leer
     * - LEFT JOIN = auch leere Warenkörbe werden gefunden
     * - GROUP BY = wichtig für SUM Aggregation
     *
     * @param shoppingCartId Die ID des Warenkorbs
     * @return Gesamtpreis inkl. Steuern und Versand
     */
    public double calculateTotal(int shoppingCartId) {
        String sql = "SELECT " +
                "Nz(SUM(cp.Price * cp.Quantity), 0) as subtotal, " +
                "sc.taxRate, " +
                "sc.deliveryFee " +
                "FROM ShoppingCart sc " +
                "LEFT JOIN CartProduct cp ON sc.ShoppingCart_ID = cp.ShoppingCart_ShoppingCart_ID " +
                "WHERE sc.ShoppingCart_ID = ? " +
                "GROUP BY sc.ShoppingCart_ID, sc.taxRate, sc.deliveryFee";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double subtotal = rs.getDouble("subtotal");
                int taxRate = rs.getInt("taxRate");
                double deliveryFee = rs.getDouble("deliveryFee");

                // Steuer berechnen: Zwischensumme × (Steuersatz / 100)
                double tax = subtotal * (taxRate / 100.0);

                // Gesamtsumme = Zwischensumme + Steuern + Versand
                return subtotal + tax + deliveryFee;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Berechnen des Totals für Warenkorb ID: " + shoppingCartId);
            e.printStackTrace();
        }
        return 0.0;
    }
}

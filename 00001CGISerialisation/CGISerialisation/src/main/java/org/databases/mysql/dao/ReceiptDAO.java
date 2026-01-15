package org.databases.mysql.dao;

import org.databases.entity.Receipt;
import org.databases.mysql.setup.Connector;
import org.interfaces.IReceipt;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für Receipt-Entität (Rechnung/Beleg)
 * Verwaltet Kaufbelege in der MySQL-Datenbank
 */
public class ReceiptDAO implements IReceipt {

    // CREATE
    /**
     * Erstellt eine neue Rechnung in der Datenbank
     * VERWENDET: Timestamp.valueOf() für LocalDateTime → SQL Timestamp
     * @param receipt Die zu speichernde Rechnung (muss ShoppingCart und Account haben)
     */
    public void createReceipt(Receipt receipt) {
        String sql = "INSERT INTO Receipt (receiptDate, ShoppingCart_ShoppingCart_ID, Account_Account_ID) VALUES (?, ?, ?)";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(receipt.getReceiptDate()));
            stmt.setInt(2, receipt.getShoppingCart().getShoppingCart_id());
            stmt.setInt(3, receipt.getAccount().getAccount_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Rechnung");
            e.printStackTrace();
        }
    }

    // READ
    /**
     * Lädt eine Rechnung anhand ihrer ID
     * @param receiptId Die ID der gesuchten Rechnung
     * @return Receipt-Objekt oder null wenn nicht gefunden
     */
    public Receipt getReceiptById(int receiptId) {
        String sql = "SELECT * FROM Receipt WHERE Receipt_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, receiptId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setReceipt_id(rs.getInt("Receipt_ID"));
                receipt.setReceiptDate(rs.getTimestamp("receiptDate").toLocalDateTime());
                return receipt;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Rechnung mit ID: " + receiptId);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lädt alle Rechnungen aus der Datenbank
     * @return Liste aller Rechnungen (leer wenn keine vorhanden)
     */
    public List<Receipt> getAllReceipts() {
        String sql = "SELECT * FROM Receipt";
        List<Receipt> receipts = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setReceipt_id(rs.getInt("Receipt_ID"));
                receipt.setReceiptDate(rs.getTimestamp("receiptDate").toLocalDateTime());
                receipts.add(receipt);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen aller Rechnungen");
            e.printStackTrace();
        }
        return receipts;
    }

    /**
     * Lädt alle Rechnungen eines bestimmten Accounts
     * @param accountId Die ID des Accounts
     * @return Liste der Rechnungen dieses Accounts
     */
    public List<Receipt> getReceiptsByAccountId(int accountId) {
        String sql = "SELECT * FROM Receipt WHERE Account_Account_ID = ?";
        List<Receipt> receipts = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setReceipt_id(rs.getInt("Receipt_ID"));
                receipt.setReceiptDate(rs.getTimestamp("receiptDate").toLocalDateTime());
                receipts.add(receipt);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Rechnungen für Account ID: " + accountId);
            e.printStackTrace();
        }
        return receipts;
    }

    /**
     * Lädt alle Rechnungen für einen bestimmten Warenkorb
     * @param shoppingCartId Die ID des Warenkorbs
     * @return Liste der Rechnungen für diesen Warenkorb
     */
    public List<Receipt> getReceiptsByShoppingCartId(int shoppingCartId) {
        String sql = "SELECT * FROM Receipt WHERE ShoppingCart_ShoppingCart_ID = ?";
        List<Receipt> receipts = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shoppingCartId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setReceipt_id(rs.getInt("Receipt_ID"));
                receipt.setReceiptDate(rs.getTimestamp("receiptDate").toLocalDateTime());
                receipts.add(receipt);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Rechnungen für Warenkorb ID: " + shoppingCartId);
            e.printStackTrace();
        }
        return receipts;
    }

    /**
     * Lädt alle Rechnungen in einem bestimmten Zeitraum
     * VERWENDET: BETWEEN mit Timestamps
     * @param startDate Start-Datum (inklusiv)
     * @param endDate End-Datum (inklusiv)
     * @return Liste der Rechnungen in diesem Zeitraum
     */
    public List<Receipt> getReceiptsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM Receipt WHERE receiptDate BETWEEN ? AND ?";
        List<Receipt> receipts = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(startDate));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setReceipt_id(rs.getInt("Receipt_ID"));
                receipt.setReceiptDate(rs.getTimestamp("receiptDate").toLocalDateTime());
                receipts.add(receipt);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Rechnungen zwischen " + startDate + " und " + endDate);
            e.printStackTrace();
        }
        return receipts;
    }

    // UPDATE
    /**
     * Aktualisiert eine bestehende Rechnung
     * Normalerweise werden Rechnungen NICHT geändert (rechtliche Gründe)
     * Aber für Korrekturen kann es nötig sein
     * @param receipt Das Receipt-Objekt mit neuen Daten (muss ID haben)
     */
    public void updateReceipt(Receipt receipt) {
        String sql = "UPDATE Receipt SET receiptDate = ?, ShoppingCart_ShoppingCart_ID = ?, Account_Account_ID = ? WHERE Receipt_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(receipt.getReceiptDate()));
            stmt.setInt(2, receipt.getShoppingCart().getShoppingCart_id());
            stmt.setInt(3, receipt.getAccount().getAccount_id());
            stmt.setInt(4, receipt.getReceipt_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Aktualisieren der Rechnung mit ID: " + receipt.getReceipt_id());
            e.printStackTrace();
        }
    }

    // DELETE
    /**
     * Löscht eine Rechnung aus der Datenbank (Hard Delete)
     * ACHTUNG: Sollte normalerweise NICHT gemacht werden (Aufbewahrungspflicht!)
     * @param receiptId Die ID der zu löschenden Rechnung
     */
    public void deleteReceipt(int receiptId) {
        String sql = "DELETE FROM Receipt WHERE Receipt_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, receiptId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen der Rechnung mit ID: " + receiptId);
            e.printStackTrace();
        }
    }

    // BUSINESS LOGIC
    /**
     * Berechnet den Gesamtbetrag einer Rechnung
     * VERWENDET: Komplexe JOIN-Abfrage mit COALESCE
     *
     * Berechnung:
     * 1. Hole Warenkorb-Daten (taxRate, deliveryFee)
     * 2. Summiere alle Produkte im Warenkorb (Preis × Menge)
     * 3. Berechne: Zwischensumme + Steuern + Versand
     *
     * @param receiptId Die ID der Rechnung
     * @return Gesamtbetrag der Rechnung
     */
    public double getReceiptTotal(int receiptId) {
        // Zuerst: Hole ShoppingCart_ID von der Rechnung
        String sqlGetCart = "SELECT ShoppingCart_ShoppingCart_ID FROM Receipt WHERE Receipt_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmtCart = conn.prepareStatement(sqlGetCart)) {

            stmtCart.setInt(1, receiptId);
            ResultSet rsCart = stmtCart.executeQuery();

            if (rsCart.next()) {
                int shoppingCartId = rsCart.getInt("ShoppingCart_ShoppingCart_ID");

                // Jetzt: Berechne Total mit komplexer Query
                String sqlTotal =
                        "SELECT " +
                                "COALESCE(SUM(cp.Price * cp.Quantity), 0) as subtotal, " +
                                "sc.taxRate, " +
                                "sc.deliveryFee " +
                                "FROM ShoppingCart sc " +
                                "LEFT JOIN CartProduct cp ON sc.ShoppingCart_ID = cp.ShoppingCart_ShoppingCart_ID " +
                                "WHERE sc.ShoppingCart_ID = ? " +
                                "GROUP BY sc.ShoppingCart_ID, sc.taxRate, sc.deliveryFee";

                try (PreparedStatement stmtTotal = conn.prepareStatement(sqlTotal)) {
                    stmtTotal.setInt(1, shoppingCartId);
                    ResultSet rsTotal = stmtTotal.executeQuery();

                    if (rsTotal.next()) {
                        double subtotal = rsTotal.getDouble("subtotal");
                        int taxRate = rsTotal.getInt("taxRate");
                        double deliveryFee = rsTotal.getDouble("deliveryFee");

                        // Berechnung: Zwischensumme + Steuern + Versand
                        double tax = subtotal * (taxRate / 100.0);
                        return subtotal + tax + deliveryFee;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Berechnen des Totals für Rechnung ID: " + receiptId);
            e.printStackTrace();
        }

        return 0.0;
    }
}
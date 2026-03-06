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

}
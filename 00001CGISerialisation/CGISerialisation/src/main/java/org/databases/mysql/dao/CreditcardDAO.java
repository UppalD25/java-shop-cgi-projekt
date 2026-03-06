package org.databases.mysql.dao;

import org.databases.entity.Creditcard;
import org.databases.mysql.setup.Connector;
import org.interfaces.ICreditcard;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für Creditcard-Entität
 * Verwaltet Kreditkarten in der MySQL-Datenbank
 */
public class CreditcardDAO implements ICreditcard {

    /**
     * Erstellt eine neue Kreditkarte in der Datenbank
     * @param creditcard Die zu speichernde Kreditkarte (muss einen Account haben)
     */
    public void createCreditcard(Creditcard creditcard) {
        String sql = "INSERT INTO Creditcard (iban, validUntil, Cardnumber, Account_Account_ID) VALUES (?, ?, ?, ?)";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, creditcard.getIban());
            // LocalDate zu SQL Date konvertieren
            stmt.setDate(2, Date.valueOf(creditcard.getValidUntil()));
            stmt.setString(3, creditcard.getCardnumber());
            stmt.setInt(4, creditcard.getAccount().getAccount_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Kreditkarte");
            e.printStackTrace();
        }
    }

    /**
     * Lädt eine Kreditkarte anhand ihrer ID
     * @param creditcardId Die ID der gesuchten Kreditkarte
     * @return Creditcard-Objekt oder null wenn nicht gefunden
     */
    public Creditcard getCreditcardById(int creditcardId) {
        String sql = "SELECT * FROM Creditcard WHERE Creditcard_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, creditcardId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Creditcard card = new Creditcard();
                card.setCreditcard_id(rs.getInt("Creditcard_ID"));
                card.setIban(rs.getString("iban"));
                // SQL Date zu LocalDate konvertieren
                card.setValidUntil(rs.getDate("validUntil").toLocalDate());
                card.setCardnumber(rs.getString("Cardnumber"));
                return card;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Kreditkarte mit ID: " + creditcardId);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lädt alle Kreditkarten aus der Datenbank
     * @return Liste aller Kreditkarten (leer wenn keine vorhanden)
     */
    public List<Creditcard> getAllCreditcards() {
        String sql = "SELECT * FROM Creditcard";
        List<Creditcard> creditcards = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Creditcard card = new Creditcard();
                card.setCreditcard_id(rs.getInt("Creditcard_ID"));
                card.setIban(rs.getString("iban"));
                card.setValidUntil(rs.getDate("validUntil").toLocalDate());
                card.setCardnumber(rs.getString("Cardnumber"));
                creditcards.add(card);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen aller Kreditkarten");
            e.printStackTrace();
        }
        return creditcards;
    }


    /**
     * Aktualisiert eine bestehende Kreditkarte
     * @param creditcard Das Creditcard-Objekt mit neuen Daten (muss ID haben)
     */
    public void updateCreditcard(Creditcard creditcard) {
        String sql = "UPDATE Creditcard SET iban = ?, validUntil = ?, Cardnumber = ?, Account_Account_ID = ? WHERE Creditcard_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, creditcard.getIban());
            stmt.setDate(2, Date.valueOf(creditcard.getValidUntil()));
            stmt.setString(3, creditcard.getCardnumber());
            stmt.setInt(4, creditcard.getAccount().getAccount_id());
            stmt.setInt(5, creditcard.getCreditcard_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten der Kreditkarte");
            e.printStackTrace();
        }
    }

    /**
     * Löscht eine Kreditkarte aus der Datenbank (Hard Delete)
     * @param creditcardId Die ID der zu löschenden Kreditkarte
     */
    public void deleteCreditcard(int creditcardId) {
        String sql = "DELETE FROM Creditcard WHERE Creditcard_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, creditcardId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen der Kreditkarte mit ID: " + creditcardId);
            e.printStackTrace();
        }
    }

    /**
     * Prüft ob eine Kreditkarte abgelaufen ist
     * @param creditcardId Die ID der zu prüfenden Kreditkarte
     * @return true wenn abgelaufen, false wenn noch gültig
     */
    public boolean isCardExpired(int creditcardId) {
        // Karte aus DB laden
        Creditcard card = getCreditcardById(creditcardId);
        if (card != null) {
            // Gültigkeitsdatum mit heutigem Datum vergleichen
            return card.getValidUntil().isBefore(LocalDate.now());
        }
        // Wenn Karte nicht existiert, als abgelaufen behandeln
        return true;
    }
}
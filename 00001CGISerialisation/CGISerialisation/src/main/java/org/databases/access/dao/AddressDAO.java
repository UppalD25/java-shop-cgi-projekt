package org.databases.access.dao;

import org.interfaces.IAddress;
import org.databases.entity.Address;
import org.databases.access.setup.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AddressDAO implements IAddress {
    /**
     * Erstellt eine neue Adresse in der Datenbank
     * @param address Die zu speichernde Adresse (muss einen Account haben)
     */
    public void createAddress(Address address) {
        String sql = "INSERT INTO Address (PLZ, Country, Street, DoorNumber, Account_Account_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Adressdaten in PreparedStatement setzen
            stmt.setString(1, address.getPlz());
            stmt.setString(2, address.getCountry());
            stmt.setString(3, address.getStreet());
            stmt.setString(4, address.getDoorNumber());
            stmt.setInt(5, address.getAccount().getAccount_id());

            // SQL ausführen
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Adresse");
            e.printStackTrace();
        }
    }

    /**
     * Lädt eine Adresse anhand ihrer ID
     * @param addressId Die ID der gesuchten Adresse
     * @return Address-Objekt oder null wenn nicht gefunden
     */
    public Address getAddressById(int addressId) {
        String sql = "SELECT * FROM Address WHERE Address_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // ID als Parameter setzen
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();

            // Wenn Adresse gefunden wurde
            if (rs.next()) {
                Address address = new Address();
                // Daten aus ResultSet in Address-Objekt übertragen
                address.setAddress_id(rs.getInt("Address_ID"));
                address.setPlz(rs.getString("PLZ"));
                address.setCountry(rs.getString("Country"));
                address.setStreet(rs.getString("Street"));
                address.setDoorNumber(rs.getString("DoorNumber"));
                return address;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Adresse mit ID: " + addressId);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lädt alle Adressen aus der Datenbank
     * @return Liste aller Adressen (leer wenn keine vorhanden)
     */
    public List<Address> getAllAddresses() {
        String sql = "SELECT * FROM Address";
        List<Address> addresses = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Alle Ergebnisse durchgehen
            while (rs.next()) {
                Address address = new Address();
                address.setAddress_id(rs.getInt("Address_ID"));
                address.setPlz(rs.getString("PLZ"));
                address.setCountry(rs.getString("Country"));
                address.setStreet(rs.getString("Street"));
                address.setDoorNumber(rs.getString("DoorNumber"));
                addresses.add(address);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen aller Adressen");
            e.printStackTrace();
        }
        return addresses;
    }

    /**
     * Lädt alle Adressen eines bestimmten Accounts
     * @param accountId Die ID des Accounts
     * @return Liste der Adressen dieses Accounts (leer wenn keine vorhanden)
     */
    public List<Address> getAddressesByAccountId(int accountId) {
        String sql = "SELECT * FROM Address WHERE Account_Account_ID = ?";
        List<Address> addresses = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            // Alle Adressen dieses Accounts sammeln
            while (rs.next()) {
                Address address = new Address();
                address.setAddress_id(rs.getInt("Address_ID"));
                address.setPlz(rs.getString("PLZ"));
                address.setCountry(rs.getString("Country"));
                address.setStreet(rs.getString("Street"));
                address.setDoorNumber(rs.getString("DoorNumber"));
                addresses.add(address);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Adressen für Account ID: " + accountId);
            e.printStackTrace();
        }
        return addresses;
    }

    /**
     * Aktualisiert eine bestehende Adresse
     * @param address Das Address-Objekt mit neuen Daten (muss ID haben)
     */
    public void updateAddress(Address address) {
        String sql = "UPDATE Address SET PLZ = ?, Country = ?, Street = ?, DoorNumber = ?, Account_Account_ID = ? WHERE Address_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Neue Werte setzen
            stmt.setString(1, address.getPlz());
            stmt.setString(2, address.getCountry());
            stmt.setString(3, address.getStreet());
            stmt.setString(4, address.getDoorNumber());
            stmt.setInt(5, address.getAccount().getAccount_id());
            // WHERE-Bedingung: welche Adresse soll aktualisiert werden
            stmt.setInt(6, address.getAddress_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten der Adresse");
            e.printStackTrace();
        }
    }

    /**
     * Löscht eine Adresse aus der Datenbank (Hard Delete)
     * @param addressId Die ID der zu löschenden Adresse
     */
    public void deleteAddress(int addressId) {
        String sql = "DELETE FROM Address WHERE Address_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, addressId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen der Adresse mit ID: " + addressId);
            e.printStackTrace();
        }
    }
}

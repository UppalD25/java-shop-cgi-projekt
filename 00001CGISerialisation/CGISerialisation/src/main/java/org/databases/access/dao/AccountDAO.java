package org.databases.access.dao;

import org.databases.entity.Account;
import org.databases.entity.Address;
import org.databases.entity.Creditcard;
import org.databases.entity.ShoppingCart;
import org.interfaces.IAccount;
import org.databases.access.setup.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements IAccount {

    // CREATE
    public void createAccount(Account account) {
        String sql = "INSERT INTO Account (Surname, Lastname, Email, Password, PhoneNumber, isActive) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getSurname());
            stmt.setString(2, account.getLastname());
            stmt.setString(3, account.getEmail());
            stmt.setString(4, account.getPassword());
            stmt.setString(5, account.getPhoneNumber());
            stmt.setBoolean(6, account.isActive());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen des Accounts");
            e.printStackTrace();
        }
    }

    // READ
    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM Account WHERE Account_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("Account_ID"));
                account.setSurname(rs.getString("Surname"));
                account.setLastname(rs.getString("Lastname"));
                account.setEmail(rs.getString("Email"));
                account.setPassword(rs.getString("Password"));
                account.setPhoneNumber(rs.getString("PhoneNumber"));
                account.setActive(rs.getBoolean("isActive"));
                return account;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen des Accounts mit der ID: " + accountId);
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByEmail(String email) {
        String sql = "SELECT * FROM Account WHERE Email = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("Account_ID"));
                account.setSurname(rs.getString("Surname"));
                account.setLastname(rs.getString("Lastname"));
                account.setEmail(rs.getString("Email"));
                account.setPassword(rs.getString("Password"));
                account.setPhoneNumber(rs.getString("PhoneNumber"));
                account.setActive(rs.getBoolean("isActive"));
                return account;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen des Accounts mit Email: " + email);
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        String sql = "SELECT * FROM Account";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("Account_ID"));
                account.setSurname(rs.getString("Surname"));
                account.setLastname(rs.getString("Lastname"));
                account.setEmail(rs.getString("Email"));
                account.setPassword(rs.getString("Password"));
                account.setPhoneNumber(rs.getString("PhoneNumber"));
                account.setActive(rs.getBoolean("isActive"));
                accounts.add(account);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen aller Accounts");
            e.printStackTrace();
        }
        return accounts;
    }

    // UPDATE
    public void updateAccount(Account account) {
        String sql = "UPDATE Account SET Surname = ?, Lastname = ?, Email = ?, Password = ?, PhoneNumber = ?, isActive = ? WHERE Account_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getSurname());
            stmt.setString(2, account.getLastname());
            stmt.setString(3, account.getEmail());
            stmt.setString(4, account.getPassword());
            stmt.setString(5, account.getPhoneNumber());
            stmt.setBoolean(6, account.isActive());
            stmt.setInt(7, account.getAccount_id());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten des Accounts");
            e.printStackTrace();
        }
    }

    public void updatePassword(int accountId, String newPassword) {
        String sql = "UPDATE Account SET Password = ? WHERE Account_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten des Passworts des Accounts mit der ID: " + accountId);
            e.printStackTrace();
        }
    }

    public void updateEmail(int accountId, String newEmail) {
        String sql = "UPDATE Account SET Email = ? WHERE Account_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newEmail);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten der Email des Accounts mit der ID: " + accountId);
            e.printStackTrace();
        }
    }

    // DELETE - Soft Delete (deaktiviert statt löscht)
    public void deleteAccount(int accountId) {
        // Soft Delete - Account wird nur deaktiviert, nicht gelöscht
        String sql = "UPDATE Account SET isActive = ? WHERE Account_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, false);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
            System.out.println("Account " + accountId + " wurde deaktiviert (Soft Delete)");

        } catch (SQLException e) {
            System.err.println("Fehler beim Deaktivieren des Accounts mit der ID: " + accountId);
            e.printStackTrace();
        }
    }


    /**
     * Reaktiviert einen deaktivierten Account
     */
    public void activateAccount(int accountId) {
        String sql = "UPDATE Account SET isActive = ? WHERE Account_ID = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, true);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
            System.out.println("Account " + accountId + " wurde reaktiviert");

        } catch (SQLException e) {
            System.err.println("Fehler beim Reaktivieren des Accounts mit der ID: " + accountId);
            e.printStackTrace();
        }
    }

    // AUTHENTICATION
    public Account login(String email, String password) {
        // Nur aktive Accounts können sich einloggen
        String sql = "SELECT * FROM Account WHERE Email = ? AND Password = ? AND isActive = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setBoolean(3, true);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("Account_ID"));
                account.setSurname(rs.getString("Surname"));
                account.setLastname(rs.getString("Lastname"));
                account.setEmail(rs.getString("Email"));
                account.setPassword(rs.getString("Password"));
                account.setPhoneNumber(rs.getString("PhoneNumber"));
                account.setActive(rs.getBoolean("isActive"));
                return account;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Login des Accounts mit Email: " + email);
            e.printStackTrace();
        }
        return null;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Account WHERE Email = ?";

        try (Connection conn = Connector.getConnectionToAccess();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Prüfen der Email-Existenz");
            e.printStackTrace();
        }
        return false;
    }


}
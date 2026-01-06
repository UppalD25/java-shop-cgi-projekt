package org.test;

import org.databases.mysql.dao.AccountDAO;
import org.databases.entity.Account;

import java.util.List;

public class AccountDAOMysqlTest {
    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO(); // ← Access DAO

        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║      ACCOUNT DAO TEST PROGRAMM - ACCESS VERSION        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        // 1. TEST: 10 Accounts erstellen
        System.out.println("► TEST 1: Erstelle 10 Test-Accounts...\n");
        createTestAccounts(accountDAO);

        deleteAccount(accountDAO, 1);
        deleteAccount(accountDAO, 2);

        // 2. TEST: Alle Accounts auslesen
        System.out.println("\n► TEST 2: Alle Accounts aus DB laden...\n");
        displayAllAccounts(accountDAO);

        // 3. TEST: Account per ID suchen
        System.out.println("\n► TEST 3: Account mit ID 1 suchen...\n");
        testGetAccountById(accountDAO, 1);

        // 4. TEST: Account per Email suchen
        System.out.println("\n► TEST 4: Account per Email suchen...\n");
        testGetAccountByEmail(accountDAO, "max.mustermann@example.com");

        // 5. TEST: Email-Existenz prüfen
        System.out.println("\n► TEST 5: Email-Existenz prüfen...\n");
        testEmailExists(accountDAO);

        // 6. TEST: Passwort updaten
        System.out.println("\n► TEST 6: Passwort updaten...\n");
        testUpdatePassword(accountDAO, 1, "NeuesPasswort123!");

        // 7. TEST: Login testen
        System.out.println("\n► TEST 7: Login testen...\n");
        testLogin(accountDAO);

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              ALLE TESTS ABGESCHLOSSEN                  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }

    /**
     * Erstellt 10 Test-Accounts (nur wenn sie noch nicht existieren)
     */
    private static void createTestAccounts(AccountDAO accountDAO) {
        String[] vornamen = {"Max", "Anna", "Peter", "Laura", "Thomas",
                "Julia", "Michael", "Sarah", "Daniel", "Lisa"};
        String[] nachnamen = {"Mustermann", "Schmidt", "Müller", "Weber", "Meyer",
                "Wagner", "Becker", "Schulz", "Hoffmann", "Koch"};

        for (int i = 0; i < 10; i++) {
            String email = vornamen[i].toLowerCase() + "." +
                    nachnamen[i].toLowerCase() + "@example.com";

            // Prüfen ob Account schon existiert
            if (accountDAO.emailExists(email)) {
                System.out.println("  ⊘ Account existiert bereits: " + email);
                continue;
            }

            Account account = new Account();
            account.setSurname(vornamen[i]);
            account.setLastname(nachnamen[i]);
            account.setEmail(email);
            account.setPassword("Passwort" + (i + 1) + "!");
            account.setPhoneNumber("0123456789" + i);
            account.setActive(true);

            accountDAO.createAccount(account);
            System.out.println("  ✓ Account erstellt: " + account.getEmail());
        }
    }

    /**
     * Zeigt alle Accounts aus der DB an
     */
    private static void displayAllAccounts(AccountDAO accountDAO) {
        List<Account> accounts = accountDAO.getAllAccounts();

        System.out.println("┌─────┬──────────────┬──────────────┬────────────────────────────────┬───────────────┐");
        System.out.println("│ ID  │ Vorname      │ Nachname     │ Email                          │ Telefon       │");
        System.out.println("├─────┼──────────────┼──────────────┼────────────────────────────────┼───────────────┤");

        for (Account account : accounts) {
            System.out.printf("│ %-3d │ %-12s │ %-12s │ %-30s │ %-13s │%n",
                    account.getAccount_id(),
                    account.getSurname(),
                    account.getLastname(),
                    account.getEmail(),
                    account.getPhoneNumber());
        }

        System.out.println("└─────┴──────────────┴──────────────┴────────────────────────────────┴───────────────┘");
        System.out.println("  → Gesamt: " + accounts.size() + " Accounts gefunden");
    }

    /**
     * Testet getAccountById
     */
    private static void testGetAccountById(AccountDAO accountDAO, int id) {
        Account account = accountDAO.getAccountById(id);

        if (account != null) {
            System.out.println("  ✓ Account gefunden:");
            System.out.println("    - ID:       " + account.getAccount_id());
            System.out.println("    - Name:     " + account.getSurname() + " " + account.getLastname());
            System.out.println("    - Email:    " + account.getEmail());
            System.out.println("    - Telefon:  " + account.getPhoneNumber());
        } else {
            System.out.println("  ✗ Account mit ID " + id + " nicht gefunden!");
        }
    }

    /**
     * Testet getAccountByEmail
     */
    private static void testGetAccountByEmail(AccountDAO accountDAO, String email) {
        Account account = accountDAO.getAccountByEmail(email);

        if (account != null) {
            System.out.println("  ✓ Account gefunden:");
            System.out.println("    - ID:       " + account.getAccount_id());
            System.out.println("    - Name:     " + account.getSurname() + " " + account.getLastname());
            System.out.println("    - Email:    " + account.getEmail());
        } else {
            System.out.println("  ✗ Account mit Email '" + email + "' nicht gefunden!");
        }
    }

    /**
     * Testet emailExists
     */
    private static void testEmailExists(AccountDAO accountDAO) {
        String existingEmail = "max.mustermann@example.com";
        String nonExistingEmail = "doesnotexist@example.com";

        boolean exists1 = accountDAO.emailExists(existingEmail);
        boolean exists2 = accountDAO.emailExists(nonExistingEmail);

        System.out.println("  " + (exists1 ? "✓" : "✗") + " Email '" + existingEmail + "' existiert: " + exists1);
        System.out.println("  " + (!exists2 ? "✓" : "✗") + " Email '" + nonExistingEmail + "' existiert: " + exists2);
    }
    private static void deleteAccount(AccountDAO accountDAO, int accountId) {
        accountDAO.deleteAccount(accountId);
    }

    /**
     * Testet updatePassword
     */
    private static void testUpdatePassword(AccountDAO accountDAO, int accountId, String newPassword) {
        // Altes Passwort speichern
        Account before = accountDAO.getAccountById(accountId);
        String oldPassword = before.getPassword();

        // Passwort updaten
        accountDAO.updatePassword(accountId, newPassword);

        // Neues Passwort prüfen
        Account after = accountDAO.getAccountById(accountId);
        String updatedPassword = after.getPassword();

        System.out.println("  • Altes Passwort:  " + oldPassword);
        System.out.println("  • Neues Passwort:  " + updatedPassword);

        if (updatedPassword.equals(newPassword)) {
            System.out.println("  ✓ Passwort erfolgreich geändert!");
        } else {
            System.out.println("  ✗ Passwort-Update fehlgeschlagen!");
        }
    }

    /**
     * Testet Login-Funktionalität
     */
    private static void testLogin(AccountDAO accountDAO) {
        // Test 1: Korrekter Login
        String email = "max.mustermann@example.com";

        // Zuerst Passwort auf bekannten Wert setzen
        Account existingAccount = accountDAO.getAccountByEmail(email);
        if (existingAccount != null) {
            accountDAO.updatePassword(existingAccount.getAccount_id(), "TestPasswort123!");
        }

        String correctPassword = "TestPasswort123!";

        Account loginSuccess = accountDAO.login(email, correctPassword);

        if (loginSuccess != null) {
            System.out.println("  ✓ Login erfolgreich für: " + loginSuccess.getEmail());
        } else {
            System.out.println("  ✗ Login fehlgeschlagen (korrekte Credentials)");
        }

        // Test 2: Falsches Passwort
        String wrongPassword = "FalschesPasswort";
        Account loginFail = accountDAO.login(email, wrongPassword);

        if (loginFail == null) {
            System.out.println("  ✓ Login korrekt abgelehnt (falsches Passwort)");
        } else {
            System.out.println("  ✗ Login sollte fehlschlagen (falsches Passwort)!");
        }

        // Test 3: Nicht existierende Email
        String wrongEmail = "notexist@example.com";
        Account loginFail2 = accountDAO.login(wrongEmail, correctPassword);

        if (loginFail2 == null) {
            System.out.println("  ✓ Login korrekt abgelehnt (Email existiert nicht)");
        } else {
            System.out.println("  ✗ Login sollte fehlschlagen (Email existiert nicht)!");
        }
    }
}

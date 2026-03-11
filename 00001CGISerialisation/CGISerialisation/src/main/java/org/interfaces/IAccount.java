package org.interfaces;

import org.databases.entity.Account;
import org.databases.entity.Address;
import org.databases.entity.Creditcard;
import org.databases.entity.ShoppingCart;

import java.util.List;

public interface IAccount {
        /**
         * Creates a new account with the details provided in the given Account object.
         *
         * @param account The Account object containing the information for the new account.
         * @return true if the account was successfully created, false otherwise.
         */
        boolean createAccount(Account account);


        Account getAccountById(int accountId);
        Account getAccountByEmail(String email);
        List<Account> getAllAccounts();

        // UPDATE
        void updateAccount(Account account);
        void updatePassword(int accountId, String newPassword);
        void updateEmail(int accountId, String newEmail);

        // soft delete
        /**
         * Führt ein "soft delete" für einen Account durch, indem der isActive-Status auf false gesetzt wird.
         * @param accountId Die ID des zu deaktivierenden Accounts
         */
        void deleteAccount(int accountId);

        // doppel checken
        Account login(String email, String password);
        boolean emailExists(String email);

      /*
        später vielleicht
        List<Creditcard> getCreditcardsByAccountId(int accountId);
        List<ShoppingCart> getShoppingCartsByAccountId(int accountId);
    */
}


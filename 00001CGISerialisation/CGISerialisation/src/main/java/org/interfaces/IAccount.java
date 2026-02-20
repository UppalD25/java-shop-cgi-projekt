package org.interfaces;

import org.databases.entity.Account;
import org.databases.entity.Address;
import org.databases.entity.Creditcard;
import org.databases.entity.ShoppingCart;

import java.util.List;

public interface IAccount {
        // CREATE
        void createAccount(Account account);

        // READ
        Account getAccountById(int accountId);
        Account getAccountByEmail(String email);
        List<Account> getAllAccounts();

        // UPDATE
        void updateAccount(Account account);
        void updatePassword(int accountId, String newPassword);
        void updateEmail(int accountId, String newEmail);

        // soft DELETE
        /**
         * Führt ein "soft delete" für einen Account durch, indem der isActive-Status auf false gesetzt wird.
         * @param accountId Die ID des zu deaktivierenden Accounts
         */
        void deleteAccount(int accountId);

        // AUTHENTICATION
        Account login(String email, String password);
        boolean emailExists(String email);

      /*
        // RELATIONSHIPS (optional)
       */
        List<Address> getAddressesByAccountId(int accountId);
        List<Creditcard> getCreditcardsByAccountId(int accountId);
        List<ShoppingCart> getShoppingCartsByAccountId(int accountId);
}

package org.databases.utils;

import org.databases.entity.Account;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseQueryHelper {
    public static void createAccountPreparingStatment(Account account, PreparedStatement stmt) throws SQLException {
        updateAccountPreparingStatment(account, stmt);
        stmt.executeUpdate();
    }

    public static void updateAccountPreparingStatment(Account account, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, account.getSurname());
        stmt.setString(2, account.getLastname());
        stmt.setString(3, account.getEmail());
        stmt.setString(4, account.getPassword());
        stmt.setString(5, account.getPhoneNumber());
        stmt.setBoolean(6, account.isActive());
    }
}

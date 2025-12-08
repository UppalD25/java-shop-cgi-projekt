package org.mysql.mySqlEntityToDB;

import java.sql.*;
import java.util.Scanner;

public class DataBaseSetup {
    private Scanner Scanner = new Scanner(System.in);

    public void createAllTables() {
        System.out.print("Bitte geben Sie den MY-SQL-Key (2FA-Key) ein:");

        String key = Scanner.nextLine();
        createCreditcardTable(key);
        createAccountTable(key);
        createAddressTable(key);
        createProductTable(key);
        createReviewTable(key);
        createShoppingCartTable(key);
        createReceiptTable(key);
        createCartProductTable(key);


        AddKey.addAllConstraints(key);

        System.out.println("\nTabellen mit Referenzen erfolgreich erstellt.\n");

    }

    private void createAccountTable(String key){
        try(Connection conn = Connector.getConnectionToMySQL(key);
            Statement stmt = conn.createStatement()){

            stmt.execute("CREATE TABLE IF NOT EXISTS Account " +
                    "(Account_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Surname VARCHAR(255), " +
                    "Lastname VARCHAR(255), " +
                    "Email VARCHAR(255) UNIQUE, " +
                    "Password VARCHAR(255), " +
                    "PhoneNumber VARCHAR(255))");
            System.out.println("Tabelle Account erfolgreich erstellt");

        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Account");
            e.printStackTrace();
        }
    }

    private void createAddressTable(String key){
        try(Connection conn = Connector.getConnectionToMySQL(key);
            Statement stmt = conn.createStatement()){
            stmt.execute("CREATE TABLE IF NOT EXISTS Address " +
                    "(Address_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "PLZ VARCHAR(255), " +
                    "Country VARCHAR(255), " +
                    "Street VARCHAR(255), " +
                    "DoorNumber VARCHAR(255), " +
                    "Account_Account_ID INT)");
            System.out.println("Tabelle Address erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Address");
            e.printStackTrace();
        }
    }

    private void createReviewTable(String key){
        try(Connection conn = Connector.getConnectionToMySQL(key);
            Statement stmt = conn.createStatement()){

            stmt.execute("CREATE TABLE IF NOT EXISTS Review " +
                    "(Review_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Comment TEXT, " +
                    "ReviewDate DATE, " +
                    "Product_Product_ID INT, " +
                    "Account_Account_ID INT)");
            System.out.println("Tabelle Review erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Review");
            e.printStackTrace();
        }
    }

    private void createCreditcardTable(String key){
        try(Connection conn = Connector.getConnectionToMySQL(key);
            Statement stmt = conn.createStatement()){

            stmt.execute("CREATE TABLE IF NOT EXISTS Creditcard " +
                    "(Creditcard_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "iban VARCHAR(255), " +
                    "validUntil DATE, " +
                    "Cardnumber VARCHAR(255), " +
                    "Account_Account_ID INT)");
            System.out.println("Tabelle Creditcard erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Creditcard");
            e.printStackTrace();
        }
    }

    private void createProductTable(String key){
        try(Connection conn = Connector.getConnectionToMySQL(key);
            Statement stmt = conn.createStatement()){

            stmt.execute("CREATE TABLE IF NOT EXISTS Product " +
                    "(Product_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Name VARCHAR(255), " +
                    "Comment TEXT, " +
                    "Price DECIMAL(10,2))");
            System.out.println("Tabelle Product erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Product");
            e.printStackTrace();
        }
    }

    private void createReceiptTable(String key){
        try(Connection conn = Connector.getConnectionToMySQL(key);
            Statement stmt = conn.createStatement()){

            stmt.execute("CREATE TABLE IF NOT EXISTS Receipt " +
                    "(Receipt_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "receiptDate DATE, " +
                    "ShoppingCart_ShoppingCart_ID INT, " +
                    "Account_Account_ID INT)");
            System.out.println("Tabelle Receipt erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Receipt");
            e.printStackTrace();
        }
    }

    private void createShoppingCartTable(String key){
        try(Connection conn = Connector.getConnectionToMySQL(key);
            Statement stmt = conn.createStatement()){

            stmt.execute("CREATE TABLE IF NOT EXISTS ShoppingCart " +
                    "(ShoppingCart_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "taxRate DECIMAL(5,2), " +
                    "deliveryFee DECIMAL(10,2), " +
                    "Account_Account_ID INT)");
            System.out.println("Tabelle ShoppingCart erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle ShoppingCart");
            e.printStackTrace();
        }
    }

    private void createCartProductTable(String key){
        try(Connection conn = Connector.getConnectionToMySQL(key);
            Statement stmt = conn.createStatement()){
            stmt.execute("CREATE TABLE IF NOT EXISTS CartProduct " +
                    "(CartProduct_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Quantity INT, " +
                    "Price DECIMAL(10,2), " +
                    "ShoppingCart_ShoppingCart_ID INT, " +
                    "Product_Product_ID INT)");
            System.out.println("Tabelle CartProduct erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle CartProduct");
            e.printStackTrace();
        }
    }
}
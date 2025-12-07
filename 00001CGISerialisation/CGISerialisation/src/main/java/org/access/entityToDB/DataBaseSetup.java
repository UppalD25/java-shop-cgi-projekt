package org.access.entityToDB;

import java.sql.*;

public class DataBaseSetup {

    public void createAllTables() {

        createCreditcardTable();
        createAccountTable();
        createAddressTable();
        createProductTable();
        createReviewTable();
        createShoppingCartTable();
        createReceiptTable();
        createCartProductTable();

        AddKey.addAllConstraints();
    }

    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, tableName, null);){
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private void createAccountTable(){
        try(Connection conn = Connector.getConnectionToAccess();
            Statement stmt = conn.createStatement()){

            if (!tableExists(conn,"Account")){
                stmt.execute("CREATE TABLE Account " +
                        "(Account_ID AUTOINCREMENT PRIMARY KEY, " +
                        "Surname VARCHAR(255), " +
                        "Lastname VARCHAR(255), " +
                        "Email VARCHAR(255), " +
                        "Password VARCHAR(255), " +
                        "PhoneNumber VARCHAR(255))");
                System.out.println("Tabelle Account erfolgreich erstellt");
            }else{
                System.out.println("Tabelle Account existiert bereits");
            }
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Account");
            e.printStackTrace();
        }
    }

    private void createAddressTable(){
        try(Connection conn = Connector.getConnectionToAccess();
            Statement stmt = conn.createStatement()){
            if (tableExists(conn,"Address")){
                System.out.println("Tabelle Address existiert bereits");
                return;
            }
            stmt.execute("CREATE TABLE Address " +
                    "(Address_ID AUTOINCREMENT PRIMARY KEY, " +
                    "PLZ VARCHAR(255), " +
                    "Country VARCHAR(255), " +
                    "Street VARCHAR(255), " +
                    "DoorNumber VARCHAR(255), " +
                    "Account_Account_ID INTEGER)");
            System.out.println("Tabelle Address erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Address");
            e.printStackTrace();
        }
    }

    private void createReviewTable(){
        try(Connection conn = Connector.getConnectionToAccess();
            Statement stmt = conn.createStatement()){
            if (tableExists(conn,"Review")){
                System.out.println("Tabelle Review existiert bereits");
                return;
            }
            stmt.execute("CREATE TABLE Review " +
                    "(Review_ID AUTOINCREMENT PRIMARY KEY, " +
                    "Comment TEXT, " +
                    "ReviewDate DATE, " +
                    "Product_Product_ID INTEGER, " +
                    "Account_Account_ID INTEGER)");
            System.out.println("Tabelle Review erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Review");
            e.printStackTrace();
        }
    }

    private void createCreditcardTable(){
        try(Connection conn = Connector.getConnectionToAccess();
            Statement stmt = conn.createStatement()){
            if (tableExists(conn,"Creditcard")){
                System.out.println("Tabelle Creditcard existiert bereits");
                return;
            }
            stmt.execute("CREATE TABLE Creditcard " +
                    "(Creditcard_ID AUTOINCREMENT PRIMARY KEY, " +
                    "iban VARCHAR(255), " +
                    "validUntil DATE, " +
                    "Cardnumber VARCHAR(255), " +
                    "Account_Account_ID INTEGER)");
            System.out.println("Tabelle Creditcard erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Creditcard");
            e.printStackTrace();
        }
    }

    private void createProductTable(){
        try(Connection conn = Connector.getConnectionToAccess();
            Statement stmt = conn.createStatement()){
            if (tableExists(conn,"Product")){
                System.out.println("Tabelle Product existiert bereits");
                return;
            }
            stmt.execute("CREATE TABLE Product " +
                    "(Product_ID AUTOINCREMENT PRIMARY KEY, " +
                    "Name VARCHAR(255), " +
                    "Comment TEXT, " +
                    "Price NUMERIC(10,2))");
            System.out.println("Tabelle Product erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Product");
            e.printStackTrace();
        }
    }

    private void createReceiptTable(){
        try(Connection conn = Connector.getConnectionToAccess();
            Statement stmt = conn.createStatement()){
            if (tableExists(conn,"Receipt")){
                System.out.println("Tabelle Receipt existiert bereits");
                return;
            }
            stmt.execute("CREATE TABLE Receipt " +
                    "(Receipt_ID AUTOINCREMENT PRIMARY KEY, " +
                    "receiptDate DATE, " +
                    "ShoppingCart_ShoppingCart_ID INTEGER, " +
                    "Account_Account_ID INTEGER)");
            System.out.println("Tabelle Receipt erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle Receipt");
            e.printStackTrace();
        }
    }

    private void createShoppingCartTable(){
        try(Connection conn = Connector.getConnectionToAccess();
            Statement stmt = conn.createStatement()){
            if (tableExists(conn,"ShoppingCart")){
                System.out.println("Tabelle ShoppingCart existiert bereits");
                return;
            }
            stmt.execute("CREATE TABLE ShoppingCart " +
                    "(ShoppingCart_ID AUTOINCREMENT PRIMARY KEY, " +
                    "taxRate NUMERIC(5,2), " +
                    "deliveryFee NUMERIC(10,2), " +
                    "Account_Account_ID INTEGER)");
            System.out.println("Tabelle ShoppingCart erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle ShoppingCart");
            e.printStackTrace();
        }
    }

    private void createCartProductTable(){
        try(Connection conn = Connector.getConnectionToAccess();
            Statement stmt = conn.createStatement()){
            if (tableExists(conn,"CartProduct")){
                System.out.println("Tabelle CartProduct existiert bereits");
                return;
            }
            stmt.execute("CREATE TABLE CartProduct " +
                    "(Quantity INTEGER, " +
                    "Price NUMERIC(10,2), " +
                    "ShoppingCart_ShoppingCart_ID INTEGER, " +
                    "Product_Product_ID INTEGER, " +
                    "PRIMARY KEY (ShoppingCart_ShoppingCart_ID, Product_Product_ID))");
            System.out.println("Tabelle CartProduct erfolgreich erstellt");
        }catch (SQLException e){
            System.err.println("Fehler beim Erstellen der Tabelle CartProduct");
            e.printStackTrace();
        }
    }
}
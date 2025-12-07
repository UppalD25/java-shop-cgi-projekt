package org.access.entityToDB;

import java.sql.*;

public class AddKey {

    public static void  addAllConstraints() {
        addConstraintAddressAccount();
        addConstraintCreditcardAccount();
        addConstraintReviewProduct();
        addConstraintReviewAccount();
        addConstraintShoppingCartAccount();
        addConstraintReceiptShoppingCart();
        addConstraintReceiptAccount();
        addConstraintCartProductShoppingCart();
        addConstraintCartProductProduct();
    }

    private static boolean constraintExists(Connection conn, String tableName, String constraintName) throws SQLException {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getImportedKeys(null, null, tableName)) {
                while (rs.next()) {
                    String fkName = rs.getString("FK_NAME");
                    if (constraintName.equalsIgnoreCase(fkName)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    private static void addConstraintAddressAccount() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "Address", "FK_Address_Account")) {
                System.out.println(" Constraint FK_Address_Account existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE Address " +
                    "ADD CONSTRAINT FK_Address_Account " +
                    "FOREIGN KEY (Account_Account_ID) REFERENCES Account(Account_ID)");
            System.out.println(" Constraint FK_Address_Account erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_Address_Account (kann bei Access vorkommen)");
            e.printStackTrace();
        }
    }

    private static void addConstraintCreditcardAccount() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "Creditcard", "FK_Creditcard_Account")) {
                System.out.println(" Constraint FK_Creditcard_Account existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE Creditcard " +
                    "ADD CONSTRAINT FK_Creditcard_Account " +
                    "FOREIGN KEY (Account_Account_ID) REFERENCES Account(Account_ID)");
            System.out.println(" Constraint FK_Creditcard_Account erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_Creditcard_Account");
            e.printStackTrace();
        }
    }

    private static void addConstraintReviewProduct() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "Review", "FK_Review_Product")) {
                System.out.println(" Constraint FK_Review_Product existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE Review " +
                    "ADD CONSTRAINT FK_Review_Product " +
                    "FOREIGN KEY (Product_Product_ID) REFERENCES Product(Product_ID)");
            System.out.println(" Constraint FK_Review_Product erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_Review_Product");
            e.printStackTrace();
        }
    }

    private static void addConstraintReviewAccount() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "Review", "FK_Review_Account")) {
                System.out.println(" Constraint FK_Review_Account existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE Review " +
                    "ADD CONSTRAINT FK_Review_Account " +
                    "FOREIGN KEY (Account_Account_ID) REFERENCES Account(Account_ID)");
            System.out.println(" Constraint FK_Review_Account erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_Review_Account");
            e.printStackTrace();
        }
    }

    private static void addConstraintShoppingCartAccount() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "ShoppingCart", "FK_ShoppingCart_Account")) {
                System.out.println(" Constraint FK_ShoppingCart_Account existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE ShoppingCart " +
                    "ADD CONSTRAINT FK_ShoppingCart_Account " +
                    "FOREIGN KEY (Account_Account_ID) REFERENCES Account(Account_ID)");
            System.out.println(" Constraint FK_ShoppingCart_Account erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_ShoppingCart_Account");
            e.printStackTrace();
        }
    }

    private static void addConstraintReceiptShoppingCart() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "Receipt", "FK_Receipt_ShoppingCart")) {
                System.out.println(" Constraint FK_Receipt_ShoppingCart existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE Receipt " +
                    "ADD CONSTRAINT FK_Receipt_ShoppingCart " +
                    "FOREIGN KEY (ShoppingCart_ShoppingCart_ID) REFERENCES ShoppingCart(ShoppingCart_ID)");
            System.out.println(" Constraint FK_Receipt_ShoppingCart erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_Receipt_ShoppingCart");
            e.printStackTrace();
        }
    }

    private static void addConstraintReceiptAccount() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "Receipt", "FK_Receipt_Account")) {
                System.out.println(" Constraint FK_Receipt_Account existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE Receipt " +
                    "ADD CONSTRAINT FK_Receipt_Account " +
                    "FOREIGN KEY (Account_Account_ID) REFERENCES Account(Account_ID)");
            System.out.println(" Constraint FK_Receipt_Account erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_Receipt_Account");
            e.printStackTrace();
        }
    }

    private static void addConstraintCartProductShoppingCart() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "CartProduct", "FK_CartProduct_ShoppingCart")) {
                System.out.println(" Constraint FK_CartProduct_ShoppingCart existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE CartProduct " +
                    "ADD CONSTRAINT FK_CartProduct_ShoppingCart " +
                    "FOREIGN KEY (ShoppingCart_ShoppingCart_ID) REFERENCES ShoppingCart(ShoppingCart_ID)");
            System.out.println(" Constraint FK_CartProduct_ShoppingCart erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_CartProduct_ShoppingCart");
            e.printStackTrace();
        }
    }

    private static void addConstraintCartProductProduct() {
        try (Connection conn = Connector.getConnectionToAccess();
             Statement stmt = conn.createStatement()) {

            if (constraintExists(conn, "CartProduct", "FK_CartProduct_Product")) {
                System.out.println(" Constraint FK_CartProduct_Product existiert bereits");
                return;
            }

            stmt.execute("ALTER TABLE CartProduct " +
                    "ADD CONSTRAINT FK_CartProduct_Product " +
                    "FOREIGN KEY (Product_Product_ID) REFERENCES Product(Product_ID)");
            System.out.println(" Constraint FK_CartProduct_Product erstellt");

        } catch (SQLException e) {
            System.err.println(" Fehler bei FK_CartProduct_Product");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AddKey addKey = new AddKey();
        addKey.addAllConstraints();
        System.out.println("\n Constraint-Setup abgeschlossen!");
    }
}
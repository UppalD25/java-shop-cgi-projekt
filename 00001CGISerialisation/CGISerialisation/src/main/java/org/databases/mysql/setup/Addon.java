package org.databases.mysql.setup;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Addon {

    public void runAddon() {
        try (Scanner scanner = new Scanner(System.in)){
            System.out.print("Bitte geben Sie den MY-SQL-Key (2FA-Key) ein: ");
            addIsActiveColumn(scanner.nextLine());
        }catch (SQLException e){
            System.err.println("Fehler beim ausführen der Methode! ");
            e.printStackTrace();
        }
    }

    private void addIsActiveColumn(String keyword) throws SQLException {
        try(Connection con =Connector.getConnectionToMySQL(keyword);
            Statement stmt = con.createStatement()){
            stmt.execute("ALTER TABLE Account ADD COLUMN isActive BOOLEAN DEFAULT TRUE");
            System.out.println("Spalte isActive wurde erfolgreich zu Account hinzugefügt!");
        }catch (SQLException e){

            System.err.println("Fehler beim ausführen des SQL-Statements!");
            e.printStackTrace();
        }
    }

}

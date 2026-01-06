package org.databases.mysql.setup;
import org.databases.utils.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String databaseURL = ConfigLoader.getProperty("mysql.url");
    private static final String databaseUser = ConfigLoader.getProperty("mysql.app.db.user");
    private static final String databasePassword = ConfigLoader.getProperty("mysql.app.db.password");

    public static Connection getConnectionToMySQL()throws SQLException{
        try{
            return DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
        }catch (SQLException e){
            System.err.println( "Fehler beim Verbinden mit der Datenbank ");
            throw e;
        }
    }
    protected static Connection getConnectionToMySQL(String key)throws SQLException{
        try{
            if(!key.equals(ConfigLoader.getProperty("zwei.faktor"))){
                throw new SQLException("Ungültiger Key!");
            }

            return getAdminConnectionToMySQL();
        }catch (SQLException e){
            System.err.println("Ungültiger Key!");
            throw e;
        }

    }
    private static Connection getAdminConnectionToMySQL()throws SQLException{
        try{
            return DriverManager.getConnection(
                    databaseURL,
                    ConfigLoader.getProperty("mysql.admin.db.user"),
                    ConfigLoader.getProperty("mysql.admin.db.password"));
        }catch (SQLException e){
            System.err.println(">>> Verbindung mit Admin fehlgeschlagen <<<");
            throw e;
        }
    }

}

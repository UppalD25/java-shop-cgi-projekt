package org.access.accessEntityToDB;
import org.externalData.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String databaseURL = ConfigLoader.getProperty("access.url");



    protected static Connection getConnectionToAccess()throws SQLException{
        try {
            return DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            System.err.println( "Fehler beim Verbinden mit der Datenbank ");
            throw e;
        }
    }

    //Vielleicht für später in bevor man versucht irgendwas zu machen diese methode austesten
    public boolean testConnection() {
        try(Connection conn = getConnectionToAccess()){
            return conn != null && !conn.isClosed();
        }catch ( SQLException e ){
            System.err.println(">>> Verbindung fehlgeschlagen <<<");
            return false;
        }
    }

/*



    private boolean connectionExists() {
        try {
            return (myConnection != null && !myConnection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean connectedToDatabase() {
        if (!connectionExists()) {
            String databaseURL = "jdbc:ucanaccess://data/" + ACCESS_DB_NAME;
            try {
                myConnection = DriverManager.getConnection(databaseURL);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    protected Connection getAccess(){
        return myConnection;
    }

     */
}

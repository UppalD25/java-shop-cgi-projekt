package org.externalData;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties config = new Properties();

    // Wird beim ersten Zugriff automatisch geladen
    static {
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                System.err.println("config.properties nicht gefunden!");
            } else {
                config.load(input);
            }

        } catch (Exception e) {
            System.err.println("Fehler beim Laden der config.properties");
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return config.getProperty(key);
    }
}
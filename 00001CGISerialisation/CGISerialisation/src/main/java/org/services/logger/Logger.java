
package org.services.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Logger class provides a thread-safe, singleton-based logging mechanism that allows
 * messages to be logged with different severity levels, such as INFO, WARNING, and SEVERE.
 *
 * The logger supports both console logging and optional file logging. When file logging is
 * enabled via the setLogFile method, log messages are written to the configured file in addition
 * to being displayed on the console.
 *
 * This class follows the lazy initialization pattern for creating the singleton instance, ensuring
 * that the logger is initialized only when required. Logging operations are timestamped and can
 * be directed based on the specified log level.
 */
public final class Logger {

    private static volatile Logger instance;
    private static final Object LOCK = new Object();

    private PrintWriter fileWriter;
    private boolean logToFile = false;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
        // Standardmäßig nur Console-Logging
    }

    /**
     * Returns the singleton instance of the Logger class. This method ensures thread-safe
     * lazy initialization of the instance.
     *
     * @return the singleton instance of the Logger class.
     */
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }


    /**
     * Sets the file path for log output and enables file logging. If the specified file does not
     * exist, it will be created. Logs messages to both the console and the specified file once
     * the file logging is enabled.
     *
     * @param filePath The path of the file where log messages will be written.
     */
    public void setLogFile(String filePath) {
        try {
            fileWriter = new PrintWriter(new FileWriter(filePath, true), true);
            logToFile = true;
            info("Logger initialisiert, Datei: " + filePath);
        } catch (IOException e) {
            severe("Logger: Log-Datei konnte nicht geöffnet werden: " + e.getMessage());
        }
    }

    /**
     * Logs a message with the specified log level. The message is formatted with a timestamp
     * and written to the console. Optionally, if file logging is enabled, the log entry is
     * also written to an output file.
     *
     * @param level   The log level of the message (e.g., INFO, WARNING, SEVERE).
     * @param message The message to be logged.
     */
    private void log(Log_Level level, String message) {
        String timestamp = LocalDateTime.now().format(dtf);
        String fullMessage = String.format("[%s] [%s] %s", timestamp, level, message);

        // Console
        System.out.println(fullMessage);

        // File
        if (logToFile && fileWriter != null) {
            fileWriter.println(fullMessage);
        }
    }

    /**
     * Logs a message with the log level set to INFO. The message is formatted with a timestamp
     * and written to the console. If file logging is enabled, the log entry is also written to
     * the configured output file.
     *
     * @param message The message to be logged with the INFO log level.
     */
    public void info(String message) {
        log(Log_Level.INFO, message);
    }

    /**
     * Logs a message with the log level set to WARNING. The message is formatted with a timestamp
     * and written to the console. If file logging is enabled, the log entry is also written to
     * the configured output file.
     *
     * @param message The message to be logged with the WARNING log level.
     */
    public void warning(String message) {
        log(Log_Level.WARNING, message);
    }

    /**
     * Logs a message with the log level set to SEVERE. The message is formatted with a timestamp
     * and written to the console. If file logging is enabled, the log entry is also written to
     * the configured output file.
     *
     * @param message The message to be logged with the SEVERE log level.
     */
    public void severe(String message) {
        log(Log_Level.SEVERE, message);
    }


    /**
     * Closes the logger by flushing and releasing the file writer resource, if initialized.
     * If file logging is enabled, any pending log entries are written to the file
     * before closing the file writer.
     *
     * This method ensures that the logger completes its operations properly and
     * prevents resource leaks. It is recommended to call this method when file
     * logging is enabled and the logger is no longer needed.
     *
     * If the file writer has not been initialized, this method performs no action.
     */
    public void close() {
        if (fileWriter != null) {
            info("Logger beendet.");
            fileWriter.flush();
            fileWriter.close();
        }
    }
}

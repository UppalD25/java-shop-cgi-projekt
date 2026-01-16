package org.services.baseService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cgi.CgiParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstrakte Basis-Klasse für alle Services
 * Stellt gemeinsame Methoden für HTTP-Responses bereit
 */
public abstract class BaseService {

    protected CgiParameter params;
    protected ObjectMapper mapper;

    public BaseService(CgiParameter params) {
        this.params = params;
        this.mapper = new ObjectMapper();
    }

    /**
     * Hauptmethode - wird in jeder Subclass implementiert
     */
    public abstract void execute();

    /**
     * Sendet JSON-Response
     * @param data Object das zu JSON serialisiert wird
     */
    protected void sendJsonResponse(Object data) {
        try {
            System.out.println("Content-Type: application/json; charset=UTF-8");
            System.out.println();
            String json = mapper.writeValueAsString(data);
            System.out.println(json);
            System.out.flush();
        } catch (Exception e) {
            System.err.println("ERROR in sendJsonResponse:");
            e.printStackTrace();
            sendErrorResponse("Fehler beim Erstellen der JSON-Response");
        }
    }

    /**
     * Sendet HTML-Response
     * @param html HTML-String
     */
    protected void sendHtmlResponse(String html) {
        System.out.println("Content-Type: text/html; charset=UTF-8");
        System.out.println();
        System.out.println(html);
        System.out.flush();
    }

    /**
     * Sendet Error-Response als JSON
     * @param errorMessage Fehlermeldung
     */
    protected void sendErrorResponse(String errorMessage) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", errorMessage);
        error.put("timestamp", System.currentTimeMillis());

        try {
            System.out.println("Content-Type: application/json; charset=UTF-8");
            System.out.println();
            String json = mapper.writeValueAsString(error);
            System.out.println(json);
            System.out.flush();
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in sendErrorResponse:");
            e.printStackTrace();
            // Fallback auf plain text
            System.out.println("Content-Type: text/plain; charset=UTF-8");
            System.out.println();
            System.out.println("Internal Server Error");
            System.out.flush();
        }
    }

    /**
     * Sendet Success-Response mit Daten
     * @param message Success-Nachricht
     * @param data Zusätzliche Daten
     */
    protected void sendSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }
        sendJsonResponse(response);
    }

    /**
     * Hilfsmethode: Liest "action" aus JSON-Body
     * @return action-String oder null
     */
    protected String getActionFromBody() {
        try {
            String body = params.getContentFromBodyAsString();
            if (body == null || body.isEmpty()) {
                return null;
            }

            Map<String, Object> json = mapper.readValue(body, Map.class);
            return (String) json.get("action");
        } catch (Exception e) {
            System.err.println("ERROR reading action from body:");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Hilfsmethode: Parst JSON-Body zu Map
     * @return Map mit JSON-Daten
     */
    protected Map<String, Object> parseJsonBody() {
        try {
            String body = params.getContentFromBodyAsString();
            if (body == null || body.isEmpty()) {
                return new HashMap<>();
            }
            return mapper.readValue(body, Map.class);
        } catch (Exception e) {
            System.err.println("ERROR parsing JSON body:");
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
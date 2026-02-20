package org.cgi;

import org.services.*;
import org.services.baseService.*;


/**
 * CGI Main Entry Point - Service Dispatcher
 * Routet Requests basierend auf PATH_INFO zu den entsprechenden Services
 */
public class Main {

    public static void main(String[] args) {
        try {
            CgiParameter params = new CgiParameter();
            String path = params.getPathInfo();
            String service = extractServiceFromPath(path);

            BaseService instance = null;

            switch(service) {
                case "account":
                case "login":
                case "register":
                    instance = new AccountService(params);
                    break;
                case "test":
                    instance = new TestService(params);
                    break;
                default:
                    sendError404(service);
                    return;
            }

            if (instance != null) {
                instance.execute();
            }

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Main:");
            e.printStackTrace();
            sendError500(e.getMessage());
        }
    }

    //  Error-Methoden geben NUR JSON!
    private static void sendError404(String service) {
        System.out.println("{\"error\":\"Service nicht gefunden: " + service + "\",\"timestamp\":" + System.currentTimeMillis() + "}");
        System.out.flush();
    }

    private static void sendError500(String message) {
        System.out.println("{\"error\":\"Interner Server-Fehler\",\"timestamp\":" + System.currentTimeMillis() + "}");
        System.out.flush();
    }

    /**
     * Extrahiert Service-Namen aus PATH_INFO
     * Beispiele:
     * - "/login" -> "login"
     * - "/account" -> "account"
     * - "/product/123" -> "product"
     * - null -> "test" (fallback)
     */
    private static String extractServiceFromPath(String pathInfo) {
        if (pathInfo == null || pathInfo.isEmpty() || pathInfo.equals("/")) {
            return "test"; // Fallback für Tests
        }

        // Führendes "/" entfernen
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }

        // Ersten Teil extrahieren (vor dem nächsten "/")
        int slashIndex = pathInfo.indexOf("/");
        if (slashIndex > 0) {
            return pathInfo.substring(0, slashIndex).toLowerCase();
        }

        return pathInfo.toLowerCase();
    }

}

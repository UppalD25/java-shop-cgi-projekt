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

            // Service aus Path extrahieren
            String service = extractServiceFromPath(path);

            // Service-Instanz erstellen
            BaseService serviceInstance = null;

            switch(service) {
                /*case "account":
                case "login":
                case "register":
                case "profile":
                    serviceInstance = new AccountService(params);
                    break;

                case "product":
                case "products":
                    serviceInstance = new ProductService(params);
                    break;

                case "cart":
                case "shopping-cart":
                    serviceInstance = new ShoppingCartService(params);
                    break;

                case "review":
                case "reviews":
                    serviceInstance = new ReviewService(params);
                    break;

                case "order":
                case "orders":
                case "checkout":
                    serviceInstance = new OrderService(params);
                    break;


                 */
                case "test":
                    serviceInstance = new TestService(params);
                    break;

                default:
                    sendError404(service);
                    return;
            }

            // Service ausführen
            if (serviceInstance != null) {
                serviceInstance.execute();
            }

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Main:");
            e.printStackTrace();
            sendError500(e.getMessage());
        }
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

    /**
     * Sendet 404 Error
     */
    private static void sendError404(String service) {
        System.out.println("Content-Type: application/json; charset=UTF-8");
        System.out.println();
        System.out.println("{");
        System.out.println("  \"error\": \"Service nicht gefunden: " + service + "\",");
        System.out.println("  \"timestamp\": " + System.currentTimeMillis());
        System.out.println("}");
        System.out.flush();
    }

    /**
     * Sendet 500 Error
     */
    private static void sendError500(String message) {
        System.out.println("Content-Type: application/json; charset=UTF-8");
        System.out.println();
        System.out.println("{");
        System.out.println("  \"error\": \"Interner Server-Fehler: " + message + "\",");
        System.out.println("  \"timestamp\": " + System.currentTimeMillis());
        System.out.println("}");
        System.out.flush();
    }
}

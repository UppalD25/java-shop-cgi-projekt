package org.cgi;

import org.services.baseServices.*;


/**
 * CGI Main Entry Point - Service Dispatcher
 * Routet Requests basierend auf PATH_INFO zu den entsprechenden Services
 */
public class Main {

    public static void main(String[] args) {
        try {
            CgiParameter params = new CgiParameter(); //damit man in den Services auf den Body zugreifen kann
            String path = params.getPathInfo(); //den kompletten Pfad bekomm also z.B. /login/12345 und der wird extrahier auf das Service
            String service = extractServiceFromPath(path);


            BaseService instance = null;

            switch(service) {
                case "login":
                case "register": //in login und register wird AcountService ausgeführt weil kein break
                    instance = new AccountService(params);
                    break;
                case "test":
                    instance = new TestService(params);
                    break;
                default:
                    sendError404(service); //Damit man auf der WEbsite sieht, ja da ist kein service von der Art
                    return;
            }

            if (instance != null) {
                instance.execute();
            }

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Main:");
            e.printStackTrace();
            sendError500(e.getMessage()); //Server Fehler der auf der Website angezegit wird
        }
    }
    /**
     * Extrahiert Service-Namen aus PATH_INFO
     * Beispiele:
     * - "/login" -> "login"
     * - "/account" -> "account"
     * - "/product/123" -> "product"
     * - null -> "test" (weil ich keinen absturz will)
     */
    private static String extractServiceFromPath(String pathInfo) {
        if (pathInfo == null || pathInfo.isEmpty() || pathInfo.equals("/")) {
            return "test"; // Zweiter Plan für Tests
        }

        //  "/" davor entfernen
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }

        // Ersten Teil extrahieren vor dem nächsten "/"
        int slashIndex = pathInfo.indexOf("/");
        if (slashIndex > 0) {
            return pathInfo.substring(0, slashIndex).toLowerCase();
        }

        return pathInfo.toLowerCase();
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

}

package org.services.baseService;

import org.cgi.CgiParameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Test-Service zum Verifizieren der CGI-Integration
 *
 * Test-URLs:
 * - GET  http://localhost/cgi-bin/serialisation.bat/test
 * - POST http://localhost/cgi-bin/serialisation.bat/test
 */
public class TestService extends BaseService {

    public TestService(CgiParameter params) {
        super(params);
    }

    @Override
    public void execute() {
        try {
            String method = params.getRequestMethod();

            if ("GET".equals(method)) {
                handleGet();
            } else if ("POST".equals(method)) {
                handlePost();
            } else {
                sendErrorResponse("Methode nicht unterstützt: " + method);
            }

        } catch (Exception e) {
            System.err.println("ERROR in TestService:");
            e.printStackTrace();
            sendErrorResponse("Fehler im TestService: " + e.getMessage());
        }
    }

    /**
     * GET Request Handler
     * Zeigt CGI-Parameter an
     */
    private void handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "TestService funktioniert!");
        response.put("method", "GET");

        // CGI-Metadaten
        Map<String, String> cgiData = new HashMap<>();
        cgiData.put("pathInfo", params.getPathInfo());
        cgiData.put("queryString", params.getQryString());
        cgiData.put("remoteAddr", params.getRemoteAddr());
        cgiData.put("userAgent", params.getUserAgent());
        cgiData.put("host", params.getHost());

        response.put("cgiParameters", cgiData);

        sendJsonResponse(response);
    }

    /**
     * POST Request Handler
     * Parsed JSON-Body und gibt ihn zurück
     */
    private void handlePost() {
        Map<String, Object> requestData = parseJsonBody();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "POST erfolgreich empfangen");
        response.put("method", "POST");
        response.put("receivedData", requestData);
        response.put("timestamp", System.currentTimeMillis());

        sendJsonResponse(response);
    }
}

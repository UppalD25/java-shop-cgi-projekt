package org.services.baseService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cgi.CgiParameter;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseService {

    protected CgiParameter params;
    protected ObjectMapper mapper;

    public BaseService(CgiParameter params) {
        this.params = params;
        this.mapper = new ObjectMapper();
    }

    public abstract void execute();

    //NUR JSON ausgeben, KEINE Header!
    protected void sendJsonResponse(Object data) {
        try {
            String json = mapper.writeValueAsString(data);
            System.out.println(json);
            System.out.flush();
        } catch (Exception e) {
            System.err.println("ERROR in sendJsonResponse:");
            e.printStackTrace();
        }
    }

    // Error als JSON, KEINE Header!
    protected void sendErrorResponse(String errorMessage) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", errorMessage);
        error.put("timestamp", System.currentTimeMillis());

        try {
            String json = mapper.writeValueAsString(error);
            System.out.println(json);
            System.out.flush();
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in sendErrorResponse:");
            e.printStackTrace();
        }
    }

    //  Success Response
    protected void sendSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }
        sendJsonResponse(response);
    }

    // Action aus Body holen
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

    // JSON Body parsen
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
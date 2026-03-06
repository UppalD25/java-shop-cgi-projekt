package org.cgi;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CgiParameter {


    private String qryString;
    private String remoteAddr;
    private String userAgent;
    private String referer;
    private String host;
    private String requestMethod;
    private String contentType;
    private String contentLength;
    private String pathInfo;

    private String bodyString = null;

    public String getQryString() { return qryString; }
    public String getRemoteAddr() { return remoteAddr; }
    public String getUserAgent() { return userAgent; }
    public String getReferer() { return referer; }
    public String getHost() { return host; }
    public String getRequestMethod() { return requestMethod; }
    public String getContentType() { return contentType; }
    public String getContentLength() { return contentLength; }
    public String getPathInfo() { return pathInfo; }

    private String getEnvironment(String key) {
        return System.getenv(key);
    }

    public CgiParameter() {
        // QUERY_STRING (URL encoded)
        String qryStringEncoded = getEnvironment("QUERY_STRING");
        if (qryStringEncoded != null) {
            try {
                qryString = URLDecoder.decode(qryStringEncoded, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                qryString = qryStringEncoded;
            }
        }

        remoteAddr = getEnvironment("REMOTE_ADDR");
        userAgent  = getEnvironment("HTTP_USER_AGENT");
        referer    = getEnvironment("HTTP_REFERER");
        host       = getEnvironment("HTTP_HOST");
        requestMethod = getEnvironment("REQUEST_METHOD");
        contentType   = getEnvironment("CONTENT_TYPE");
        contentLength = getEnvironment("CONTENT_LENGTH");
        pathInfo      = getEnvironment("PATH_INFO");
    }

    //  Fallback-Leselogik für JSON / chunked Body
    public String getContentFromBodyAsString() {
        if (bodyString != null) return bodyString;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            bodyString = sb.toString();
        } catch (Exception e) {
            bodyString = "";
        }

        return bodyString;
    }
}

package org.cgi;

public class Eintrag {

    public Eintrag() {
        // Standard-Konstruktor für Frameworks (z.B. Jackson)
    }

    public Eintrag(String titel, String inhalt) {
        this.titel = titel;
        this.inhalt = inhalt;
    }

    private String titel;
    private String inhalt;
    private String qryString;
    private String remoteAddr;
    private String userAgent;
    private String referer;
    private String pathInfo;

    private Data data;

    public String getQryString() {
        return qryString;
    }

    public void setQryString(String qryString) {
        this.qryString = qryString;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

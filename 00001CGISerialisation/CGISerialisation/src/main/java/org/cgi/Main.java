package org.cgi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

//CGI Common Gateway Interface

public class Main {

    public static void main(String[] args) {
        CgiParameter params = new CgiParameter();
        ObjectMapper mapper = new ObjectMapper();
        Eintrag eintrag = new Eintrag();

        System.out.println("Content-Type: text/html; charset=UTF-8");
        System.out.println();
        System.out.println("<meta charset=\"UTF-8\">");
        System.out.println("<h1>Hello World<h1>");

        try {
            // Metadaten setzen
            eintrag.setQryString(params.getQryString());
            eintrag.setRemoteAddr(params.getRemoteAddr());
            eintrag.setUserAgent(params.getUserAgent());
            eintrag.setReferer(params.getReferer());
            eintrag.setPathInfo(params.getPathInfo());
            eintrag.setTitel("Mein erster Eintrag");
            eintrag.setInhalt("keine Daten erhalten");

            // POST + JSON
            if ("POST".equals(params.getRequestMethod()) &&
                    params.getContentType() != null &&
                    params.getContentType().startsWith("application/json")) {
                String jsonData = params.getContentFromBodyAsString();
                Data data = mapper.readValue(jsonData, Data.class);
                eintrag.setData(data);
                eintrag.setInhalt(jsonData);
            }

            // Liste laden oder neu erstellen
            File file = new File("eintraege.json");
            List<Eintrag> liste;
            if (file.exists()) {
                liste = mapper.readValue(file, new TypeReference<List<Eintrag>>() {});
            } else {
                liste = new ArrayList<>();
            }

            // Bei POST hinzufügen
            if ("POST".equals(params.getRequestMethod())) {
                liste.add(eintrag);
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, liste);
            }

            // HTML-Ausgabe aller Einträge
            System.out.println("<html><head><title>Einträge</title></head><body>");
            System.out.println("<h1>Alle Einträge</h1>");

            for (Eintrag e : liste) {
                System.out.println("<div style='margin-bottom:20px;'>");
                System.out.println("<strong>Titel:</strong> " + e.getTitel() + "<br>");
                System.out.println("<strong>Inhalt:</strong> " + e.getInhalt() + "<br>");
                if (e.getData() != null) {
                    System.out.println("<strong>Name:</strong> " + e.getData().getName() + "<br>");
                    System.out.println("<strong>Email:</strong> " + e.getData().getEmail() + "<br>");
                }
                System.out.println("<hr></div>");
            }
            System.out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.flush();
    }
}
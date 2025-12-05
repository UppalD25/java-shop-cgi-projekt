package org.access.entity;

import org.mysql.entity.Account;

import java.time.LocalDateTime;

public class Bewertung {
    private int bewertung_id;
    private Account account;
    private Produkt produkt;
    private String kommentar;
    private LocalDateTime bewertungdatum;


    public Produkt getProdukt() {
        return produkt;
    }
    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }

    public int getBewertung_id() {
        return bewertung_id;
    }

    public void setBewertung_id(int id) {
        this.bewertung_id = id;
    }

    public LocalDateTime getBewertungdatum() {
        return bewertungdatum;
    }
    public void setBewertungdatum(LocalDateTime bewertungdatum) {
        this.bewertungdatum = bewertungdatum;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public String getKommentar() {
        return kommentar;
    }
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

}

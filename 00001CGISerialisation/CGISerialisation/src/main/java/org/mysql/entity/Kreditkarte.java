package org.mysql.entity;

import java.time.LocalDate;

public class    Kreditkarte {
    private int kreditkarte_id;
    private String iban;
    private LocalDate gueltigkeit;
    private String kartennummer;

    public LocalDate getGueltigkeit() {
        return gueltigkeit;
    }

    public void setGueltigkeit(LocalDate gueltigkeit) {
        this.gueltigkeit = gueltigkeit;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getKreditkarte_id() {
        return kreditkarte_id;
    }

    public void setKreditkarte_id(int kreditkarte_id) {
        this.kreditkarte_id = kreditkarte_id;
    }

    public String getKartennummer() {
        return kartennummer;
    }

    public void setKartennummer(String kartennummer) {
        this.kartennummer = kartennummer;
    }

}

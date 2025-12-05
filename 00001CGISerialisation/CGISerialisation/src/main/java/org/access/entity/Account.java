package org.access.entity;

import org.mysql.entity.Kreditkarte;
import org.mysql.entity.Warenkorb;

import java.util.List;

public class Account {
    private int account_id;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;
    private String telefonnummer;
    private List<Addresse> addresse;
    private List<Warenkorb> warenkorb;
    private List<Kreditkarte> kreditkarte;

    public List<Addresse> getAddresse() {
        return addresse;
    }

    public void setAddresse(List<Addresse> addresse) {
        this.addresse = addresse;
    }

    public List<Warenkorb> getWarenkorb() {
        return warenkorb;
    }

    public void setWarenkorb(List<Warenkorb> warenkorb) {
        this.warenkorb = warenkorb;
    }

    public List<Kreditkarte> getKreditkarte() {
        return kreditkarte;
    }

    public void setKreditkarte(List<Kreditkarte> kreditkarte) {
        this.kreditkarte = kreditkarte;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }
}

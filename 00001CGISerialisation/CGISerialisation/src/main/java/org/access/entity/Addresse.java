package org.access.entity;

public class Addresse {
    private int addresse_id;
    private String plz;
    private String stadt;
    private String strasse;
    private String tuernummer;
    private Account account;

    public int getAddresse_id() {
        return addresse_id;
    }

    public void setAddresse_id(int addresse_id) {
        this.addresse_id = addresse_id;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getTuernummer() {
        return tuernummer;
    }

    public void setTuernummer(String tuernummer) {
        this.tuernummer = tuernummer;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

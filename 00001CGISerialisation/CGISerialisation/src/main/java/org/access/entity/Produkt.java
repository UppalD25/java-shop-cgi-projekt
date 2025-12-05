package org.access.entity;

import org.mysql.entity.Bewertung;

import java.util.ArrayList;

public class Produkt {
    private int produkt_id;
    private String name;
    private double price;
    private String beschreibung;
    private ArrayList<Bewertung> Bewertung;

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public ArrayList<Bewertung> getBewertung() {
        return Bewertung;
    }

    public void setBewertung(ArrayList<Bewertung> bewertung) {
        Bewertung = bewertung;
    }

    public int getProdukt_id() {
        return produkt_id;
    }

    public void setProdukt_id(int produkt_id) {
        this.produkt_id = produkt_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

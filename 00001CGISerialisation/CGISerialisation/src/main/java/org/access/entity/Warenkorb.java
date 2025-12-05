package org.access.entity;

public class Warenkorb {
    private int warenkorb_id;
    private double price;
    private int steuern;
    private double liefergebuehr;

    public int getWarenkorb_id() {
        return warenkorb_id;
    }

    public void setWarenkorb_id(int warenkorb_id) {
        this.warenkorb_id = warenkorb_id;
    }

    public double getLiefergebuehr() {
        return liefergebuehr;
    }

    public void setLiefergebuehr(double liefergebuehr) {
        this.liefergebuehr = liefergebuehr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSteuern() {
        return steuern;
    }

    public void setSteuern(int steuern) {
        this.steuern = steuern;
    }
}

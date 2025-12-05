package org.access.entity;

import org.mysql.entity.Account;

import java.time.LocalDateTime;

public class Rechnung {
    private int rechnung_id;
    private Account account;
    private LocalDateTime erstellDatum;
    private Warenkorb warenkorb;

    public Warenkorb getWarenkorb() {
        return warenkorb;
    }

    public void setWarenkorb(Warenkorb warenkorb) {
        this.warenkorb = warenkorb;
    }

    public int getRechnung_id() {
        return rechnung_id;
    }

    public void setRechnung_id(int rechnung_id) {
        this.rechnung_id = rechnung_id;
    }

    public LocalDateTime getErstellDatum() {
        return erstellDatum;
    }

    public void setErstellDatum(LocalDateTime erstellDatum) {
        this.erstellDatum = erstellDatum;
    }

    public org.mysql.entity.Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

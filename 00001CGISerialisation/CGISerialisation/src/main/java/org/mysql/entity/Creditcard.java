package org.mysql.entity;

import java.time.LocalDate;

public class Creditcard {
    private int creditcard_id;
    private String iban;
    private LocalDate validUntil;
    private String cardnumber;
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getCreditcard_id() {
        return creditcard_id;
    }

    public void setCreditcard_id(int creditcard_id) {
        this.creditcard_id = creditcard_id;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

}

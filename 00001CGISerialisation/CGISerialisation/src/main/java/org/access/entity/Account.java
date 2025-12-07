package org.access.entity;

import org.mysql.entity.Kreditkarte;
import org.mysql.entity.Warenkorb;

import java.util.List;

public class Account {
    private int account_id;
    private String surname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private List<Address> address;
    private List<Warenkorb> shoppingCart;
    private List<Kreditkarte> creditCard;

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Warenkorb> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<Warenkorb> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Kreditkarte> getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(List<Kreditkarte> creditCard) {
        this.creditCard = creditCard;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

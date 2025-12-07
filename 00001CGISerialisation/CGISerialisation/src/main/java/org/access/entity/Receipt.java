package org.access.entity;

import org.mysql.entity.Account;

import java.time.LocalDateTime;

public class Receipt {
    private int receipt_id;
    private Account account;
    private LocalDateTime receiptDate;
    private ShoppingCart shoppingCart;

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public LocalDateTime getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDateTime receiptDate) {
        this.receiptDate = receiptDate;
    }

    public org.mysql.entity.Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

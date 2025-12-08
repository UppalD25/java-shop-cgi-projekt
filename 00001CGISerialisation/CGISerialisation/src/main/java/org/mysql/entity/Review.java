package org.mysql.entity;

import java.time.LocalDateTime;

public class Review {
    private int review_id;
    private Account account;
    private Product product;
    private String comment;
    private LocalDateTime reviewDate;


    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int id) {
        this.review_id = id;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}

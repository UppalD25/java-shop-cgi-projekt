package org.interfaces;

import org.databases.entity.Review;
import java.util.List;

public interface IReview {
    // CREATE
    void createReview(Review review);

    // READ
    Review getReviewById(int reviewId);
    List<Review> getAllReviews();
    List<Review> getReviewsByProductId(int productId);
    List<Review> getReviewsByAccountId(int accountId);

    // UPDATE
    void updateReview(Review review);

    // DELETE
    void deleteReview(int reviewId);
    void deleteReviewsByProductId(int productId);
}
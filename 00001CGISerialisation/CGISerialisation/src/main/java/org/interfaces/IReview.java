package org.interfaces;

import org.databases.entity.Review;
import java.util.List;

public interface IReview {
    // CREATE
    void createReview(Review review);
    // UPDATE
    void updateReview(Review review);
    // DELETE
    void deleteReview(int reviewId);

    List<Review> getReviewsByProductId(int productId);

    Review getReviewById(int reviewId);

    List<Review> getAllReviews();

    List<Review> getReviewsByAccountId(int accountId);


}
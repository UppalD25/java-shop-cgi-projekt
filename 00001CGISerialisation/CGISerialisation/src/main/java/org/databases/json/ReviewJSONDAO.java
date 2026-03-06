package org.databases.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.databases.entity.Review;
import org.interfaces.IReview;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewJSONDAO implements IReview{

    private static final String FILE_PATH = "data/reviews.json";
    private ObjectMapper mapper = new ObjectMapper();

    public void createReview(Review review){
        try{
            List<Review> reviews = loadAll();

            int review_id = reviews.isEmpty() ? 1 :
                    reviews.stream()
                            .mapToInt(Review::getReview_id)
                            .max()
                            .getAsInt() + 1;

            review.setReview_id(review_id);
            reviews.add(review);

            saveAll(reviews);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Review getReviewById(int reviewId){
        try {
            return loadAll().stream()
                    .filter(r -> r.getReview_id() == reviewId)
                    .findFirst()
                    .orElse(null);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<Review> getAllReviews(){
        try {
            return loadAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public List<Review> getReviewsByProductId(int productId){
        try {
            return loadAll().stream()
                    .filter(r -> r.getProduct().getProduct_id() == productId)  // ✅ Nur ID!
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public List<Review> getReviewsByAccountId(int accountId){
        try {
            return loadAll().stream()
                    .filter(r -> r.getAccount().getAccount_id() == accountId)  // ✅ Nur ID!
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void updateReview(Review review){
        try {
            List<Review> reviews = loadAll();
            for (int i = 0; i < reviews.size(); i++) {
                if (reviews.get(i).getReview_id() == review.getReview_id()) {
                    reviews.set(i, review);
                    break;
                }
            }
            saveAll(reviews);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteReview(int reviewId){
        try {
            List<Review> reviews = loadAll();
            reviews.removeIf(r -> r.getReview_id() == reviewId);
            saveAll(reviews);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void deleteReviewsByProductId(int productId){
        try {
            List<Review> reviews = loadAll();
            reviews.removeIf(r -> r.getProduct().getProduct_id() == productId);  // ✅
            saveAll(reviews);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private List<Review> loadAll() throws Exception {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveAll(new ArrayList<>());
            return new ArrayList<>();
        }

        //  Review statt Account!
        return mapper.readValue(file, new TypeReference<List<Review>>() {});
    }

    private void saveAll(List<Review> reviews) throws Exception {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(file, reviews);
    }
}
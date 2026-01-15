package org.databases.mysql.dao;

import org.databases.entity.Review;
import org.databases.mysql.setup.Connector;
import org.interfaces.IReview;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für Review-Entität
 * Verwaltet Produktbewertungen in der MySQL-Datenbank
 */
public class ReviewDAO implements IReview {

    /**
     * Erstellt eine neue Review in der Datenbank
     * VERWENDET: Timestamp.valueOf() für LocalDateTime → SQL Timestamp Konvertierung
     *
     * @param review Die zu speichernde Review (muss Product und Account haben)
     */
    public void createReview(Review review) {
        String sql = "INSERT INTO Review (Comment, ReviewDate, Product_Product_ID, Account_Account_ID) VALUES (?, ?, ?, ?)";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, review.getComment());
            // LocalDateTime zu SQL Timestamp konvertieren
            stmt.setTimestamp(2, Timestamp.valueOf(review.getReviewDate()));
            stmt.setInt(3, review.getProduct().getProduct_id());
            stmt.setInt(4, review.getAccount().getAccount_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Review");
            e.printStackTrace();
        }
    }

    /**
     * Lädt eine Review anhand ihrer ID
     * VERWENDET: toLocalDateTime() für SQL Timestamp → LocalDateTime Konvertierung
     *
     * @param reviewId Die ID der gesuchten Review
     * @return Review-Objekt oder null wenn nicht gefunden
     */
    public Review getReviewById(int reviewId) {
        String sql = "SELECT * FROM Review WHERE Review_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Review review = new Review();
                review.setReview_id(rs.getInt("Review_ID"));
                review.setComment(rs.getString("Comment"));
                // SQL Timestamp zu LocalDateTime konvertieren
                review.setReviewDate(rs.getTimestamp("ReviewDate").toLocalDateTime());
                return review;
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Review mit ID: " + reviewId);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lädt alle Reviews aus der Datenbank
     *
     * @return Liste aller Reviews (leer wenn keine vorhanden)
     */
    public List<Review> getAllReviews() {
        String sql = "SELECT * FROM Review";
        List<Review> reviews = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Review review = new Review();
                review.setReview_id(rs.getInt("Review_ID"));
                review.setComment(rs.getString("Comment"));
                review.setReviewDate(rs.getTimestamp("ReviewDate").toLocalDateTime());
                reviews.add(review);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen aller Reviews");
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Lädt alle Reviews für ein bestimmtes Produkt
     * Nützlich für Produktseite um alle Bewertungen anzuzeigen
     *
     * @param productId Die ID des Produkts
     * @return Liste der Reviews für dieses Produkt
     */
    public List<Review> getReviewsByProductId(int productId) {
        String sql = "SELECT * FROM Review WHERE Product_Product_ID = ?";
        List<Review> reviews = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setReview_id(rs.getInt("Review_ID"));
                review.setComment(rs.getString("Comment"));
                review.setReviewDate(rs.getTimestamp("ReviewDate").toLocalDateTime());
                reviews.add(review);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Reviews für Produkt ID: " + productId);
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Lädt alle Reviews die ein bestimmter Account geschrieben hat
     * Nützlich für "Meine Bewertungen" Seite
     *
     * @param accountId Die ID des Accounts
     * @return Liste der Reviews von diesem Account
     */
    public List<Review> getReviewsByAccountId(int accountId) {
        String sql = "SELECT * FROM Review WHERE Account_Account_ID = ?";
        List<Review> reviews = new ArrayList<>();

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setReview_id(rs.getInt("Review_ID"));
                review.setComment(rs.getString("Comment"));
                review.setReviewDate(rs.getTimestamp("ReviewDate").toLocalDateTime());
                reviews.add(review);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Reviews für Account ID: " + accountId);
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Aktualisiert eine bestehende Review
     *
     * @param review Das Review-Objekt mit neuen Daten (muss ID haben)
     */
    public void updateReview(Review review) {
        String sql = "UPDATE Review SET Comment = ?, ReviewDate = ?, Product_Product_ID = ?, Account_Account_ID = ? WHERE Review_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, review.getComment());
            stmt.setTimestamp(2, Timestamp.valueOf(review.getReviewDate()));
            stmt.setInt(3, review.getProduct().getProduct_id());
            stmt.setInt(4, review.getAccount().getAccount_id());
            stmt.setInt(5, review.getReview_id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Updaten der Review");
            e.printStackTrace();
        }

    }

    public void deleteReview(int reviewId){
        String sql = "DELETE FROM Review WHERE Review_ID = ?";

        try(Connection connection = Connector.getConnectionToMySQL();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, reviewId);
            stmt.execute();
        }catch (SQLException e){
            System.err.println("Fehler beim löschen der Review mit der ID: " + reviewId);
            e.printStackTrace();
        }
    }
    /**
     * Löscht ALLE Reviews eines Produkts
     * Nützlich wenn ein Produkt gelöscht wird
     * @param productId Die ID des Produkts
     */
    public void deleteReviewsByProductId(int productId) {
        String sql = "DELETE FROM Review WHERE Product_Product_ID = ?";

        try (Connection conn = Connector.getConnectionToMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen der Reviews für Produkt ID: " + productId);
            e.printStackTrace();
        }
    }
}
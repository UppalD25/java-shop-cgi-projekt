package org.interfaces;

import org.databases.entity.Creditcard;
import java.util.List;

public interface ICreditcard {
    // CREATE
    void createCreditcard(Creditcard creditcard);
    // UPDATE
    void updateCreditcard(Creditcard creditcard);
    // DELETE
    void deleteCreditcard(int creditcardId);
    // ist die Karte veraltet?
    boolean isCardExpired(int creditcardId);
    List<Creditcard> getAllCreditcards();

    /*
    später vielleicht
    Creditcard getCreditcardById(int creditcardId);
    List<Creditcard> getCreditcardsByAccountId(int accountId);
    */
}

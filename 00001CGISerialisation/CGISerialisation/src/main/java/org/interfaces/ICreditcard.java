package org.interfaces;

import org.databases.entity.Creditcard;
import java.util.List;

public interface ICreditcard {
    // CREATE
    void createCreditcard(Creditcard creditcard);

    // READ
    Creditcard getCreditcardById(int creditcardId);
    List<Creditcard> getAllCreditcards();
    List<Creditcard> getCreditcardsByAccountId(int accountId);

    // UPDATE
    void updateCreditcard(Creditcard creditcard);

    // DELETE
    void deleteCreditcard(int creditcardId);

    // VALIDATION
    boolean isCardExpired(int creditcardId);
}
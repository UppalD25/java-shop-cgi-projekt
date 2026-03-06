package org.interfaces;

import org.databases.entity.Receipt;
import java.time.LocalDateTime;
import java.util.List;

public interface IReceipt {
    // CREATE
    void createReceipt(Receipt receipt);
    // UPDATE
    void updateReceipt(Receipt receipt);
    // DELETE
    void deleteReceipt(int receiptId);
    Receipt getReceiptById(int receiptId);
    List<Receipt> getAllReceipts();
    List<Receipt> getReceiptsByAccountId(int accountId);


    /* später vielleicht
    List<Receipt> getReceiptsByShoppingCartId(int shoppingCartId);
    List<Receipt> getReceiptsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
     double getReceiptTotal(int receiptId);
     */

}
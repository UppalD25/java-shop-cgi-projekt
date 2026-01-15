package org.interfaces;

import org.databases.entity.Receipt;
import java.time.LocalDateTime;
import java.util.List;

public interface IReceipt {
    // CREATE
    void createReceipt(Receipt receipt);

    // READ
    Receipt getReceiptById(int receiptId);
    List<Receipt> getAllReceipts();
    List<Receipt> getReceiptsByAccountId(int accountId);
    List<Receipt> getReceiptsByShoppingCartId(int shoppingCartId);
    List<Receipt> getReceiptsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // UPDATE
    void updateReceipt(Receipt receipt);

    // DELETE
    void deleteReceipt(int receiptId);

    // BUSINESS LOGIC
    double getReceiptTotal(int receiptId);
}
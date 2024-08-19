package com.htt.ecourse.service;


import com.htt.ecourse.dtos.ReceiptDTO;
import com.htt.ecourse.pojo.Receipt;

import java.util.List;

public interface ReceipService {
    List<Receipt> getAllReceipts();
    List<Receipt> findByUserId(Long userId);
    Receipt getReceipt(Long id);
    Receipt createReceipt(ReceiptDTO receiptDTO);
    Receipt updateReceipt(Long receiptId, ReceiptDTO receiptDTO);
    void deleteReceipt(Long id);
}

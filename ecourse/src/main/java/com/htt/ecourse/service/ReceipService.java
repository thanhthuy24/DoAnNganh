package com.htt.ecourse.service;


import com.htt.ecourse.dtos.ReceiptDTO;
import com.htt.ecourse.pojo.Cart;
import com.htt.ecourse.pojo.Receipt;
import com.htt.ecourse.responses.ReceiptResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ReceipService {
    Page<ReceiptResponse> getAllReceipts(PageRequest pageRequest);
    List<Receipt> findByUserId(Long userId);
    Receipt getReceipt(Long id);
    Receipt createReceipt(ReceiptDTO receiptDTO);
    Receipt updateReceipt(Long receiptId, ReceiptDTO receiptDTO);
    void deleteReceipt(Long id);
    void addReceipt(List<Cart> cartList);
}

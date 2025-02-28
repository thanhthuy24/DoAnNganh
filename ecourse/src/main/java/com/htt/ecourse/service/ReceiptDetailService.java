package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ReceiptDetailDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Receiptdetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiptDetailService {
    List<Receiptdetail> findByReceiptId(Long receiptId);
    Receiptdetail getReceiptDetail(Long id);
    Receiptdetail createReceiptDetail(ReceiptDetailDTO receiptDetailDTO) throws DataNotFoundException;
    Page<Receiptdetail> getAllReceiptDetails(Pageable pageable, String keyword);
}

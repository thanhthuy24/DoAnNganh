package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ReceiptDetailDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Receiptdetail;

import java.util.List;

public interface ReceiptDetailService {
    List<Receiptdetail> findByReceiptId(Long receiptId);
    Receiptdetail getReceiptDetail(Long id);
    Receiptdetail createReceiptDetail(ReceiptDetailDTO receiptDetailDTO) throws DataNotFoundException;
}

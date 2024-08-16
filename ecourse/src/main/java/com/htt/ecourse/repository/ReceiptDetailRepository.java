package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Receiptdetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptDetailRepository extends JpaRepository<Receiptdetail, Long> {
    List<Receiptdetail> findReceiptDetailById (Long id);
}

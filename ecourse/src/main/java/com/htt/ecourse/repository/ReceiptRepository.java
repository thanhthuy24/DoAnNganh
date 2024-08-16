package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findByUserId(Long userId);
}

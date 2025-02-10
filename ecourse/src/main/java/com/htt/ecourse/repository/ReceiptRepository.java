package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Cart;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findByUserId(Long userId);
    @Query("SELECT r FROM Receipt r WHERE LOWER(r.user.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Receipt> searchReceiptsAll(@Param("keyword") String keyword, Pageable pageable);

}

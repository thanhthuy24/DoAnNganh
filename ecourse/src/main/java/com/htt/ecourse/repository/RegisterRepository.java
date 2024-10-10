package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterRepository extends JpaRepository<Register, Long> {
    List<Register> findByStatus(Boolean status);
    Register findByUserId(Long userId);
}

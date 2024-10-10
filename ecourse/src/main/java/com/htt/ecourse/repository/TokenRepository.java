package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}

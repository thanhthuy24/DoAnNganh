package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUserId(Long userId);
    List<Token> findByUserIdIn(List<Long> userIds);
    Optional<Token> findByToken(String token);
    void deleteByUserId(Long userId);
    Token findByTokenAndUserId(String token, Long userId);
}

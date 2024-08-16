package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    //SELECT * FROM users where username = ?
}

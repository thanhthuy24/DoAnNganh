package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Commentlike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Commentlike, Long> {
}

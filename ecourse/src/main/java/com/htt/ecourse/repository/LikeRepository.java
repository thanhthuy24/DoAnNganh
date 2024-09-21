package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Commentlike;
import com.htt.ecourse.pojo.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Commentlike, Long> {
    Page<Commentlike> findByCommentId(Long commentId, PageRequest pageRequest);
    Optional<Commentlike> findByUserIdAndCommentId(Long userId, Long commentId);
    Long countLikeByCommentId(Long commentId);
}

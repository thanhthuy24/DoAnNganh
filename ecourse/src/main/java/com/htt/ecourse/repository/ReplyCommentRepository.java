package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Replycomment;
import com.htt.ecourse.responses.ReplyCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<Replycomment, Long> {
    Page<Replycomment> findByCommentId(Long commentId, Pageable pageable);
}

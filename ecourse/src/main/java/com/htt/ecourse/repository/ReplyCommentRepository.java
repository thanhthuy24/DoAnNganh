package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Replycomment;
import com.htt.ecourse.responses.ReplyCommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<Replycomment, Long> {
    List<Replycomment> findByCommentId(Long commentId);
}

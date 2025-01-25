package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ReplyDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Replycomment;
import com.htt.ecourse.responses.ReplyCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyCommentService {
    Replycomment createReplyComment(ReplyDTO replyDTO) throws DataNotFoundException;
    Page<Replycomment> getReplyByCommentId(Long commentId, Pageable pageable) throws DataNotFoundException;
}

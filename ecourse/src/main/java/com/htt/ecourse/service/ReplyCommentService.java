package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ReplyDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Replycomment;
import com.htt.ecourse.responses.ReplyCommentResponse;

import java.util.List;

public interface ReplyCommentService {
    Replycomment createReplyComment(ReplyDTO replyDTO) throws DataNotFoundException;
    List<Replycomment> getReplyByCommentId(Long commentId) throws DataNotFoundException;
}

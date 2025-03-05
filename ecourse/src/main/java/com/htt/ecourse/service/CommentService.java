package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CommentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Comment;
import com.htt.ecourse.responses.CommentResponse;
import com.htt.ecourse.responses.list.CommentListResponse;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
    Comment createComment(CommentDTO commentDTO) throws DataNotFoundException, JSONException;
//    Page<CommentResponse> getCommentsByLessonId(Long lessonId, PageRequest pageRequest) throws DataNotFoundException;
    Page<Comment> getComments(Long lessonId, PageRequest pageRequest) throws DataNotFoundException;
    Comment createCommentChild(CommentDTO commentDTO, Long commentId) throws DataNotFoundException;
    Long countCommentByLessonId(Long lessonId);
}

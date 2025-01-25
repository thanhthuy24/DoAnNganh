package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.ReplyDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Comment;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Replycomment;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.CommentRepository;
import com.htt.ecourse.repository.LessonRepository;
import com.htt.ecourse.repository.ReplyCommentRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.responses.AssignmentResponse;
import com.htt.ecourse.responses.ReplyCommentResponse;
import com.htt.ecourse.service.ReplyCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyCommentServiceImpl implements ReplyCommentService {
    private final ReplyCommentRepository replyCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @Override
    public Replycomment createReplyComment(ReplyDTO replyDTO) throws DataNotFoundException {
        Comment existingComment = commentRepository.findById(replyDTO.getCommentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found!!!"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Replycomment newReplyComment = Replycomment.builder()
                .content(replyDTO.getContent())
                .comment(existingComment)
                .user(user)
                .createdDate(new Date())
                .build();

        replyCommentRepository.save(newReplyComment);

        return newReplyComment;
    }

    @Override
    public Page<Replycomment> getReplyByCommentId(Long commentId, Pageable pageable) throws DataNotFoundException {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found!!"));

        return replyCommentRepository.findByCommentId(commentId, pageable)
                .map(Replycomment::fromReplyComment);
    }
}

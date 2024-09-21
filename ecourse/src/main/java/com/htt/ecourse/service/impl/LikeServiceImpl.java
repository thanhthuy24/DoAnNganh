package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.LikeDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.*;
import com.htt.ecourse.responses.CommentResponse;
import com.htt.ecourse.responses.LikeResponse;
import com.htt.ecourse.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public Commentlike createLike(LikeDTO likeDTO) throws DataNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Long userId = user.getId();

        Comment existingComment = commentRepository.findById(likeDTO.getCommentId())
                .orElseThrow(() -> new DataNotFoundException("Comment not found"));

        Optional<Enrollment> existingEnrollment = enrollmentRepository
                .findByUserIdAndCourseId(userId, existingComment.getLesson().getCourse().getId());

        if (existingEnrollment == null || !existingEnrollment.isPresent()) {
            throw new DataNotFoundException("Enrollment not found");
        }

        Optional<Commentlike> existingLike = likeRepository.findByUserIdAndCommentId(userId, existingComment.getId());

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return null;
        }

        Commentlike newLike = Commentlike.builder()
                .createdDate(new Date())
                .comment(existingComment)
                .user(user)
                .build();

        likeRepository.save(newLike);
        return newLike;
    }

    @Override
    public Page<LikeResponse> getLikeByCommentId(Long commentId, PageRequest pageRequest) throws DataNotFoundException {
        Page<Commentlike> list = likeRepository.findByCommentId(commentId, pageRequest);

        return list.map(LikeResponse::fromLike);
    }

    @Override
    public Long countLike(Long commentId) {
        return likeRepository.countLikeByCommentId(commentId);

    }
}

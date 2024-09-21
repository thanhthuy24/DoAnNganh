package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.LikeDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.repository.CommentRepository;
import com.htt.ecourse.responses.CommentResponse;
import com.htt.ecourse.responses.LikeResponse;
import com.htt.ecourse.responses.list.CommentListResponse;
import com.htt.ecourse.responses.list.LikeListResponse;
import com.htt.ecourse.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/like")
public class ApiLikeController {
    private final LikeService likeService;
    private final CommentRepository commentRepository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createLike(
            @RequestBody LikeDTO likeDTO,
            BindingResult rs
            ) throws DataNotFoundException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        likeService.createLike(likeDTO);
        return ResponseEntity.ok(likeDTO);
    }

    @GetMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getLikeComment(
            @PathVariable Long commentId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) throws DataNotFoundException {
        if (commentRepository.findById(commentId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found.");
        }

        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdDate").descending());
        Page<LikeResponse> likeResponsePage = likeService.getLikeByCommentId(commentId, pageRequest);

        // lay tong so trang
        int totalPage = likeResponsePage.getTotalPages();
        List<LikeResponse> likes = likeResponsePage.getContent();
        return ResponseEntity.ok(LikeListResponse.builder()
                .likes(likes)
                .totalPages(totalPage)
                .build());

    }

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getLikes(
            @PathVariable Long commentId
    ){
        return ResponseEntity.ok(likeService.countLike(commentId));
    }
}

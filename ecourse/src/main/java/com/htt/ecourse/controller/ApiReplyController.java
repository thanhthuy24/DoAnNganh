package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.ReplyDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Comment;
import com.htt.ecourse.pojo.Replycomment;
import com.htt.ecourse.responses.ReplyCommentResponse;
import com.htt.ecourse.responses.list.CommentListRes;
import com.htt.ecourse.responses.list.ReplyCommentListResponse;
import com.htt.ecourse.service.ReplyCommentService;
import jakarta.validation.Valid;
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
@RequestMapping("api/reply")
public class ApiReplyController {
    private final ReplyCommentService replyCommentService;

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReplyCommentListResponse> getReplyComment(
            @PathVariable Long commentId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) throws DataNotFoundException {
//        List<Replycomment> list = replyCommentService.getReplyByCommentId(commentId);
//        return ResponseEntity.ok(list);
        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdDate").descending());
        Page<Replycomment> list = replyCommentService.getReplyByCommentId(commentId, pageRequest);

        int totalPages = list.getTotalPages();

        List<Replycomment> replycomments = list.getContent();
        return ResponseEntity.ok(ReplyCommentListResponse.builder()
                .replycomments(replycomments)
                .totalPages(totalPages)
                .build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createReplyComment (
            @Valid
            @RequestBody ReplyDTO replyDTO,
            BindingResult rs
    ) throws DataNotFoundException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        replyCommentService.createReplyComment(replyDTO);
        return ResponseEntity.ok(replyDTO);
    }
}

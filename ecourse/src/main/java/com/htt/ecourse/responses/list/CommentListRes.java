package com.htt.ecourse.responses.list;

import com.htt.ecourse.pojo.Comment;
import com.htt.ecourse.responses.CommentResponse;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentListRes {
    private List<Comment> comments;
    private int totalPages;
}

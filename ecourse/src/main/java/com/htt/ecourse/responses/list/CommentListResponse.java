package com.htt.ecourse.responses.list;

import com.htt.ecourse.responses.CommentResponse;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponse {
    private List<CommentResponse> comments;
    private int totalPages;
}

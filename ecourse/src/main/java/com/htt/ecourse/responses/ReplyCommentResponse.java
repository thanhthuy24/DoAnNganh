package com.htt.ecourse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Replycomment;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyCommentResponse {
    private String content;
    private Date createdDate;
    @JsonProperty("comment_id")
    private Long commentId;
    @JsonProperty("user_id")
    private Long userId;

    public static ReplyCommentResponse fromReplyComment(Replycomment replycomment) {
        ReplyCommentResponse replyCommentResponse = ReplyCommentResponse.builder()
                .content(replycomment.getContent())
                .commentId(replycomment.getComment().getId())
                .userId(replycomment.getUser().getId())
                .build();
        replyCommentResponse.setCreatedDate(replycomment.getCreatedDate());
        return replyCommentResponse;
    }
}

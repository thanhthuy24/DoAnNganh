package com.htt.ecourse.responses.list;

import com.htt.ecourse.pojo.Comment;
import com.htt.ecourse.pojo.Replycomment;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCommentListResponse {
    private List<Replycomment> replycomments;
    private int totalPages;
}

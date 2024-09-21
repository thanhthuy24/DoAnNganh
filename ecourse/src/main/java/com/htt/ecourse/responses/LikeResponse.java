package com.htt.ecourse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htt.ecourse.pojo.Commentlike;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeResponse {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("comment_id")
    private Long commentId;

    public static LikeResponse fromLike(Commentlike like) {
        LikeResponse likeResponse = LikeResponse.builder()
                .commentId(like.getComment().getId())
                .userId(like.getUser().getId())
                .build();
        return likeResponse;
    }
}

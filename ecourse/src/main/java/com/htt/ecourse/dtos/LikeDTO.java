package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("comment_id")
    private Long commentId;
}

package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    @NotNull
    @Size(min = 2, max = 4000, message = "Comment must be between 2 and 200 characters")
    private String content;
    @JsonProperty("comment_id")
    private Long commentId;
    @JsonProperty("user_id")
    private Long userId;
}

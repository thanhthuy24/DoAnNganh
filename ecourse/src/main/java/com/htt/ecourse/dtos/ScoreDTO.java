package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreDTO {
    private String feedback;

    private Float score;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("assignment_id")
    private Long assignmentId;
}

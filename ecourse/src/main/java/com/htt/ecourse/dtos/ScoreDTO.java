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
    @Size(min = 1, max = 255, message = "Feedback must be between 1 and 255 characters!")
    private String feedback;
    @NotNull
    private Float score;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("assignment_id")
    private Long assignmentId;
}

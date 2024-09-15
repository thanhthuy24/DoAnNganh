package com.htt.ecourse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerChoiceResponse {
    private Long id;
    @JsonProperty("assignment_id")
    private Long assignmentId;
    @JsonProperty("question_id")
    private Long questionId;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("choice_id")
    private Long choiceId;
}

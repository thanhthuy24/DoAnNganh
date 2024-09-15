package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Choice;
import com.htt.ecourse.pojo.Question;
import com.htt.ecourse.pojo.User;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerChoiceDTO {
    @JsonProperty("assignment_id")
    private Long assignmentId;
    @JsonProperty("question_id")
    private Long questionId;
    @JsonProperty("choice_id")
    private Long choiceId;
    @JsonProperty("user_id")
    private Long userId;
}

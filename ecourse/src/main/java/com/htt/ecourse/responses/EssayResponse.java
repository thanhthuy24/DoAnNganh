package com.htt.ecourse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htt.ecourse.pojo.Essay;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EssayResponse {
    private String content;
    @JsonProperty("created_date")
    private Date createdDate;
    @JsonProperty("assignment_id")
    private Long assignmentId;
    @JsonProperty("question_id")
    private Long questionId;
    @JsonProperty("user_id")
    private Long userId;

    public static EssayResponse fromEssay(Essay essay) {
        EssayResponse essayResponse = EssayResponse.builder()
                .content(essay.getContent())
                .assignmentId(essay.getAssignment().getId())
                .userId(essay.getUser().getId())
                .questionId(essay.getQuestion().getId())
                .build();
        essayResponse.setCreatedDate(essay.getCreatedDate());
        return essayResponse;
    }
}
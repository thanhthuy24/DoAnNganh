package com.htt.ecourse.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    private String content;
    private Long assignmentId;
}

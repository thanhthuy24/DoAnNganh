package com.htt.ecourse.dtos;

import com.htt.ecourse.pojo.Choice;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionChoiceDTO {
    private Long id;
    private String content;
    private List<Choice> choices;
}


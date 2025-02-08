package com.htt.ecourse.dtos;

import com.htt.ecourse.pojo.Essay;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionEssayDTO {
    private Long id;
    private String content;
    private List<Essay> essays;
}

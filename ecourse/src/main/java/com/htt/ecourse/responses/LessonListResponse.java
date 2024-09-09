package com.htt.ecourse.responses;

import com.htt.ecourse.pojo.Lesson;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonListResponse {
    private List<LessonResponse> lessons;
    private int totalPages;
}

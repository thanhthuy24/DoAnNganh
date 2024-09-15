package com.htt.ecourse.responses.list;

import com.htt.ecourse.responses.LessonResponse;
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

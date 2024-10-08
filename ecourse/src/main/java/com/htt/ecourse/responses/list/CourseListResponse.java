package com.htt.ecourse.responses.list;

import com.htt.ecourse.pojo.Course;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseListResponse {
    private List<Course> courses;
    private int totalPages;
}

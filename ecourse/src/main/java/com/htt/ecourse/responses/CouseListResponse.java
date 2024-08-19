package com.htt.ecourse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.htt.ecourse.dtos.CourseDTO;
import com.htt.ecourse.pojo.Course;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouseListResponse {
    private List<Course> courses;
    private int totalPages;
}
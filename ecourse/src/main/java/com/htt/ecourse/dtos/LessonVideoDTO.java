package com.htt.ecourse.dtos;

import com.htt.ecourse.pojo.Course;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonVideoDTO {
    private Long id;
    private String name;
    private String description;
    private List<VideoListDTO> videos;
//    private CourseDTO course;
}

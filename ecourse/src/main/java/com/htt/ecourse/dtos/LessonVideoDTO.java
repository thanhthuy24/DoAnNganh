package com.htt.ecourse.dtos;

import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Video;
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
    private List<Video> videos;
//    private CourseDTO course;
}

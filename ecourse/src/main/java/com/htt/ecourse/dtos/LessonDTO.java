package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htt.ecourse.pojo.Course;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    @Size(min = 2, max = 200, message = "Lesson's name must be between 2 and 200 characters")
    @JsonProperty("name")
    private String name;
    @Size(min = 2, max = 200, message = "Description must be between 2 and 200 characters")
    @JsonProperty("description")
    private String description;
    @JsonProperty("course_id")
    private Long courseId;
}

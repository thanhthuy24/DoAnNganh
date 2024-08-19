package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htt.ecourse.pojo.Lesson;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {
    @Size(min = 5, max = 200, message = "Video's name")
    @JsonProperty("name")
    private String name;
    @Min(value = 1, message = "Lesson's ID must be > 0")
    @JsonProperty("lesson_id")
    private Long lessonId;
}
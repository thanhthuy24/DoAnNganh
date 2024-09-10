package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {
    private Date enrollmentDate;
    @JsonProperty("course_id")
    private Long courseId;
//    @JsonProperty("user_id")
//    private Long userId;
}

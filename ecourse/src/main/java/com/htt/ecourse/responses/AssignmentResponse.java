package com.htt.ecourse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Lesson;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentResponse {
    private String name;
    private Date createdDate;
    private Date dueDate;
    @JsonProperty("tag_id")
    private Long tagId;
    @JsonProperty("course_id")
    private Long courseId;
    @JsonProperty("lesson_id")
    private Long lessonId;
    public static AssignmentResponse fromAssignment(Assignment assignment) {
        AssignmentResponse assignmentResponse = AssignmentResponse.builder()
                .name(assignment.getName())
                .tagId(assignment.getTag().getId())
                .courseId(assignment.getCourse().getId())
                .lessonId(assignment.getLesson().getId())
                .build();
        assignmentResponse.setCreatedDate(assignment.getCreatedDate());
        assignmentResponse.setDueDate(assignment.getDueDate());
        return assignmentResponse;
    }
}

package com.htt.ecourse.responses;

import com.htt.ecourse.pojo.Teacher;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherListResponse {
    private List<Teacher> teachers;
    private int totalPages;
}

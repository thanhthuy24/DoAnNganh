package com.htt.ecourse.responses;

import com.htt.ecourse.pojo.Assignment;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentListResponse {
    private List<Assignment> assignments;
    private int totalPages;
}

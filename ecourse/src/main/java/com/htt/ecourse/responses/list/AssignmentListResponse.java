package com.htt.ecourse.responses.list;

import com.htt.ecourse.responses.AssignmentResponse;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentListResponse {
    private List<AssignmentResponse> assignments;
    private int totalPages;
}

package com.htt.ecourse.responses.list;

import com.htt.ecourse.responses.LikeResponse;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeListResponse {
    private List<LikeResponse> likes;
    private int totalPages;
}

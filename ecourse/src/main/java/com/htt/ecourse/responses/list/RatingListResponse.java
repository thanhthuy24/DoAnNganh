package com.htt.ecourse.responses.list;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htt.ecourse.pojo.Courserating;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingListResponse {
    private List<Courserating> ratings;
    private int totalPages;
}

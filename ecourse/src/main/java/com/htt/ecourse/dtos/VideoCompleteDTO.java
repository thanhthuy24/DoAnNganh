package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoCompleteDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("video_id")
    private Long videoId;
}

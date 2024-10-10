package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String token;
    @JsonProperty("user_id")
    private Long userId;
}

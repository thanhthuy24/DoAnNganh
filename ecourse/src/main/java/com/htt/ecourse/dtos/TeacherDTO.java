package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    @NotNull
    @Size(min = 2, max = 4000, message = "Position's teacher must be between 2 and 4000 characters")
    private String position;
    @NotNull
    @Size(min = 2, max = 4000, message = "Description's teacher must be between 2 and 4000 characters")
    private String description;
    @JsonProperty("user_id")
    private Long userId;
}
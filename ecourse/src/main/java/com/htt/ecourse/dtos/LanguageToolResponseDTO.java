package com.htt.ecourse.dtos;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageToolResponseDTO {
    private String message;
    private String shortMessage;
    private List<String> replacements;
}

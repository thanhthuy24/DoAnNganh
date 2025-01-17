package com.htt.ecourse.dtos;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}

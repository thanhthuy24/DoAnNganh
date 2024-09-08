package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phone;
    private String avatar;
    private Date dateOfBirth;
    @JsonProperty("facebook_account")
    private int facebookAccountId;
    @JsonProperty("google_account")
    private int googleAccountId;

    @JsonIgnore
    private MultipartFile file;
}

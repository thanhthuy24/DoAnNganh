package com.htt.ecourse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    @NotNull
    @Size(min = 2, max = 200, message = "Course's name must be between 2 and 200 characters")
    private String name;
    private String description;
    private String image;
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 10000000, message = "Price must be less than or equal to 10,000,000")
    private float price;
    private float discount;
    @JsonProperty("category_id")
    private Long categoryId;

//    private List<MultipartFile> files;
    private MultipartFile file;
}

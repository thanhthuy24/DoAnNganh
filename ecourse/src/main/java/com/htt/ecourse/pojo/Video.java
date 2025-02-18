package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "video")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    public static final int MAXIMUM_VIDEOS_PER_LESSON = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 400)
    @NotNull
    @Column(name = "description", nullable = false, length = 400)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

}
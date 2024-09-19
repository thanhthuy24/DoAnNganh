package com.htt.ecourse.pojo;

import com.htt.ecourse.responses.AssignmentResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courserating")
public class Courserating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rating_date")
    private Date ratingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotNull
    @Column(name = "rating")
    private Long rating;

    @Size(min = 1, max = 255, message = "Comment is required!!")
    @Column(name = "comment")
    private String comment;

    @PrePersist
    protected void onCreate() {
        this.ratingDate = new Date();
    }

    public static Courserating fromRating(Courserating courserating) {
        Courserating courseRating = Courserating.builder()
                .rating(courserating.getRating())
                .course(courserating.getCourse())
                .user(courserating.getUser())
                .comment(courserating.getComment())
                .build();
        courseRating.setRatingDate(courserating.getRatingDate());
        return courseRating;
    }

}
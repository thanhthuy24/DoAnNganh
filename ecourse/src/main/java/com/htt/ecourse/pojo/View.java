package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "views")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "view_count")
    private Long viewCount;

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
    }

}
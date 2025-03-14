package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 4000)
    @NotNull
    @Column(name = "content", length = 4000)
    private String content;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 255)
    @Column(name = "sentiment")
    private String sentiment;


    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
    }

    public static Comment fromComment(Comment comment) {
        Comment cmt = Comment.builder()
                .id(comment.getId())
                .lesson(comment.getLesson())
                .content(comment.getContent())
                .user(comment.getUser())
                .build();
        cmt.setCreatedDate(comment.getCreatedDate());
        return cmt;
    }

}
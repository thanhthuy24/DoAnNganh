package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "replycomment")
public class Replycomment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 4000)
    @Column(name = "content", length = 4000)
    private String content;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @PreUpdate
    protected void onUpdate() {
        this.createdDate = new Date();
    }

    public static Replycomment fromReplyComment(Replycomment replyComment) {
        Replycomment reply = Replycomment.builder()
                .id(replyComment.getId())
                .comment(replyComment.getComment())
                .content(replyComment.getContent())
                .user(replyComment.getUser())
                .build();
        reply.setCreatedDate(replyComment.getCreatedDate());
        return reply;
    }

}
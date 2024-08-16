package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "token")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "token")
    private String token;

    @Size(max = 50)
    @Column(name = "token_type", length = 50)
    private String tokenType;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "revoked")
    private Boolean revoked;

    @Column(name = "expired")
    private Boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
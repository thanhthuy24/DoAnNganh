package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "socialaccount")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Socialaccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "provider", length = 20)
    private String provider;

    @Size(max = 50)
    @Column(name = "provider_id", length = 50)
    private String providerId;

    @Size(max = 150)
    @Column(name = "email", length = 150)
    private String email;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
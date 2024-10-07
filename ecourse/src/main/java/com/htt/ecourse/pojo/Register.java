package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "register")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 4000)
    @Column(name = "reason", length = 4000)
    private String reason;

    @Size(max = 255)
    @Column(name = "position", length = 255)
    private String position;

    @Column(name = "status")
    private Boolean status;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
package com.htt.ecourse.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "teacher")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 2, max = 4000, message = "Position's teacher must be between 2 and 4000 characters")
    @Column(name = "position", length = 4000)
    private String position;

    @NotNull
    @Size(min = 2, max = 4000, message = "Position's teacher must be between 2 and 4000 characters")
    @Column(name = "description", length = 4000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

}
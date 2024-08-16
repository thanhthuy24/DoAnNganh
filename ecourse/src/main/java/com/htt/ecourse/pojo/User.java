package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "user")
    private Set<Receipt> receipts = new LinkedHashSet<>();

    @Size(max = 100)
    @Column(name = "username", length = 100)
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 10)
    @Column(name = "phone", length = 10)
    private String phone;

    @Size(max = 255)
    @Column(name = "firstName")
    private String firstName;

    @Size(max = 255)
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "google_account_id")
    private int googleAccount;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facebook_account_id")
    private int facebookAccount;

    @OneToMany(mappedBy = "user")
    private Set<Token> tokens = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

}
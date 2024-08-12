package com.htt.ecourse.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @ColumnDefault("1")
    @Column(name = "isActive")
    private Boolean isActive;

    @NotNull
    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "updatedDate", nullable = false)
    private Instant updatedDate;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "discount", precision = 10)
    private BigDecimal discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
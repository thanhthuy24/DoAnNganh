package com.htt.ecourse.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "receiptdetail")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receiptdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "price")
    private Float price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_money")
    private Float totalMoney;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

}
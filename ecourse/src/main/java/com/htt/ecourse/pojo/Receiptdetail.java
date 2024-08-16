package com.htt.ecourse.pojo;

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
    private Integer id;

    @Column(name = "price")
    private Float price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "totalMoney")
    private Float totalMoney;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiptId")
    private Receipt receipt;

}
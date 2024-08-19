package com.htt.ecourse.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "receipt")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_money")
    private Float totalMoney;

    @Size(max = 100)
    @Column(name = "payment_method", length = 100)
    private String paymentMethod;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(name = "paycheck_items")
public class PaycheckItemDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paycheck_id", nullable = false)
    private Long paycheck_id;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "category", nullable = false)
    private String category;

}

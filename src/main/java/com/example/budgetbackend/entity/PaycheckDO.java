package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "paycheck")
public class PaycheckDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "label")
    private String label;

    @Setter
    @Column(name = "value")
    private double value;

    @Setter
    @Column(name = "sub_category")
    private String sub_category;

    public PaycheckDO(Long id) {
        this.id = id;
    }
}

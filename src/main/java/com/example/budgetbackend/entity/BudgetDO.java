package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "budget")
public class BudgetDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(name = "category")
    private String category;

    @Setter
    @Column(name = "amount")
    private double amount;

    public BudgetDO(Long id) {
        this.id = id;
    }
}

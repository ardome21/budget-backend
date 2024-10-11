package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
// FIXME This is more of budgetItem. Need to change db
@Table(name = "budget_items")
public class BudgetItemDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "budget_id")
    private long budgetId;

    @Column(name = "category")
    private String category;

    @Column(name = "amount")
    private double amount;

}

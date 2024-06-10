package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "budget")
public class BudgetDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category")
    private String category;

    @Column(name = "amount")
    private double amount;

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BudgetDO() {}

    public BudgetDO( String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

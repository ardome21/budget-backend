package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;

@Getter
@Entity
@Table(name = "reoccurring_transactions")
public class ReoccurringTransactionDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "frequency_value", nullable = false)
    private String frequency_value;

    @Column(name = "frequency_unit", nullable = false)
    private String frequency_unit;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "amount", nullable = false)
    private double amount;

    public void setFrequencyValue(String frequency_value) {
        this.frequency_value = frequency_value;
    }

    public void setFrequencyUnit(String frequency_unit) {
        this.frequency_unit = frequency_unit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ReoccurringTransactionDO() {}

    public ReoccurringTransactionDO(
            String frequency_value,
            String frequency_unit,
            String description,
            String category,
            double amount
    ){
        this.frequency_value = frequency_value;
        this.frequency_unit = frequency_unit;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }
}

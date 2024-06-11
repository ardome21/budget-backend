package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "reoccurring_transactions")
public class ReoccurringTransactionDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "frequency_value", nullable = false)
    private String frequencyValue;

    @Setter
    @Column(name = "frequency_unit", nullable = false)
    private String frequencyUnit;

    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    @Setter
    @Column(name = "category", nullable = false)
    private String category;

    @Setter
    @Column(name = "amount", nullable = false)
    private double amount;

    public ReoccurringTransactionDO(Long id) {
        this.id = id;
    }

}

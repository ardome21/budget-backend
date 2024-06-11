package com.example.budgetbackend.entity;

import com.example.budgetbackend.model.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "transactions")
public class TransactionDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    @Setter
    @Column(name = "category", nullable = false)
    private String category;

    @Setter
    @Column(name = "amount", nullable = false)
    private double amount;

    public TransactionDO(Long id) {
        this.id = id;
    }

}

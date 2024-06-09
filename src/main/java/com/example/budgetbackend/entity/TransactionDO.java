package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "transactions")
public class TransactionDO {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @Column(name = "category", nullable = false, length = 25)
    private String category;

    @Column(name = "amount", nullable = false)
    private double amount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionDO() {}

    public TransactionDO(Date date, String description, String category, double amount) {
        this.date = date;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }
}

package com.example.budgetbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TransactionItem {
    private String description;
    private String category;
    private double amount;

}

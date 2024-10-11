package com.example.budgetbackend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
public class BudgetItem {
    Long id;
    String category;
    double planned;
    double spent;
}

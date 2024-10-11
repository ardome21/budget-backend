package com.example.budgetbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Budget {
    Long id;
    double takeHome;
    List<BudgetItem> categories;
    double totalPlanned;
    double plannedDif;
    double totalSpent;
    double spendingDif;
}

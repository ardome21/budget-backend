package com.example.budgetbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaycheckItem {
    Long id;
    String label;
    double value;
}

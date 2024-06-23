package com.example.budgetbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Paycheck {
    long id;
    PaycheckItem grossPay;
    List<PaycheckItem> taxes;
    List<PaycheckItem> benefits;
    List<PaycheckItem> retirement;
    double takeHome;
}

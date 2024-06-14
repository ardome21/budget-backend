package com.example.budgetbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    private Long id;
    @Setter
    private LocalDate date;
    @Setter
    private TransactionItem transactionItem;

}

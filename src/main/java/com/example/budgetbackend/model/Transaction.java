package com.example.budgetbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    private Long id;
    private Date date;
    private TransactionItem transactionItem;

}

package com.example.budgetbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Transaction {
    private Long id;
    private Date date;
    private TransactionItem transactionItem;

}

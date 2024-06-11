package com.example.budgetbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class Transaction {

    private final Long id;
    @Setter
    private LocalDate date;
    @Setter
    private TransactionItem transactionItem;

    public Transaction(Long id){
        this.id = id;
    }

}

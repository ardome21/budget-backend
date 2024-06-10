package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.model.Transaction;

public interface TransactionMapper {
    Transaction toModel(TransactionDO entity);
    TransactionDO toEntity(Transaction model);
}

package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.ReoccurringTransactionDO;
import com.example.budgetbackend.model.ReoccurringTransaction;

public interface ReoccurringTransactionMapper {
    ReoccurringTransaction toModel(ReoccurringTransactionDO entity);

    ReoccurringTransactionDO toEntity(ReoccurringTransaction model);
}

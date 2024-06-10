package com.example.budgetbackend.mapper.impl;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.mapper.TransactionMapper;
import com.example.budgetbackend.model.Transaction;
import com.example.budgetbackend.model.TransactionItem;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toModel(TransactionDO entity) {
        if (entity == null) {
            return null;
        }
        Transaction model = new Transaction();
        model.setId(entity.getId());
        model.setDate(entity.getDate());
        model.setTransactionItem(new TransactionItem(entity.getDescription(), entity.getCategory(), entity.getAmount()));
        return model;
    }

    @Override
    public TransactionDO toEntity(Transaction model) {
        if (model == null) {
            return null;
        }
        TransactionDO entity = new TransactionDO();

        entity.setDate(model.getDate());
        entity.setDescription(model.getTransactionItem().getDescription());
        entity.setCategory(model.getTransactionItem().getCategory());
        entity.setAmount(model.getTransactionItem().getAmount());
        return entity;
    }
}

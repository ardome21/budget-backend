package com.example.budgetbackend.mapper.impl;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.mapper.TransactionMapper;
import com.example.budgetbackend.model.Transaction;
import com.example.budgetbackend.model.TransactionItem;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toModel(TransactionDO transactionDO) {
        if (transactionDO == null) {
            return null;
        }
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setDescription(transactionDO.getDescription());
        transactionItem.setCategory(transactionDO.getCategory());
        transactionItem.setAmount(transactionDO.getAmount());

        Transaction transaction = new Transaction();
        transaction.setId(transactionDO.getId());
        transaction.setDate(transactionDO.getDate());
        transaction.setTransactionItem(transactionItem);
        return transaction;
    }

    @Override
    public TransactionDO toEntity(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        TransactionDO transactionDO = new TransactionDO();
        transactionDO.setId(transaction.getId());
        transactionDO.setDate(transaction.getDate());
        if (transaction.getTransactionItem() != null) {
            transactionDO.setDescription(transaction.getTransactionItem().getDescription());
            transactionDO.setCategory(transaction.getTransactionItem().getCategory());
            transactionDO.setAmount(transaction.getTransactionItem().getAmount());
        }
        return transactionDO;
    }
}

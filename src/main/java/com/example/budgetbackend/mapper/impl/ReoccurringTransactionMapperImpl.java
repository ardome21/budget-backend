package com.example.budgetbackend.mapper.impl;

import com.example.budgetbackend.entity.ReoccurringTransactionDO;
import com.example.budgetbackend.mapper.ReoccurringTransactionMapper;
import com.example.budgetbackend.model.Frequency;
import com.example.budgetbackend.model.ReoccurringTransaction;
import com.example.budgetbackend.model.TransactionItem;
import org.springframework.stereotype.Component;

@Component
public class ReoccurringTransactionMapperImpl implements ReoccurringTransactionMapper {
    @Override
    public ReoccurringTransaction toModel(ReoccurringTransactionDO entity) {
        if(entity == null){
            return null;
        }
        ReoccurringTransaction reoccurringTransaction = new ReoccurringTransaction();
        reoccurringTransaction.setId(entity.getId());
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setDescription(entity.getDescription());
        transactionItem.setCategory(entity.getCategory());
        transactionItem.setAmount(entity.getAmount());
        reoccurringTransaction.setTransactionItem(transactionItem);
        Frequency frequency = new Frequency(entity.getFrequencyValue(), entity.getFrequencyUnit());
        reoccurringTransaction.setFrequency(frequency);
        return reoccurringTransaction;
    }

    @Override
    public ReoccurringTransactionDO toEntity(ReoccurringTransaction model){
        if(model == null){
            return null;
        }
        ReoccurringTransactionDO reoccurringTransactionDO = new ReoccurringTransactionDO();
        reoccurringTransactionDO.setId(model.getId());
        TransactionItem transactionItem = model.getTransactionItem();
        if(transactionItem != null){
            reoccurringTransactionDO.setDescription(transactionItem.getDescription());
            reoccurringTransactionDO.setCategory(transactionItem.getCategory());
            reoccurringTransactionDO.setAmount(transactionItem.getAmount());
        }
        Frequency frequency = model.getFrequency();
        if(frequency != null){
            reoccurringTransactionDO.setFrequencyValue(frequency.getValue());
            reoccurringTransactionDO.setFrequencyUnit(frequency.getUnit());
        }
        return reoccurringTransactionDO;
    }
}

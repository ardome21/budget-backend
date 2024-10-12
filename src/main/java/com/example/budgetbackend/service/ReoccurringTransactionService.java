package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.ReoccurringTransactionDO;
import com.example.budgetbackend.mapper.ReoccurringTransactionMapper;
import com.example.budgetbackend.model.Frequency;
import com.example.budgetbackend.model.ReoccurringTransaction;
import com.example.budgetbackend.model.TransactionItem;
import com.example.budgetbackend.repository.ReoccurringTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReoccurringTransactionService {
    private final ReoccurringTransactionRepository reoccurringTransactionRepository;

    private final ReoccurringTransactionMapper reoccurringTransactionMapper;

    @Autowired
    public ReoccurringTransactionService(ReoccurringTransactionRepository reoccurringTransactionRepository,
                                         ReoccurringTransactionMapper reoccurringTransactionMapper){
        this.reoccurringTransactionRepository = reoccurringTransactionRepository;
        this.reoccurringTransactionMapper = reoccurringTransactionMapper;
    }

    public List<ReoccurringTransaction> getAllReoccurringTransactions(){
        return reoccurringTransactionRepository.findAll()
                .stream()
                .map(reoccurringTransactionMapper::toModel)
                .collect(Collectors.toList());
    }

    public Optional<ReoccurringTransaction> getReoccurringTransactionById(Long id){
        return reoccurringTransactionRepository.findById(id)
                .map(reoccurringTransactionMapper::toModel);
    }

    public ReoccurringTransaction createReoccurringTransaction(ReoccurringTransaction reoccurringTransaction){
        throwIfReoccurringTransactionExists(reoccurringTransaction);
        ReoccurringTransactionDO reoccurringTransactionDO = reoccurringTransactionMapper
                .toEntity(reoccurringTransaction);
        ReoccurringTransactionDO savedEntity = reoccurringTransactionRepository.save(reoccurringTransactionDO);
        return reoccurringTransactionMapper.toModel(savedEntity);
    }

    public Optional<ReoccurringTransaction> updateReoccurringTransaction(
            Long id,
            ReoccurringTransaction reoccurringTransaction
    ) {
        return reoccurringTransactionRepository.findById(id)
                .map(reoccurringTransactionDO -> applyUpdates(reoccurringTransactionDO, reoccurringTransaction));
    }

    private ReoccurringTransaction applyUpdates(
            ReoccurringTransactionDO reoccurringTransactionDO,
            ReoccurringTransaction reoccurringTransaction
    ) {
        Frequency frequency = reoccurringTransaction.getFrequency();
        if(frequency != null){
            reoccurringTransactionDO.setFrequencyValue(frequency.getValue());
            reoccurringTransactionDO.setFrequencyUnit(frequency.getUnit());
        }
        TransactionItem transactionItem = reoccurringTransaction.getTransactionItem();
        if(transactionItem != null){
            reoccurringTransactionDO.setDescription(transactionItem.getDescription());
            reoccurringTransactionDO.setCategory(transactionItem.getCategory());
            reoccurringTransactionDO.setAmount(transactionItem.getAmount());
        }
        ReoccurringTransactionDO updatedEntity = reoccurringTransactionRepository.save(reoccurringTransactionDO);
        return reoccurringTransactionMapper.toModel(updatedEntity);
    }

    public boolean deleteReoccurringTransaction(Long id){
        if(reoccurringTransactionRepository.existsById(id)){
            reoccurringTransactionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void throwIfReoccurringTransactionExists(ReoccurringTransaction reoccurringTransaction) {
        TransactionItem transactionItem = reoccurringTransaction.getTransactionItem();
        Optional<ReoccurringTransactionDO> existingTransaction = reoccurringTransactionRepository
                .findByDescriptionAndCategory(
                        transactionItem.getDescription(),
                        transactionItem.getCategory()
                );
        if (existingTransaction.isPresent()){
            Long id = existingTransaction.get().getId();
            throw new IllegalArgumentException(
                    "A transaction with the same description and category already exists with ID: " + id + ". Please use the update method."
            );
        }
    }
}

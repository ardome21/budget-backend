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

//    TODO: Make it impossibile to save a transaction if the id already exist
    public ReoccurringTransaction saveReoccurringTransaction(ReoccurringTransaction reoccurringTransaction){
        ReoccurringTransactionDO reoccurringTransactionDO = reoccurringTransactionMapper
                .toEntity(reoccurringTransaction);
        ReoccurringTransactionDO savedEntity = reoccurringTransactionRepository.save(reoccurringTransactionDO);
        return reoccurringTransactionMapper.toModel(savedEntity);
    }

//    FIXME This should probably be its own function, multiline lambda could be named
    public Optional<ReoccurringTransaction> updateReoccurringTransaction(
            Long id,
            ReoccurringTransaction reoccurringTransaction
    ) {
        return reoccurringTransactionRepository.findById(id).map(reoccurringTransactionDO -> {
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
        });
    }

    public boolean deleteReoccurringTransaction(Long id){
        if(reoccurringTransactionRepository.existsById(id)){
            reoccurringTransactionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

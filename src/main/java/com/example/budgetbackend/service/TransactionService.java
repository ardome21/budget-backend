package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDO> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<TransactionDO> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public TransactionDO saveTransaction(TransactionDO transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<TransactionDO> updateTransaction(Long id, TransactionDO transactionDetails) {
        return transactionRepository.findById(id).map(transaction -> {
            transaction.setDate(transactionDetails.getDate());
            transaction.setDescription(transactionDetails.getDescription());
            transaction.setCategory(transactionDetails.getCategory());
            transaction.setAmount(transactionDetails.getAmount());
            return transactionRepository.save(transaction);
        });
    }

    public boolean deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

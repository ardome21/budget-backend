package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.TransactionEntity;
import com.example.budgetbackend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionEntity> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<TransactionEntity> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public TransactionEntity saveTransaction(TransactionEntity transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<TransactionEntity> updateTransaction(Long id, TransactionEntity transactionDetails) {
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

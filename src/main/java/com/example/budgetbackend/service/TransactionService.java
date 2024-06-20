package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.mapper.TransactionMapper;
import com.example.budgetbackend.model.Transaction;
import com.example.budgetbackend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toModel)
                .collect(Collectors.toList());
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toModel);
    }

    public Transaction saveTransaction(Transaction transaction) {
        TransactionDO transactionDO = transactionMapper.toEntity(transaction);
        TransactionDO savedEntity = transactionRepository.save(transactionDO);
        return transactionMapper.toModel(savedEntity);
    }

    public Optional<Transaction> updateTransaction(Long id, Transaction transactionDetails) {
        return transactionRepository.findById(id).map(transaction -> {
            transaction.setDate(transactionDetails.getDate());
            transaction.setDescription(transactionDetails.getTransactionItem().getDescription());
            transaction.setCategory(transactionDetails.getTransactionItem().getCategory());
            transaction.setAmount(transactionDetails.getTransactionItem().getAmount());
            TransactionDO updatedTransaction = transactionRepository.save(transaction);
            return transactionMapper.toModel(updatedTransaction);
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

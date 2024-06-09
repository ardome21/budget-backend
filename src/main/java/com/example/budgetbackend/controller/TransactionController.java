package com.example.budgetbackend.controller;

import com.example.budgetbackend.entity.TransactionEntity;
import com.example.budgetbackend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<TransactionEntity> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionEntity> getTransactionById(@PathVariable Long id) {
        Optional<TransactionEntity> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TransactionEntity createTransaction(@RequestBody TransactionEntity transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionEntity> updateTransaction(@PathVariable Long id, @RequestBody TransactionEntity transactionDetails) {
        Optional<TransactionEntity> updatedTransaction = transactionService.updateTransaction(id, transactionDetails);
        return updatedTransaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        if (transactionService.deleteTransaction(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


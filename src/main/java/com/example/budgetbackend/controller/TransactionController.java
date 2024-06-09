package com.example.budgetbackend.controller;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionDO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDO> getTransactionById(@PathVariable Long id) {
        Optional<TransactionDO> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TransactionDO createTransaction(@RequestBody TransactionDO transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDO> updateTransaction(@PathVariable Long id, @RequestBody TransactionDO transactionDetails) {
        Optional<TransactionDO> updatedTransaction = transactionService.updateTransaction(id, transactionDetails);
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


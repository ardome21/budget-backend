package com.example.budgetbackend.controller;

import com.example.budgetbackend.model.ReoccurringTransaction;
import com.example.budgetbackend.service.ReoccurringTransactionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reoccurring")
public class ReoccurringTransactionController {

    private final ReoccurringTransactionService reoccurringTransactionService;

    public ReoccurringTransactionController(ReoccurringTransactionService reoccurringTransactionService) {
        this.reoccurringTransactionService = reoccurringTransactionService;
    }

    @GetMapping
    public ResponseEntity<List<ReoccurringTransaction>> getReoccurringTransactions(){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reoccurringTransactionService.getAllReoccurringTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReoccurringTransaction> getReoccurringTransactionById(@PathVariable Long id){
        Optional<ReoccurringTransaction> reoccurringTransaction =
                reoccurringTransactionService.getReoccurringTransactionById(id);
        return reoccurringTransaction.map(value -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReoccurringTransaction> createReoccurringTransaction(@RequestBody ReoccurringTransaction reoccurringTransaction) {
        ReoccurringTransaction savedReoccurringTransaction =
                reoccurringTransactionService.saveReoccurringTransaction(reoccurringTransaction);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedReoccurringTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReoccurringTransaction> updateReoccurringTransaction(@PathVariable Long id, @RequestBody ReoccurringTransaction reoccurringTransaction) {
        Optional<ReoccurringTransaction> updatedTransaction = reoccurringTransactionService.updateReoccurringTransaction(id, reoccurringTransaction);
        return updatedTransaction.map(value -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        if (reoccurringTransactionService.deleteReoccurringTransaction(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



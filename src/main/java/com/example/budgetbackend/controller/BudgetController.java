package com.example.budgetbackend.controller;

import com.example.budgetbackend.model.Budget;
import com.example.budgetbackend.service.BudgetService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService){
        this.budgetService = budgetService;
    }

    @GetMapping()
    public ResponseEntity<List<Budget>> getAllBudgets(){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(budgetService.getAllBudgets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id){
        Optional<Budget> budget = budgetService.getBudgetById(id);
        return budget.map(value -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.BudgetDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<BudgetDO, Long> {
}

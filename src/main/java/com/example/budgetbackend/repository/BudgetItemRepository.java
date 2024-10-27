package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.BudgetItemDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetItemRepository extends JpaRepository<BudgetItemDO,Long> {
    List<BudgetItemDO> findByBudgetId(Long budgetId);
}

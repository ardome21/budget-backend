package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.ReoccurringTransactionDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReoccurringTransactionRepository extends JpaRepository<ReoccurringTransactionDO, Long> {
}

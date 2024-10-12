package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.ReoccurringTransactionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReoccurringTransactionRepository extends JpaRepository<ReoccurringTransactionDO, Long> {
    Optional<ReoccurringTransactionDO> findByDescriptionAndCategory(String description, String category);
}

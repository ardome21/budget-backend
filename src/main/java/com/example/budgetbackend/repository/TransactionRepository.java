package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
}

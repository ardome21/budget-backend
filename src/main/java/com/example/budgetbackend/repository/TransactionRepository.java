package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.TransactionDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionDO,Long> {
}

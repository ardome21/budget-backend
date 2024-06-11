package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.PaycheckDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaycheckRepository extends JpaRepository<PaycheckDO, Long> {
}

package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.PaycheckItemDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaycheckRepository extends JpaRepository<PaycheckItemDO, Long> {
}

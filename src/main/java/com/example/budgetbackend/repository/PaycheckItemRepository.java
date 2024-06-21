package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.PaycheckItemDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaycheckItemRepository extends JpaRepository<PaycheckItemDO, Long> {
    List<PaycheckItemDO> findByPaycheckId(Long paycheckId);
}

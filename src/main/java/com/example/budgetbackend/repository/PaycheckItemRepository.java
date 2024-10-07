package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.model.PaycheckItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaycheckItemRepository extends JpaRepository<PaycheckItemDO, Long> {
    List<PaycheckItemDO> findByPaycheckId(Long paycheckId);

    Optional<PaycheckItemDO> findByPaycheckIdAndCategoryAndLabel(Long paycheckId, String category, String label);
}

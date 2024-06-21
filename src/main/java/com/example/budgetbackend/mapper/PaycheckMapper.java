package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.model.Paycheck;

import java.util.List;

public interface PaycheckMapper {
    Paycheck toModel(List<PaycheckItemDO> entity);
    List<PaycheckItemDO> toEntity(Paycheck model);
}

package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.model.Paycheck;

import java.util.List;



public interface PaycheckMapper {
    Paycheck entityListToModel(List<PaycheckItemDO> entityList);

    Paycheck entityToModel(PaycheckDO entity);

    List<PaycheckItemDO> modelToEntityList(Paycheck model);

    PaycheckDO modelToEntity(Paycheck model);
}

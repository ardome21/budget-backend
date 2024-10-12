package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.model.PaycheckItem;

import java.util.List;



public interface PaycheckMapper {
    Paycheck entityListToModel(List<PaycheckItemDO> entityList);

    List<PaycheckItemDO> modelToEntityList(Paycheck model);

    PaycheckDO modelToEntity(Paycheck model);

    PaycheckItem entityItemToModelItem(PaycheckItemDO model);

    PaycheckItemDO modelItemToEntityItem(Long paycheckId, String category, PaycheckItem entity);
}

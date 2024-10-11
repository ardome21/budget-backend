package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.BudgetDO;
import com.example.budgetbackend.entity.BudgetItemDO;
import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.model.Budget;
import com.example.budgetbackend.model.BudgetItem;
import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.model.PaycheckItem;

import java.util.List;

public interface BudgetMapper {

    List<BudgetItem> entityListToModelList(List<BudgetItemDO> entityList);

    List<BudgetItemDO> modelListToEntityList(Long budgetId,List<BudgetItem> model);

    BudgetDO modelToEntity(Budget model);

    BudgetItem entityItemToModelItem(BudgetItemDO model, double spent);

    BudgetItemDO modelItemToEntityItem(Long budgetId, BudgetItem entity);


}

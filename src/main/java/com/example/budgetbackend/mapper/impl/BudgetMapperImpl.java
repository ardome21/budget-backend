package com.example.budgetbackend.mapper.impl;

import com.example.budgetbackend.entity.BudgetDO;
import com.example.budgetbackend.entity.BudgetItemDO;
import com.example.budgetbackend.mapper.BudgetMapper;
import com.example.budgetbackend.model.Budget;
import com.example.budgetbackend.model.BudgetItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetMapperImpl implements BudgetMapper {

    public BudgetMapperImpl(){}

    @Override
    public List<BudgetItem> entityListToModelList(List<BudgetItemDO> entityList) {
        if(entityList.isEmpty()){
            return null;
        }
        return entityList.stream()
                .map(budgetItemDO -> this.entityItemToModelItem(budgetItemDO, -1))
                .toList();
    }

    @Override
    public List<BudgetItemDO> modelListToEntityList(Long budgetId, List<BudgetItem> modelList) {
        if(modelList.isEmpty()){
            return null;
        }
        return modelList.stream()
                .map((budgetItem -> this.modelItemToEntityItem(budgetId,budgetItem)))
                .toList();
    }

    @Override
    public BudgetDO modelToEntity(Budget model) {
        if(model == null){
            return null;
        }
        BudgetDO budgetDO = new BudgetDO();
        budgetDO.setId(model.getId());
        return budgetDO;
    }

    @Override
    public BudgetItem entityItemToModelItem(BudgetItemDO entity, double spent) {
        if(entity == null) {
            return null;
        }
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setId(entity.getId());
        budgetItem.setCategory(entity.getCategory());
        budgetItem.setPlanned(entity.getAmount());
        if(spent != -1){
            budgetItem.setSpent(spent);
        }
        return budgetItem;
    }

    @Override
    public BudgetItemDO modelItemToEntityItem(Long budgetId, BudgetItem model) {
        if(model == null){
            return null;
        }
        BudgetItemDO budgetItemDO = new BudgetItemDO();
        budgetItemDO.setId(model.getId());
        budgetItemDO.setBudgetId(budgetId);
        budgetItemDO.setCategory(model.getCategory());
        budgetItemDO.setAmount(model.getPlanned());
        return budgetItemDO;
    }
}

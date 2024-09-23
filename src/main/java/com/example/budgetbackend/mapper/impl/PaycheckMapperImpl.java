package com.example.budgetbackend.mapper.impl;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.mapper.PaycheckMapper;
import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.model.PaycheckItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaycheckMapperImpl implements PaycheckMapper {

    public PaycheckMapperImpl() {}

    @Override
    public Paycheck entityListToModel(List<PaycheckItemDO> entity){
        if(entity.isEmpty()){
            return null;
        }
        PaycheckItem grossPay = null;
        List<PaycheckItem> taxes = new ArrayList<>();
        List<PaycheckItem> benefits = new ArrayList<>();
        List<PaycheckItem> retirement = new ArrayList<>();

        Long paycheckId = entity.get(0).getPaycheckId();

        for(PaycheckItemDO itemDO: entity){
            PaycheckItem item = entityItemToModelItem(itemDO);
            switch (itemDO.getCategory()) {
                case "gross_pay" -> grossPay = item;
                case "taxes" -> taxes.add(item);
                case "benefits" -> benefits.add(item);
                case "retirement" -> retirement.add(item);
                default -> throw new IllegalArgumentException("Unknown category: " + itemDO.getCategory());
            }
        }

        Paycheck paycheck = new Paycheck();
        paycheck.setId(paycheckId);
        paycheck.setGrossPay(grossPay);
        paycheck.setTaxes(taxes);
        paycheck.setBenefits(benefits);
        paycheck.setRetirement(retirement);
        return paycheck;
    }

    private PaycheckItem entityItemToModelItem(PaycheckItemDO entity) {
        PaycheckItem paycheckItem = new PaycheckItem();
        paycheckItem.setId(entity.getId());
        paycheckItem.setLabel(entity.getLabel());
        paycheckItem.setValue(entity.getValue());
        return paycheckItem;
    }

    private List<PaycheckItemDO> modelItemListToEntityItemList(Long paycheckID, String category, List<PaycheckItem> model){
        return model.stream()
                .map(item -> modelItemToEntityItem(paycheckID, category, item))
                .collect(Collectors.toList());
    }

    private PaycheckItemDO modelItemToEntityItem(Long paycheckID, String category, PaycheckItem model) {
        PaycheckItemDO paycheckItemDO = new PaycheckItemDO();
        paycheckItemDO.setId(model.getId());
        paycheckItemDO.setPaycheckId(paycheckID);
        paycheckItemDO.setLabel(model.getLabel());
        paycheckItemDO.setValue(model.getValue());
        paycheckItemDO.setCategory(category);
        return paycheckItemDO;
    }

    @Override
    public Paycheck entityToModel(PaycheckDO entity) {
        if(entity == null){
            return null;
        }
        Paycheck paycheck = new Paycheck();
        paycheck.setId(entity.getId());
        return paycheck;
    }

    @Override
    public List<PaycheckItemDO> modelToEntityList(Paycheck model){
        if(model == null) {
            return null;
        }
        Long id = model.getId();

        List<PaycheckItemDO> completeListOfDOs = new ArrayList<>();

        PaycheckItemDO grossPayDO = modelItemToEntityItem(id, "gross_pay" ,model.getGrossPay());
        completeListOfDOs.add(grossPayDO);

        List<PaycheckItemDO> taxesDOs = modelItemListToEntityItemList(id, "taxes", model.getTaxes());
        completeListOfDOs.addAll(taxesDOs);

        List<PaycheckItemDO> benefitsDOs = modelItemListToEntityItemList(id, "benefits", model.getBenefits());
        completeListOfDOs.addAll(benefitsDOs);

        List<PaycheckItemDO> retirementDOs = modelItemListToEntityItemList(id, "retirement", model.getRetirement());
        completeListOfDOs.addAll(retirementDOs);

        return completeListOfDOs;
    }



    public PaycheckDO modelToEntity(Paycheck model) {
        if(model == null){
            return null;
        }
        PaycheckDO paycheckDO = new PaycheckDO();
        paycheckDO.setId(model.getId());
        return paycheckDO;
    }
}

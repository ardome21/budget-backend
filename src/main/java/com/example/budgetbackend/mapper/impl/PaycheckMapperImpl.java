package com.example.budgetbackend.mapper.impl;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.mapper.PaycheckMapper;
import com.example.budgetbackend.model.Paycheck;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaycheckMapperImpl implements PaycheckMapper {

    @Override
    public Paycheck entityListToModel(List<PaycheckItemDO> entity) {

//        Switch Statement
//        Map Gross Pay

//        Map Taxes

//        Map Benefits

//        Map Retirement

//        find smart way to generate takeHome, probably call a service method
        return null;
    }

    @Override
    public Paycheck entityToModel(PaycheckDO entity) {
        return null;
    }

    @Override
    public List<PaycheckItemDO> modelToEntityList(Paycheck model){

//        Map Gross Pay

//        Map Taxes

//        Map Benefits

//        Map Retirement
        return null;
    }

    @Override
    public PaycheckDO modelToEntity(Paycheck model) {
        return null;
    }
}

package com.example.budgetbackend.mapper.impl;

import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.mapper.PaycheckMapper;
import com.example.budgetbackend.model.Paycheck;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaycheckMapperImpl implements PaycheckMapper {

    @Override
    public Paycheck toModel(List<PaycheckItemDO> entity) {

//        Switch Statement
//        Map Gross Pay

//        Map Taxes

//        Map Benefits

//        Map Retirement

//        find smart way to generate takeHome, probably call a service method
        return null;
    }

    @Override
    public List<PaycheckItemDO> toEntity(Paycheck model){

//        Map Gross Pay

//        Map Taxes

//        Map Benefits

//        Map Retirement
        return null;
    }
}

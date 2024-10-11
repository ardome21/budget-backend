package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.mapper.impl.PaycheckMapperImpl;
import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.testUtils.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaycheckMapperTest {

    @InjectMocks
    private PaycheckMapperImpl paycheckMapper;

    private Paycheck mockPaycheck;

    private List<PaycheckItemDO> mockPaycheckItemDOs;

    @BeforeEach
    void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        List<Paycheck> paychecks = DataLoader.loadMockData("mocks/paychecks.json", Paycheck.class);
        List<PaycheckItemDO> paycheckItemDOs = DataLoader.loadMockData("mocks/paycheckItemDOs.json", PaycheckItemDO.class);
        mockPaycheck = paychecks.get(0);
        mockPaycheckItemDOs = paycheckItemDOs.subList(0,3);
    }

    @Test
    void testEntityListToModel() {
        Paycheck result = paycheckMapper.entityListToModel(mockPaycheckItemDOs);
        assertEquals(result.getId(),mockPaycheck.getId());
        assertEquals(result.getId(),mockPaycheck.getId());
        assertEquals(result.getGrossPay().getId(), mockPaycheck.getGrossPay().getId());
        assertEquals(result.getGrossPay().getValue(), mockPaycheck.getGrossPay().getValue());
        assertEquals(result.getGrossPay().getLabel(), mockPaycheck.getGrossPay().getLabel());
        assertEquals(result.getTaxes().get(0).getId(), mockPaycheck.getTaxes().get(0).getId());
        assertEquals(result.getTaxes().get(0).getValue(), mockPaycheck.getTaxes().get(0).getValue());
        assertEquals(result.getTaxes().get(0).getLabel(), mockPaycheck.getTaxes().get(0).getLabel());
        assertEquals(result.getBenefits().get(0).getId(), mockPaycheck.getBenefits().get(0).getId());
        assertEquals(result.getBenefits().get(0).getValue(), mockPaycheck.getBenefits().get(0).getValue());
        assertEquals(result.getBenefits().get(0).getLabel(), mockPaycheck.getBenefits().get(0).getLabel());
    }

    @Test
    void testModelItemToEntityItemList() {
        List<PaycheckItemDO> result = paycheckMapper.modelToEntityList(mockPaycheck);
        assertEquals(result.get(0).getPaycheckId(),mockPaycheck.getId());
        assertEquals(result.get(0).getId(), mockPaycheck.getGrossPay().getId());
        assertEquals(result.get(0).getLabel(), mockPaycheck.getGrossPay().getLabel());
        assertEquals(result.get(0).getValue(), mockPaycheck.getGrossPay().getValue());
    }

    @Test
    void testModelToEntity() {
        PaycheckDO result = paycheckMapper.modelToEntity(mockPaycheck);
        assertEquals(result.getId(),mockPaycheck.getId());
    }
}

package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.BudgetDO;
import com.example.budgetbackend.entity.BudgetItemDO;
import com.example.budgetbackend.mapper.impl.BudgetMapperImpl;
import com.example.budgetbackend.model.Budget;
import com.example.budgetbackend.model.BudgetItem;
import com.example.budgetbackend.testUtils.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetMapperTest {

    @InjectMocks
    private BudgetMapperImpl budgetMapper;

    private Budget mockBudget;

    private List<BudgetDO> budgetDOs;

    private List<BudgetItemDO> mockBudgetItemDOs;

    @BeforeEach
    void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        List<Budget> budgets = DataLoader.loadMockData("mocks/budgets.json", Budget.class);
        budgetDOs = DataLoader.loadMockData("mocks/budgetDOs.json", BudgetDO.class);
        List<BudgetItemDO> budgetItemsDOs = DataLoader.loadMockData("mocks/budgetItemDOs.json", BudgetItemDO.class);
        mockBudget = budgets.get(0);
        mockBudgetItemDOs = budgetItemsDOs.subList(0, 8);
    }

    @Test
    void testEntityListToModelList() {
        List<BudgetItem> budgetItems = mockBudget.getCategories();
        List<BudgetItem> result = budgetMapper.entityListToModelList(mockBudgetItemDOs);
        assertEquals(result.get(0).getId(), budgetItems.get(0).getId());
        assertEquals(result.get(1).getCategory(), budgetItems.get(1).getCategory());
        assertEquals(result.get(2).getPlanned(), budgetItems.get(2).getPlanned());
        assertEquals(result.get(3).getId(), budgetItems.get(3).getId());
        assertEquals(result.get(4).getCategory(), budgetItems.get(4).getCategory());
        assertEquals(result.get(5).getPlanned(), budgetItems.get(5).getPlanned());
    }

    @Test
    void testModelListToEntityList() {
        List<BudgetItemDO> expectedList = mockBudgetItemDOs;
        List<BudgetItemDO> result = budgetMapper.modelListToEntityList(1L, mockBudget.getCategories());
        assertEquals(result.get(0).getBudgetId(), expectedList.get(0).getBudgetId());
        assertEquals(result.get(1).getAmount(), expectedList.get(1).getAmount());
        assertEquals(result.get(2).getCategory(), expectedList.get(2).getCategory());
        assertEquals(result.get(3).getId(), expectedList.get(3).getId());
    }

    @Test
    void testModelToEntity() {
        BudgetDO expected = budgetDOs.get(0);
        BudgetDO result = budgetMapper.modelToEntity(mockBudget);
        assertEquals(result.getId(), expected.getId());
    }
}


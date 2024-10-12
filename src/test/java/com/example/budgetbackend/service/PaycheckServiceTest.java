package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.mapper.PaycheckMapper;
import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.model.PaycheckItem;
import com.example.budgetbackend.repository.PaycheckItemRepository;
import com.example.budgetbackend.repository.PaycheckRepository;
import com.example.budgetbackend.testUtils.DataLoader;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PaycheckServiceTest {

    @Mock
    private PaycheckRepository paycheckRepository;

    @Mock
    PaycheckItemRepository paycheckItemRepository;

    @Mock
    private PaycheckMapper paycheckMapper;

    @InjectMocks PaycheckService paycheckService;

    private List<Paycheck> mockPaychecks;

    private List<PaycheckDO> mockPaycheckDOs;

    private List<PaycheckItemDO> mockPaycheckItemDOs;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        mockPaychecks = DataLoader.loadMockData("mocks/paychecks.json",Paycheck.class);
        mockPaycheckDOs = DataLoader.loadMockData("mocks/paycheckDOs.json", PaycheckDO.class);
        mockPaycheckItemDOs = DataLoader.loadMockData("mocks/paycheckItemDOs.json", PaycheckItemDO.class);
    }

    @Test
    void testGetAllPaychecks(){
        List<PaycheckItemDO> mockList1 = mockPaycheckItemDOs.subList(0,4);
        List<PaycheckItemDO> mockList2 = mockPaycheckItemDOs.subList(4,8);
        when(paycheckRepository.findAll()).thenReturn(mockPaycheckDOs);
        when(paycheckItemRepository.findByPaycheckId(1L)).thenReturn(mockList1);
        when(paycheckItemRepository.findByPaycheckId(2L)).thenReturn(mockList2);
        when(paycheckMapper.entityListToModel(mockList1)).thenReturn(mockPaychecks.get(0));
        when(paycheckMapper.entityListToModel(mockList2)).thenReturn(mockPaychecks.get(1));
        List<Paycheck> result = paycheckService.getAllPaychecks();
        assertEquals(mockPaychecks, result);
        verify(paycheckRepository, times(1)).findAll();
        verify(paycheckMapper, times(1)).entityListToModel(mockList1);
        verify(paycheckMapper, times(1)).entityListToModel(mockList2);
    }

    @Test
    void testGetPaycheckById() {
        List<PaycheckItemDO> mockList1 = mockPaycheckItemDOs.subList(0,4);
        when(paycheckItemRepository.findByPaycheckId(1L)).thenReturn(mockList1);
        when(paycheckMapper.entityListToModel(mockList1)).thenReturn(mockPaychecks.get(0));
        Optional<Paycheck> result = paycheckService.getPaycheckById(1L);
        assertEquals(Optional.of(mockPaychecks.get(0)), result);

    }

    @Test
    void testGetPaycheckItemById() {
        when(paycheckItemRepository.findById(1L)).thenReturn(Optional.of(mockPaycheckItemDOs.get(0)));
        when(paycheckMapper.entityItemToModelItem(mockPaycheckItemDOs.get(0))).thenReturn(mockPaychecks.get(0).getGrossPay());
        Optional<PaycheckItem> result = paycheckService.getPaycheckItemById(1L);
        assertEquals(Optional.of(mockPaychecks.get(0).getGrossPay()), result);
    }

    @Test
    void testCreatePaycheck() {
        PaycheckDO partialPaycheckDO = new PaycheckDO();
        partialPaycheckDO.setId(1L);
        List<PaycheckItemDO> mockList1 = mockPaycheckItemDOs.subList(0,4);
        when(paycheckMapper.modelToEntity(any())).thenReturn(mockPaycheckDOs.get(0));
        when(paycheckRepository.save(any())).thenReturn(partialPaycheckDO);
        when(paycheckMapper.modelToEntityList(any())).thenReturn(mockList1);
        when(paycheckItemRepository.save(mockPaycheckItemDOs.get(0))).thenReturn(mockPaycheckItemDOs.get(0));
        when(paycheckItemRepository.save(mockPaycheckItemDOs.get(1))).thenReturn(mockPaycheckItemDOs.get(1));
        when(paycheckItemRepository.save(mockPaycheckItemDOs.get(2))).thenReturn(mockPaycheckItemDOs.get(2));
        when(paycheckItemRepository.save(mockPaycheckItemDOs.get(3))).thenReturn(mockPaycheckItemDOs.get(3));
        when(paycheckMapper.entityListToModel(mockList1)).thenReturn(mockPaychecks.get(0));

        Paycheck result = paycheckService.createPaycheck(mockPaychecks.get(0));

        assertEquals(mockPaychecks.get(0), result);
    }

    @Test
    void testCreatePaycheckItem() {
        PaycheckItem expectedTaxes = mockPaychecks.get(0).getTaxes().get(0);
        when(paycheckMapper.modelItemToEntityItem(any(), any(), any())).thenReturn(mockPaycheckItemDOs.get(1));
        when(paycheckItemRepository.save(mockPaycheckItemDOs.get(1))).thenReturn(mockPaycheckItemDOs.get(1));
        when(paycheckMapper.entityItemToModelItem(mockPaycheckItemDOs.get(1))).thenReturn(expectedTaxes);

        PaycheckItem result = paycheckService.createPaycheckItem(
                1L,"taxes", expectedTaxes);
        assertEquals(result, expectedTaxes);
    }

    @Test
    void testCreatePaycheckItem_grossPay() {
        assertThrows(IllegalArgumentException.class, () -> {
            paycheckService.createPaycheckItem(1L, "gross_pay", new PaycheckItem());
        });
    }

    @Test
    void testCreatePaycheckItem_AlreadyExists() {
        PaycheckItemDO existingItemDO = mockPaycheckItemDOs.get(1);
        PaycheckItem existingItem = mockPaychecks.get(0).getTaxes().get(0);
        when(paycheckItemRepository.findByPaycheckIdAndCategoryAndLabel(any(),any(),any())).thenReturn(Optional.ofNullable(existingItemDO));
        assertThrows(IllegalArgumentException.class, () -> {
            paycheckService.createPaycheckItem(2L,"taxes", existingItem);
        });
    }

    @Test
    void testUpdatePaycheck_DNE() {
        when(paycheckRepository.existsById(3L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> {
            paycheckService.updatePaycheck(3L, new Paycheck());
        });
    }

    @Test
    void testUpdatePaycheck() {
        List<PaycheckItemDO> mockList1 = mockPaycheckItemDOs.subList(0,4);
        when(paycheckRepository.existsById(1L)).thenReturn(true);
        when(paycheckMapper.modelToEntityList(any())).thenReturn(mockList1);
        when(paycheckItemRepository.save(mockList1.get(0))).thenReturn(mockList1.get(0));
        when(paycheckItemRepository.save(mockList1.get(1))).thenReturn(mockList1.get(1));
        when(paycheckItemRepository.save(mockList1.get(2))).thenReturn(mockList1.get(2));
        when(paycheckItemRepository.save(mockList1.get(3))).thenReturn(mockList1.get(3));
        when(paycheckMapper.entityListToModel(mockList1)).thenReturn(mockPaychecks.get(0));
        Optional<Paycheck> result = paycheckService.updatePaycheck(1L, mockPaychecks.get(0));
        assertEquals(result, Optional.of(mockPaychecks.get(0)));
    }

    @Test
    void testUpdatePaycheckItem_PaycheckDNE() {
        when(paycheckRepository.existsById(3L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class,  () -> {
            paycheckService.updatePaycheckItem(1L,3L, "category" ,new PaycheckItem());
        });
    }

    @Test
    void testUpdatePaycheckItem_PaycheckItemDNE() {
        when(paycheckRepository.existsById(1L)).thenReturn(true);
        when(paycheckItemRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            paycheckService.updatePaycheckItem(9L,1L, "category", new PaycheckItem());
        });
    }

    @Test
    void testUpdatePaycheckItem_paycheckIDDoNotMatch() {
        PaycheckItemDO paycheckItemDO = mockPaycheckItemDOs.get(5);
        when(paycheckRepository.existsById(1L)).thenReturn(true);
        when(paycheckItemRepository.findById(5L)).thenReturn(Optional.of(paycheckItemDO));
        assertThrows(IllegalArgumentException.class, () -> {
            paycheckService.updatePaycheckItem(5L, 1L, "taxes", new PaycheckItem());
        });
    }

    @Test
    void testUpdatePaycheckItem() {
        PaycheckItemDO paycheckItemDO = mockPaycheckItemDOs.get(2);
        when(paycheckRepository.existsById(1L)).thenReturn(true);
        when(paycheckItemRepository.findById(2L)).thenReturn(Optional.of(paycheckItemDO));
        when(paycheckMapper.modelItemToEntityItem(eq(1L),eq("taxes"), any())).thenReturn(paycheckItemDO);
        when(paycheckItemRepository.save(paycheckItemDO)).thenReturn(paycheckItemDO);
        when(paycheckMapper.entityItemToModelItem(paycheckItemDO)).thenReturn(mockPaychecks.get(0).getTaxes().get(0));
        Optional<PaycheckItem> result = paycheckService.updatePaycheckItem(2L, 1L, "taxes", new PaycheckItem());
        assertEquals(result, Optional.of(mockPaychecks.get(0).getTaxes().get(0)));
    }

    // Delete Paycheck
    @Test
    void testDeletePaycheck() {
        when(paycheckRepository.existsById(any())).thenReturn(true);
        Boolean result = paycheckService.deletePaycheck(1L);
        assertEquals(result, true);
        verify(paycheckRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePaycheck_DNE() {
        when(paycheckRepository.existsById(any())).thenReturn(false);
        Boolean result = paycheckService.deletePaycheck(1L);
        assertEquals(result, false);
        verify(paycheckRepository, times(0)).deleteById(1L);
    }

    // Delete Paycheck Item

    @Test
    void testDeletePaycheckItem() {
        Optional<PaycheckItemDO> paycheckItemDO = Optional.of(mockPaycheckItemDOs.get(1));
        when(paycheckItemRepository.findById(any())).thenReturn(paycheckItemDO);
        Boolean result = paycheckService.deletePaycheckItem(2L);
        assertEquals(result,true);
        verify(paycheckItemRepository, times(1)).deleteById(2L);
    }

    @Test
    void testDeletePaycheckItem_DNE() {
        when(paycheckItemRepository.findById(any())).thenReturn(Optional.empty());
        Boolean result = paycheckService.deletePaycheckItem(9L);
        assertEquals(result, false);
        verify(paycheckRepository, times(0)).deleteById(9L);
    }

    // Delete Paycheck Item budget
    @Test
    void testDeletePaycheckItem_GrossPay() {
        Optional<PaycheckItemDO> paycheckItemDO = Optional.of(mockPaycheckItemDOs.get(0));
        when(paycheckItemRepository.findById(any())).thenReturn(paycheckItemDO);
        assertThrows(UnsupportedOperationException.class, () -> {
            paycheckService.deletePaycheckItem(1L);
        });
    }


}

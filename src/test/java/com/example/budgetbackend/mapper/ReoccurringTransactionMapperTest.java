package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.ReoccurringTransactionDO;
import com.example.budgetbackend.mapper.impl.ReoccurringTransactionMapperImpl;
import com.example.budgetbackend.mockGenerator.ReoccurringTransactionMockGenerator;
import com.example.budgetbackend.model.ReoccurringTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReoccurringTransactionMapperTest {
    @InjectMocks
    private ReoccurringTransactionMapperImpl reoccurringTransactionMapper;

    private ReoccurringTransaction mockReoccurringTransaction;

    private ReoccurringTransactionDO mockReoccurringTransactionDO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockReoccurringTransaction = ReoccurringTransactionMockGenerator.generateReoccuringTransactionList().get(0);
        mockReoccurringTransactionDO = ReoccurringTransactionMockGenerator.generateReoccuringTransactionDOList().get(0);
    }

    @Test
    void testToModel_shouldReturnReoccurringTransaction() {
        ReoccurringTransaction result = reoccurringTransactionMapper.toModel(mockReoccurringTransactionDO);
        assertEquals(mockReoccurringTransaction.getId(),result.getId());
        assertEquals(mockReoccurringTransaction.getFrequency().getUnit(),result.getFrequency().getUnit());
        assertEquals(mockReoccurringTransaction.getFrequency().getValue(),result.getFrequency().getValue());
        assertEquals(mockReoccurringTransaction.getTransactionItem().getAmount(),result.getTransactionItem().getAmount());
        assertEquals(mockReoccurringTransaction.getTransactionItem().getDescription(),result.getTransactionItem().getDescription());
        assertEquals(mockReoccurringTransaction.getTransactionItem().getCategory(),result.getTransactionItem().getCategory());

    }

    @Test
    void testToModel_shouldReturnNull() {
        ReoccurringTransaction result = reoccurringTransactionMapper.toModel(null);
        assertNull(result);
    }

    @Test
    void testToEntity_shouldReturnTransaction() {
        ReoccurringTransactionDO result = reoccurringTransactionMapper.toEntity(mockReoccurringTransaction);
        assertEquals(mockReoccurringTransactionDO.getId(),result.getId());
        assertEquals(mockReoccurringTransactionDO.getAmount(),result.getAmount());
        assertEquals(mockReoccurringTransactionDO.getDescription(),result.getDescription());
        assertEquals(mockReoccurringTransactionDO.getCategory(),result.getCategory());
        assertEquals(mockReoccurringTransactionDO.getFrequencyValue(),result.getFrequencyValue());
        assertEquals(mockReoccurringTransactionDO.getFrequencyUnit(),result.getFrequencyUnit());
    }

    @Test
    void testToEntity_shouldReturnNull() {
        ReoccurringTransactionDO result = reoccurringTransactionMapper.toEntity(null);
        assertNull(result);
    }
}

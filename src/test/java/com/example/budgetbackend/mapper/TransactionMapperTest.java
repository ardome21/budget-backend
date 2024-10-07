package com.example.budgetbackend.mapper;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.mapper.impl.TransactionMapperImpl;
import com.example.budgetbackend.model.Transaction;
import com.example.budgetbackend.testUtils.DataLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransactionMapperTest {

    @InjectMocks
    private TransactionMapperImpl transactionMapper;

    private Transaction mockTransaction;

    private TransactionDO mockTransactionDO;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        List<Transaction> mockTransactions = DataLoader.loadMockData("mocks/transactions.json", Transaction.class);
        List<TransactionDO> mockTransactionDOs = DataLoader.loadMockData("mocks/transactionDOs.json", TransactionDO.class);
        mockTransaction = mockTransactions.get(0);
        mockTransactionDO = mockTransactionDOs.get(0);
    }

    @Test
    void testToModel_shouldReturnTransaction() {
        Transaction result = transactionMapper.toModel(mockTransactionDO);
        assertEquals(mockTransaction.getId(),result.getId());
        assertEquals(mockTransaction.getDate(),result.getDate());
        assertEquals(mockTransaction.getTransactionItem().getAmount(),result.getTransactionItem().getAmount());
        assertEquals(mockTransaction.getTransactionItem().getDescription(),result.getTransactionItem().getDescription());
        assertEquals(mockTransaction.getTransactionItem().getCategory(),result.getTransactionItem().getCategory());

    }

    @Test
    void testToModel_shouldReturnNull() {
        Transaction result = transactionMapper.toModel(null);
        assertNull(result);
    }

    @Test
    void testToEntity_shouldReturnTransaction() {
        TransactionDO result = transactionMapper.toEntity(mockTransaction);
        assertEquals(mockTransactionDO.getId(),result.getId());
        assertEquals(mockTransactionDO.getAmount(),result.getAmount());
        assertEquals(mockTransactionDO.getDescription(),result.getDescription());
        assertEquals(mockTransactionDO.getCategory(),result.getCategory());
        assertEquals(mockTransactionDO.getDate(),result.getDate());
    }

    @Test
    void testToEntity_shouldReturnNull() {
        TransactionDO result = transactionMapper.toEntity(null);
        assertNull(result);
    }
}

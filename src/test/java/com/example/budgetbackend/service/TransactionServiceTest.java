package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.mapper.TransactionMapper;
import com.example.budgetbackend.model.Transaction;
import com.example.budgetbackend.repository.TransactionRepository;
import com.example.budgetbackend.testUtils.DataLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    private List<Transaction> mockTransactions;

    private List<TransactionDO> mockTransactionDOs;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        mockTransactions = DataLoader.loadMockData("mocks/transactions.json", Transaction.class);
        mockTransactionDOs = DataLoader.loadMockData("mocks/transactionDOs.json", TransactionDO.class);
    }

    @Test
    void testGetAllTransactions(){
        when(transactionRepository.findAll()).thenReturn(mockTransactionDOs);
        when(transactionMapper.toModel(mockTransactionDOs.get(0))).thenReturn(mockTransactions.get(0));
        when(transactionMapper.toModel(mockTransactionDOs.get(1))).thenReturn(mockTransactions.get(1));
        when(transactionMapper.toModel(mockTransactionDOs.get(2))).thenReturn(mockTransactions.get(2));
        List<Transaction> result = transactionService.getAllTransactions();
        assertEquals(mockTransactions, result);
        verify(transactionRepository, times(1)).findAll();
        verify(transactionMapper, times(mockTransactions.size())).toModel(any(TransactionDO.class));
    }

    @Test
    void testTransactionById(){
        when(transactionRepository.findById(any())).thenReturn(Optional.of(mockTransactionDOs.get(0)));
        when(transactionMapper.toModel(any())).thenReturn(mockTransactions.get(0));
        Long id = mockTransactions.get(0).getId();
        Optional<Transaction> result = transactionService.getTransactionById(id);
        assertEquals(Optional.of(mockTransactions.get(0)), result);
    }

    @Test
    void testSaveTransaction(){
        when(transactionMapper.toEntity(any())).thenReturn(mockTransactionDOs.get(0));
        when(transactionRepository.save(any())).thenReturn(mockTransactionDOs.get(0));
        when(transactionMapper.toModel(any())).thenReturn(mockTransactions.get(0));
        Transaction result = transactionService.saveTransaction(mockTransactions.get(0));
        assertEquals(mockTransactions.get(0), result);
    }

    @Test
    void testUpdateTransaction() {
        when(transactionRepository.findById(any())).thenReturn(Optional.of(mockTransactionDOs.get(0)));
        when(transactionRepository.save(mockTransactionDOs.get(0))).thenReturn(mockTransactionDOs.get(0));
        when(transactionMapper.toModel(mockTransactionDOs.get(0))).thenReturn(mockTransactions.get(0));
        Transaction transaction = mockTransactions.get(0);
        Long id = transaction.getId();
        Optional<Transaction> result = transactionService.updateTransaction(id, transaction);
        assertEquals(Optional.of(mockTransactions.get(0)), result);
    }

    @Test
    void testDeletedTransactionExists() {
        Long id = mockTransactions.get(0).getId();
        when(transactionRepository.existsById(id)).thenReturn(true);
        boolean result = transactionService.deleteTransaction(id);
        assertTrue(result);
        verify(transactionRepository,times(1)).deleteById(id);
    }

    @Test
    void testDeleteTransactionDNE() {
        Long id = 3L;
        when(transactionRepository.existsById(id)).thenReturn(false);
        boolean result = transactionService.deleteTransaction(id);
        assertFalse(result);
    }
}

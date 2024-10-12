package com.example.budgetbackend.service;


import com.example.budgetbackend.entity.ReoccurringTransactionDO;
import com.example.budgetbackend.mapper.ReoccurringTransactionMapper;
import com.example.budgetbackend.model.ReoccurringTransaction;
import com.example.budgetbackend.repository.ReoccurringTransactionRepository;
import com.example.budgetbackend.testUtils.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReoccurringTransactionServiceTest {

    @Mock
    private ReoccurringTransactionRepository reoccurringTransactionRepository;

    @Mock
    private ReoccurringTransactionMapper reoccurringTransactionMapper;

    @InjectMocks
    private ReoccurringTransactionService reoccurringTransactionService;

    private List<ReoccurringTransaction> mockReoccurringTransactions;

    private List<ReoccurringTransactionDO> mockReoccurringTransactionDOs;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        mockReoccurringTransactions = DataLoader.loadMockData("mocks/reoccurringTransactions.json", ReoccurringTransaction.class);
        mockReoccurringTransactionDOs = DataLoader.loadMockData("mocks/reoccurringTransactionDOs.json", ReoccurringTransactionDO.class);
    }

    @Test
    void testGetAllReoccurringTransactions(){
        when(reoccurringTransactionRepository.findAll()).thenReturn(mockReoccurringTransactionDOs);
        when(reoccurringTransactionMapper.toModel(mockReoccurringTransactionDOs.get(0))).thenReturn(mockReoccurringTransactions.get(0));
        when(reoccurringTransactionMapper.toModel(mockReoccurringTransactionDOs.get(1))).thenReturn(mockReoccurringTransactions.get(1));
        when(reoccurringTransactionMapper.toModel(mockReoccurringTransactionDOs.get(2))).thenReturn(mockReoccurringTransactions.get(2));
        when(reoccurringTransactionMapper.toModel(mockReoccurringTransactionDOs.get(3))).thenReturn(mockReoccurringTransactions.get(3));
        List<ReoccurringTransaction> result = reoccurringTransactionService.getAllReoccurringTransactions();
        assertEquals(mockReoccurringTransactions, result);
        verify(reoccurringTransactionRepository, times(1)).findAll();
        verify(reoccurringTransactionMapper, times(mockReoccurringTransactions.size())).toModel(any(ReoccurringTransactionDO.class));
    }

    @Test
    void testReoccurringTransactionById(){
        when(reoccurringTransactionRepository.findById(any())).thenReturn(Optional.of(mockReoccurringTransactionDOs.get(0)));
        when(reoccurringTransactionMapper.toModel(any())).thenReturn(mockReoccurringTransactions.get(0));
        Long id = mockReoccurringTransactions.get(0).getId();
        Optional<ReoccurringTransaction> result = reoccurringTransactionService.getReoccurringTransactionById(id);
        assertEquals(Optional.of(mockReoccurringTransactions.get(0)), result);
    }

    @Test
    void testCreateReoccurringTransaction(){
        when(reoccurringTransactionMapper.toEntity(any())).thenReturn(mockReoccurringTransactionDOs.get(0));
        when(reoccurringTransactionRepository.save(any())).thenReturn(mockReoccurringTransactionDOs.get(0));
        when(reoccurringTransactionMapper.toModel(any())).thenReturn(mockReoccurringTransactions.get(0));
        ReoccurringTransaction result = reoccurringTransactionService.createReoccurringTransaction(mockReoccurringTransactions.get(0));
        assertEquals(mockReoccurringTransactions.get(0), result);
    }

    @Test
    void testCreateReoccurringTransactionThrowError(){
        when(reoccurringTransactionRepository.findByDescriptionAndCategory(
                mockReoccurringTransactions.get(0).getTransactionItem().getDescription(),
                mockReoccurringTransactions.get(0).getTransactionItem().getCategory()))
                .thenReturn(Optional.of(mockReoccurringTransactionDOs.get(0)));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                reoccurringTransactionService.createReoccurringTransaction(mockReoccurringTransactions.get(0)));
        assertEquals("A transaction with the same description and category already exists with ID: "
                        + mockReoccurringTransactionDOs.get(0).getId() + ". Please use the update method.",
                exception.getMessage());
    }

    @Test
    void testUpdateReoccurringTransaction() {
        when(reoccurringTransactionRepository.findById(any())).thenReturn(Optional.of(mockReoccurringTransactionDOs.get(0)));
        when(reoccurringTransactionRepository.save(mockReoccurringTransactionDOs.get(0))).thenReturn(mockReoccurringTransactionDOs.get(0));
        when(reoccurringTransactionMapper.toModel(mockReoccurringTransactionDOs.get(0))).thenReturn(mockReoccurringTransactions.get(0));
        ReoccurringTransaction transaction = mockReoccurringTransactions.get(0);
        Long id = transaction.getId();
        Optional<ReoccurringTransaction> result = reoccurringTransactionService.updateReoccurringTransaction(id, transaction);
        assertEquals(Optional.of(mockReoccurringTransactions.get(0)), result);
    }

    @Test
    void testDeletedReoccurringTransactionExists() {
        Long id = mockReoccurringTransactions.get(0).getId();
        when(reoccurringTransactionRepository.existsById(id)).thenReturn(true);
        boolean result = reoccurringTransactionService.deleteReoccurringTransaction(id);
        assertTrue(result);
        verify(reoccurringTransactionRepository,times(1)).deleteById(id);
    }

    @Test
    void testDeleteReoccurringTransactionDNE() {
        Long id = 3L;
        when(reoccurringTransactionRepository.existsById(id)).thenReturn(false);
        boolean result = reoccurringTransactionService.deleteReoccurringTransaction(id);
        assertFalse(result);
    }
}

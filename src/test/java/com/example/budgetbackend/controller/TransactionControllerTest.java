package com.example.budgetbackend.controller;

import com.example.budgetbackend.mockGenerator.TransactionMockGenerator;
import com.example.budgetbackend.model.Transaction;
import com.example.budgetbackend.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    private List<Transaction> mockTransactions;
    private Transaction mockTransaction;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTransactions = TransactionMockGenerator.generateTransactionList();
        mockTransaction = mockTransactions.get(0);
    }

    @Test
    void testGetAllTransactions_returnTransactionList() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(mockTransactions);
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockTransaction.getId()));

        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void getTransactionById_shouldReturnTransaction() throws Exception {
        when(transactionService.getTransactionById(mockTransaction.getId())).thenReturn(Optional.of(mockTransaction));

        mockMvc.perform(get("/transactions/{id}", mockTransaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockTransaction.getId()));
        verify(transactionService, times(1)).getTransactionById(mockTransaction.getId());
    }

    @Test
    void getTransactionById_shouldReturnNotFound() throws Exception {
        when(transactionService.getTransactionById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/transactions/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).getTransactionById(1L);
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

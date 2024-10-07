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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("mocks/transactions.json");
        mockTransactions = objectMapper.readValue(jsonStream,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));
        mockTransaction = mockTransactions.get(0);
    }

    @Test
    void testGetAllTransactions_shouldReturnTransactionList() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(mockTransactions);
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockTransaction.getId()));

        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void testGetTransactionById_shouldReturnTransaction() throws Exception {
        when(transactionService.getTransactionById(mockTransaction.getId())).thenReturn(Optional.of(mockTransaction));

        mockMvc.perform(get("/transactions/{id}", mockTransaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockTransaction.getId()));
        verify(transactionService, times(1)).getTransactionById(mockTransaction.getId());
    }

    @Test
    void testGetTransactionById_shouldReturnNotFound() throws Exception {
        when(transactionService.getTransactionById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/transactions/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).getTransactionById(1L);
    }

    @Test
    void testCreateTransaction_shouldReturnTransaction() throws Exception {
        when(transactionService.saveTransaction(any(Transaction.class))).thenReturn(mockTransaction);
        String inputJsonString = asJsonString(mockTransaction);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockTransaction.getId()));
        verify(transactionService, times(1)).saveTransaction(any(Transaction.class));
    }

    @Test
    void testUpdateTransaction_shouldReturnTransaction() throws Exception {
        Long id = mockTransaction.getId();
        when(transactionService.updateTransaction(eq(id),any(Transaction.class)))
                .thenReturn(Optional.of(mockTransaction));
        String inputJsonString = asJsonString(mockTransaction);
        mockMvc.perform(put("/transactions/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockTransaction.getId()));

        verify(transactionService, times(1)).updateTransaction(eq(id),any(Transaction.class));
    }

    @Test
    void testUpdateTransaction_shouldReturnNotFound() throws Exception {
        Long id = 3L;
        when(transactionService.updateTransaction(eq(id),any(Transaction.class)))
                .thenReturn(Optional.empty());
        String inputJsonString = asJsonString(mockTransaction);
        mockMvc.perform(put("/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJsonString))
                        .andExpect(status().isNotFound());
        verify(transactionService, times(1)).updateTransaction(eq(id),any(Transaction.class));
    }

    @Test
    void testDeleteTransaction_shouldFindTransaction() throws Exception {
        Long id = 1L;
        when(transactionService.deleteTransaction(id)).thenReturn(true);
        mockMvc.perform(delete("/transactions/{id}", id))
                .andExpect(status().isNoContent());
         verify(transactionService,times(1)).deleteTransaction(id);
    }

    @Test
    void testDeleteTransaction_shouldNotFindTransaction() throws Exception {
        Long id = 3L;
        when(transactionService.deleteTransaction(id)).thenReturn(false);
        mockMvc.perform(delete("/transactions/{id}", id))
                .andExpect(status().isNotFound());
        verify(transactionService,times(1)).deleteTransaction(id);
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

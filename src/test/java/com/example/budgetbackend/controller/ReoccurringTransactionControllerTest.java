package com.example.budgetbackend.controller;

import com.example.budgetbackend.mockGenerator.ReoccurringTransactionMockGenerator;
import com.example.budgetbackend.model.ReoccurringTransaction;
import com.example.budgetbackend.service.ReoccurringTransactionService;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReoccurringTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReoccurringTransactionService reoccurringTransactionService;

    private List<ReoccurringTransaction> mockReoccurringTransactions;

    private ReoccurringTransaction mockReoccurringTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockReoccurringTransactions = ReoccurringTransactionMockGenerator.generateReoccuringTransactionList();
        mockReoccurringTransaction = mockReoccurringTransactions.get(0);
    }

    @Test
    void testGetAllReoccurringTransactions_shouldReturnReoccurringTransactionList() throws Exception {
        when(reoccurringTransactionService.getAllReoccurringTransactions()).thenReturn(mockReoccurringTransactions);
        mockMvc.perform(get("/reoccurring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockReoccurringTransaction.getId()));
        verify(reoccurringTransactionService, times(1)).getAllReoccurringTransactions();
    }

    @Test
    void testGetReoccurringTransactionById_shouldReturnReoccurringTransaction() throws Exception {
        when(reoccurringTransactionService.getReoccurringTransactionById(mockReoccurringTransaction.getId())).thenReturn(Optional.of(mockReoccurringTransaction));

        mockMvc.perform(get("/reoccurring/{id}", mockReoccurringTransaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockReoccurringTransaction.getId()));
        verify(reoccurringTransactionService, times(1)).getReoccurringTransactionById(mockReoccurringTransaction.getId());
    }

    @Test
    void testGetReoccurringTransactionById_shouldReturnNotFound() throws Exception {
        when(reoccurringTransactionService.getReoccurringTransactionById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/reoccurring/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(reoccurringTransactionService, times(1)).getReoccurringTransactionById(1L);
    }

    @Test
    void testCreateReoccurringTransaction_shouldReturnReoccurringTransaction() throws Exception {
        when(reoccurringTransactionService.saveReoccurringTransaction(any(ReoccurringTransaction.class))).thenReturn(mockReoccurringTransaction);
        String inputJsonString = asJsonString(mockReoccurringTransaction);
        mockMvc.perform(post("/reoccurring")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockReoccurringTransaction.getId()));
        verify(reoccurringTransactionService, times(1)).saveReoccurringTransaction(any(ReoccurringTransaction.class));
    }

    @Test
    void testUpdateReoccurringTransaction_shouldReturnReoccurringTransaction() throws Exception {
        Long id = mockReoccurringTransaction.getId();
        when(reoccurringTransactionService.updateReoccurringTransaction(eq(id),any(ReoccurringTransaction.class)))
                .thenReturn(Optional.of(mockReoccurringTransaction));
        String inputJsonString = asJsonString(mockReoccurringTransaction);
        mockMvc.perform(put("/reoccurring/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockReoccurringTransaction.getId()));

        verify(reoccurringTransactionService, times(1)).updateReoccurringTransaction(eq(id),any(ReoccurringTransaction.class));
    }

    @Test
    void testUpdateTransaction_shouldReturnNotFound() throws Exception {
        Long id = 3L;
        when(reoccurringTransactionService.updateReoccurringTransaction(eq(id),any(ReoccurringTransaction.class)))
                .thenReturn(Optional.empty());
        String inputJsonString = asJsonString(mockReoccurringTransaction);
        mockMvc.perform(put("/reoccurring/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJsonString))
                .andExpect(status().isNotFound());
        verify(reoccurringTransactionService, times(1)).updateReoccurringTransaction(eq(id),any(ReoccurringTransaction.class));
    }

    @Test
    void testDeleteTransaction_shouldFindTransaction() throws Exception {
        Long id = 1L;
        when(reoccurringTransactionService.deleteReoccurringTransaction(id)).thenReturn(true);
        mockMvc.perform(delete("/reoccurring/{id}", id))
                .andExpect(status().isNoContent());
        verify(reoccurringTransactionService,times(1)).deleteReoccurringTransaction(id);
    }

    @Test
    void testDeleteTransaction_shouldNotFindTransaction() throws Exception {
        Long id = 3L;
        when(reoccurringTransactionService.deleteReoccurringTransaction(id)).thenReturn(false);
        mockMvc.perform(delete("/reoccurring/{id}", id))
                .andExpect(status().isNotFound());
        verify(reoccurringTransactionService,times(1)).deleteReoccurringTransaction(id);
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

package com.example.budgetbackend.controller;

import com.example.budgetbackend.model.ReoccurringTransaction;
import com.example.budgetbackend.service.ReoccurringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ReoccurringTransactionController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReoccurringTransactionService reoccurringTransactionService;

    private List<ReoccurringTransaction> mockReoccurringTransactions;

    private ReoccurringTransaction mockReoccurringTransaction;
}

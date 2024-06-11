package com.example.budgetbackend.controller;

import com.example.budgetbackend.service.TransactionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;



}

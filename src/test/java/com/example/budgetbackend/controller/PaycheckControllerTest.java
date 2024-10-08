package com.example.budgetbackend.controller;

import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.service.PaycheckService;
import com.example.budgetbackend.testUtils.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class PaycheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaycheckService paycheckService;

    private List<Paycheck> mockPaychecks;

    private Paycheck mockPaycheck;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        mockPaychecks = DataLoader.loadMockData("mocks/paychecks.json", Paycheck.class);
        mockPaycheck = mockPaychecks.get(0);
    }

    @Test
    void testGetAllPaychecks_shouldReturnList() throws Exception {
        when(paycheckService.getAllPaychecks()).thenReturn(mockPaychecks);
        mockMvc.perform(get("/paychecks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockPaycheck.getId()));
        verify(paycheckService, times(1)).getAllPaychecks();
    }

    @Test
    void testGetAllPaychecks_shouldNotFound() throws Exception {

    }

    @Test
    void testGetPaycheckById_shouldReturnPaycheck() throws Exception {

    }

    @Test
    void testGetPaycheckById_shouldReturnNotFound() throws Exception {

    }

    @Test
    void testCreatePaycheck_shouldReturnPaycheck() throws Exception {

    }

    @Test
    void testCreatePaycheckItem_shouldReturnPaycheckItem() throws Exception {

    }

    @Test
    void testUpdatePaycheck_shouldReturnPaycheck() throws Exception {

    }

    @Test
    void testUpdatePaycheck_shouldReturnNotFound() throws Exception {

    }

    @Test
    void testUpdatePaycheckItem_shouldReturnPaycheckItem() throws Exception {

    }

    @Test
    void testUpdatePaycheckItem_shouldReturnNotFound() throws Exception {

    }

    @Test
    void testDeletePaycheck_shouldReturnPaycheck() throws Exception {

    }

    @Test
    void testDeletePaycheck_shouldReturnNotFound() throws Exception {

    }

    @Test
    void testDeletePaycheckItem_shouldReturnPaycheckItem() throws Exception {

    }

    @Test
    void testDeletePaycheckItem_shouldReturnNotFound() throws Exception {

    }



}

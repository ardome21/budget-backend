package com.example.budgetbackend.controller;

import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.model.PaycheckItem;
import com.example.budgetbackend.service.PaycheckService;
import com.example.budgetbackend.testUtils.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PaycheckController.class)
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
    void testGetPaycheckById_shouldReturnPaycheck() throws Exception {
        when(paycheckService.getPaycheckById(1L)).thenReturn(Optional.ofNullable(mockPaycheck));
        mockMvc.perform(get("/paychecks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockPaycheck.getId()));
        verify(paycheckService, times(1)).getPaycheckById(any());
    }

    @Test
    void testGetPaycheckItemById_shouldReturnPaycheck() throws Exception {
        PaycheckItem mockPaycheckItem = mockPaycheck.getGrossPay();
        when(paycheckService.getPaycheckItemById(1L)).thenReturn(Optional.ofNullable(mockPaycheckItem));
        mockMvc.perform(get("/paychecks/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockPaycheck.getId()));
        verify(paycheckService, times(1)).getPaycheckItemById(any());
    }

    @Test
    void testCreatePaycheck_shouldReturnPaycheck() throws Exception {
        when(paycheckService.createPaycheck(any())).thenReturn(mockPaycheck);
        String paycheckString = DataLoader.asJsonString(mockPaycheck);
        mockMvc.perform(post("/paychecks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paycheckString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockPaycheck.getId()));
        verify(paycheckService, times(1)).createPaycheck(any());
    }

    @Test
    void testCreatePaycheckItem_shouldReturnPaycheckItem() throws Exception {
        PaycheckItem mockPaycheckItem = mockPaycheck.getTaxes().get(0);
        when(paycheckService.createPaycheckItem(any(),any(), any())).thenReturn(mockPaycheckItem);
        String paycheckItemString = DataLoader.asJsonString(mockPaycheckItem);
        mockMvc.perform(post("/paychecks/items")
                .param("paycheckId", "1")
                .param("category", "taxes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paycheckItemString))
                .andExpect(status().isOk());
        verify(paycheckService, times(1)).createPaycheckItem(any(),any(),any());
    }

    @Test
    void testUpdatePaycheck_shouldReturnPaycheck() throws Exception {
        when(paycheckService.updatePaycheck(any(),any())).thenReturn(Optional.ofNullable(mockPaycheck));
        String paycheckString = DataLoader.asJsonString(mockPaycheck);
        mockMvc.perform(put("/paychecks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paycheckString))
                .andExpect(status().isOk());
        verify(paycheckService, times(1)).updatePaycheck(any(),any());
    }

    @Test
    void testUpdatePaycheckItem_shouldReturnPaycheckItem() throws Exception {
        PaycheckItem mockPaycheckItem = mockPaycheck.getTaxes().get(0);
        when(paycheckService.updatePaycheckItem(any(),any(), any(), any())).thenReturn(Optional.ofNullable(mockPaycheckItem));
        String paycheckString = DataLoader.asJsonString(mockPaycheck);
        mockMvc.perform(put("/paychecks/items/2")
                .param("paycheckId", "1")
                .param("category", "taxes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paycheckString))
                .andExpect(status().isOk());
        verify(paycheckService, times(1)).updatePaycheckItem(any(),any(), any(), any());
    }

    @Test
    void testDeletePaycheck_shouldFindPaycheck() throws Exception {
        when(paycheckService.deletePaycheck(any())).thenReturn(true);
        mockMvc.perform(delete("/paychecks/1"))
                .andExpect(status().isNoContent());
        verify(paycheckService, times(1)).deletePaycheck(any());
    }

    @Test
    void testDeletePaycheck_shouldNotFindPaycheck() throws Exception {
        when(paycheckService.deletePaycheck(any())).thenReturn(false);
        mockMvc.perform(delete("/paychecks/1"))
                .andExpect(status().isNotFound());
        verify(paycheckService, times(1)).deletePaycheck(any());
    }


    @Test
    void testDeletePaycheckItem_shouldFindPaycheckItem() throws Exception {
        when(paycheckService.deletePaycheckItem(any())).thenReturn(true);
        mockMvc.perform(delete("/paychecks/items/1"))
                .andExpect(status().isNoContent());
        verify(paycheckService, times(1)).deletePaycheckItem(any());
    }

    @Test
    void testDeletePaycheckItem_shouldNotFindPaycheckItem() throws Exception {
        when(paycheckService.deletePaycheckItem(any())).thenReturn(false);
        mockMvc.perform(delete("/paychecks/items/1"))
                .andExpect(status().isNotFound());
        verify(paycheckService, times(1)).deletePaycheckItem(any());
    }




}

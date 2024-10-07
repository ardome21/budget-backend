package com.example.budgetbackend.mockGenerator;

import com.example.budgetbackend.entity.TransactionDO;
import com.example.budgetbackend.model.Transaction;
import com.example.budgetbackend.model.TransactionItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// CODE_DEBT: To be deleted and all mocks should be hardcode JSONS
public class TransactionMockGenerator {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static List<Object[]> generateRawDataList() {
        List<Object[]> rawDataList = new ArrayList<>();
        rawDataList.add(new Object[]{1L, "2023-06-10", "Sample Transaction 1", "Category 1", 100.00});
        rawDataList.add(new Object[]{2L, "2023-06-11", "Sample Transaction 2", "Category 2", 200.00});
        return rawDataList;
    }
    private static Transaction generateTransaction(
            Long id,
            String date,
            String description,
            String category,
            double amount
    ) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setDate(LocalDate.parse(date, formatter));
        TransactionItem transactionItem = generateTransactionItem(description, category, amount);
        transaction.setTransactionItem(transactionItem);
        return transaction;
    }
    private static TransactionItem generateTransactionItem(String description, String category, double amount) {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setDescription(description);
        transactionItem.setCategory(category);
        transactionItem.setAmount(amount);
        return transactionItem;
    }

    private static TransactionDO generateTransactionDO(
            Long id,
            String date,
            String description,
            String category,
            double amount
    ) {
        TransactionDO transactionDO = new TransactionDO();
        transactionDO.setId(id);
        transactionDO.setDate(LocalDate.parse(date, formatter));
        transactionDO.setDescription(description);
        transactionDO.setCategory(category);
        transactionDO.setAmount(amount);
        return transactionDO;
    }

    public static List<Transaction> generateTransactionList(){
        List<Transaction> transactions = new ArrayList<>();
        List<Object[]> rawDataList = generateRawDataList();
        for (Object[] rawData : rawDataList) {
            transactions.add(generateTransaction(
                    (Long) rawData[0],
                    (String) rawData[1],
                    (String) rawData[2],
                    (String) rawData[3],
                    (Double) rawData[4]));
        }
        return transactions;
    }

    public static List<TransactionDO> generateTransactionDOList(){
        List<TransactionDO> transactionDOs = new ArrayList<>();
        List<Object[]> rawDataList = generateRawDataList();

        for (Object[] rawData: rawDataList) {
            transactionDOs.add(generateTransactionDO(
                    (Long) rawData[0],
                    (String) rawData[1],
                    (String) rawData[2],
                    (String) rawData[3],
                    (Double) rawData[4]));

        }
        return transactionDOs;
    }
}

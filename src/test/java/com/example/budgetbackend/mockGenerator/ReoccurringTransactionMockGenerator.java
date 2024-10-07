package com.example.budgetbackend.mockGenerator;

import com.example.budgetbackend.entity.ReoccurringTransactionDO;
import com.example.budgetbackend.model.Frequency;
import com.example.budgetbackend.model.ReoccurringTransaction;
import com.example.budgetbackend.model.TransactionItem;

import java.util.ArrayList;
import java.util.List;


// CODE_DEBT: To be deleted and all mocks should be hardcode JSONS
public class ReoccurringTransactionMockGenerator {

    private static List<Object[]> generateRawDataList() {
        List<Object[]> rawDataList = new ArrayList<>();
        rawDataList.add(new Object[]{1L, 1, "month", "Sample Transaction 1", "Category 1", 100.00});
        rawDataList.add(new Object[]{2L, 1, "week", "Sample Transaction 2", "Category 2", 200.00});
        return rawDataList;
    }

    private static ReoccurringTransaction generateReoccurringTransaction(
            Long id,
            int frequencyValue,
            String frequencyUnit,
            String description,
            String category,
            double amount
    ){
        ReoccurringTransaction reoccurringTransaction = new ReoccurringTransaction();
        reoccurringTransaction.setId(id);
        Frequency frequency = generateFrequency(frequencyValue, frequencyUnit);
        reoccurringTransaction.setFrequency(frequency);
        TransactionItem transactionItem = generateTransactionItem(description, category, amount);
        reoccurringTransaction.setTransactionItem(transactionItem);
        return reoccurringTransaction;
    }

    private static Frequency generateFrequency(int frequencyValue, String frequencyUnit) {
        return new Frequency(frequencyValue, frequencyUnit);
    }

    private static TransactionItem generateTransactionItem(String description, String category, double amount) {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setDescription(description);
        transactionItem.setCategory(category);
        transactionItem.setAmount(amount);
        return transactionItem;
    }

    private static ReoccurringTransactionDO generateReoccurringTransactionDO(
            Long id,
            int frequencyValue,
            String frequencyUnit,
            String description,
            String category,
            double amount
    ) {
        ReoccurringTransactionDO reoccurringTransactionDO = new ReoccurringTransactionDO();
        reoccurringTransactionDO.setId(id);
        reoccurringTransactionDO.setFrequencyValue(frequencyValue);
        reoccurringTransactionDO.setFrequencyUnit(frequencyUnit);
        reoccurringTransactionDO.setDescription(description);
        reoccurringTransactionDO.setCategory(category);
        reoccurringTransactionDO.setAmount(amount);
        return reoccurringTransactionDO;
    }

    public static List<ReoccurringTransaction> generateReoccuringTransactionList(){
        List<ReoccurringTransaction> reoccurringTransactions = new ArrayList<>();
        List<Object[]> rawDataList = generateRawDataList();
        for (Object[] rawData : rawDataList) {
            reoccurringTransactions.add(generateReoccurringTransaction(
                    (Long) rawData[0],
                    (int) rawData[1],
                    (String) rawData[2],
                    (String) rawData[3],
                    (String) rawData[4],
                    (double) rawData[5]));
        }
        return reoccurringTransactions;
    }

    public static List<ReoccurringTransactionDO> generateReoccuringTransactionDOList(){
        List<ReoccurringTransactionDO> reoccurringTransactionDOs = new ArrayList<>();
        List<Object[]> rawDataList = generateRawDataList();

        for (Object[] rawData: rawDataList) {
            reoccurringTransactionDOs.add(generateReoccurringTransactionDO(
                    (Long) rawData[0],
                    (int) rawData[1],
                    (String) rawData[2],
                    (String) rawData[3],
                    (String) rawData[4],
                    (double) rawData[5]));

        }
        return reoccurringTransactionDOs;
    }
}

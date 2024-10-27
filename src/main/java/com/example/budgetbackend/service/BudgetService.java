package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.BudgetItemDO;
import com.example.budgetbackend.mapper.BudgetMapper;
import com.example.budgetbackend.model.Budget;
import com.example.budgetbackend.model.BudgetItem;
import com.example.budgetbackend.model.TransactionItem;
import com.example.budgetbackend.repository.BudgetItemRepository;
import com.example.budgetbackend.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    private final BudgetItemRepository budgetItemRepository;

    private final BudgetMapper budgetMapper;

    private final TransactionService transactionService;

    private final PaycheckService paycheckService;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository,
                         BudgetItemRepository budgetItemRepository,
                         BudgetMapper budgetMapper,
                         TransactionService transactionService,
                         PaycheckService paycheckService){
        this.budgetRepository = budgetRepository;
        this.budgetItemRepository = budgetItemRepository;
        this.budgetMapper = budgetMapper;
        this.transactionService = transactionService;
        this.paycheckService = paycheckService;
    }

    public List<Budget> getAllBudgets() {
        return getAllSimpleBudgets()
                .stream()
                .map(this::getBudgetItemDOsandCreateBudget)
                .toList();
    }

    private Budget getBudgetItemDOsandCreateBudget(Budget budget) {
        List<BudgetItemDO> budgetItemDOs = getBudgetItemDOsByBudgetId(budget.getId());
        return createBudgetFromBudgetItemDOs(budget.getMonth(), budgetItemDOs);
    }

    public Budget createBudgetFromBudgetItemDOs(YearMonth month, List<BudgetItemDO> budgetItemDOs) {
        if (budgetItemDOs.isEmpty()){
            return null;
        }
        Budget budget = new Budget();
        budget.setId(budgetItemDOs.get(0).getBudgetId());
        budget.setMonth(month);

        double takeHome = paycheckService.getUserTakeHome();
        budget.setTakeHome(takeHome);

        double totalPlanned = 0;
        double totalSpent = 0;

        List<BudgetItem> budgetItems = budgetMapper.entityListToModelList(budgetItemDOs);

        for (BudgetItem item : budgetItems) {
            double monthlySpent = calculateSpent(month, item);
            item.setSpent(monthlySpent);
        }

        for (BudgetItem item : budgetItems) {
            totalPlanned += item.getPlanned();
            totalSpent += item.getSpent();
        }

        budget.setCategories(budgetItems);
        budget.setTotalPlanned(totalPlanned);
        budget.setTotalSpent(totalSpent);
        return budget;
    }

    // CODE_DEBT - should there be a flag or something if the data is impossible?
    //      -1. reoccurring transactions of a category are more than budget allotment for category or
    //      -2. grossPay from paycheck doesn't match total budget allotment
    //  I don't think so, but maybe there should be a database health checkout that periodically runs
    public double calculateSpent(YearMonth month, BudgetItem rawBudgetItem) {
        String category = rawBudgetItem.getCategory();
        List<TransactionItem> monthlyTransactions = transactionService.getTransactionsByMonthAndCategory(month, category);
        return monthlyTransactions.stream()
                .mapToDouble(TransactionItem::getAmount)
                .sum();
    }

    public List<BudgetItemDO> getBudgetItemDOsByBudgetId(Long budgetId) {
        return budgetItemRepository.findByBudgetId(budgetId);
    }

    private List<Budget> getAllSimpleBudgets() {
        return budgetRepository.findAll()
                .stream()
                .map(budgetMapper::entityToModel)
                .toList();
    }

    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .map(budgetMapper::entityToModel)
                .map(this::getBudgetItemDOsandCreateBudget);
    }
}

package com.odde.budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class BudgetService {

    private final List<Budget> budgetList;

    public BudgetService(BudgetRepo budgetRepo) {
        budgetList = budgetRepo.findAll();
    }

    public long queryBudget(LocalDate start, LocalDate end) {
        if (budgetList.isEmpty())
            return 0;

        int budgetAmount = 0;
        for (Budget budgetItem : budgetList) {
            int validDays = new TimePeriod(start, end).getDaysInPeriod(budgetItem.getYearMonth());
            budgetAmount += budgetItem.getDailyAmount() * validDays;
        }

        return budgetAmount;
    }
}

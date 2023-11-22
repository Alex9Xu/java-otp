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
            budgetAmount += getMoneyInThisMonth(budgetItem, start, end);
        }

        return budgetAmount;
    }

    private int getMoneyInThisMonth(Budget repoItem, LocalDate start, LocalDate end) {
        YearMonth startYearMonth = YearMonth.of(start.getYear(), start.getMonth());
        YearMonth endYearMonth = YearMonth.of(end.getYear(), end.getMonth());

        if (repoItem.getYearMonth().isBefore(startYearMonth))
            return 0;
        else if (repoItem.getYearMonth().isAfter(endYearMonth))
            return 0;
        else if (repoItem.getYearMonth().equals(startYearMonth))
            return getMoneyInStart(start, repoItem);
        else if (repoItem.getYearMonth().equals(endYearMonth))
            return getMoneyInEnd(end, repoItem);
        else
            return repoItem.getAmount();
    }

    private int getMoneyInStart(LocalDate start, Budget repoItem) {
        if (start.getDayOfMonth() == 1)
            return repoItem.getAmount();

        YearMonth yearMonth = YearMonth.of(start.getYear(), start.getMonth());
        int validDays = yearMonth.lengthOfMonth() - start.getDayOfMonth() + 1;

        return repoItem.getDailyAmount() * validDays;
    }

    private int getMoneyInEnd(LocalDate end, Budget repoItem) {
        int validDays = end.getDayOfMonth();
        return repoItem.getDailyAmount() * validDays;
    }
}

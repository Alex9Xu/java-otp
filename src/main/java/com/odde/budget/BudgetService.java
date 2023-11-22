package com.odde.budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class BudgetService {

    private final BudgetRepo budgetRepo = new BudgetRepo() {
        @Override
        public List<Budget> findAll() {
            List<Budget> budgetList = new ArrayList<>();
            budgetList.add(octBudget);
            budgetList.add(novBudget);
            budgetList.add(decBudget);

            return budgetList;
        }

        private final Budget octBudget = new Budget(YearMonth.of(2022,10), 31);
        private final Budget novBudget = new Budget(YearMonth.of(2022,11), 30);
        private final Budget decBudget = new Budget(YearMonth.of(2022, 12), 62);
    };

    public long queryBudget(LocalDate start, LocalDate end) {
        if (budgetRepo.findAll().isEmpty())
            return 0;

        int budgetRepoSize = budgetRepo.findAll().size();

        int budgetAmount = 0;
        for (int i=0; i<budgetRepoSize; i++) {
            Budget budgetItem = budgetRepo.findAll().get(i);
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
        int monthLength = yearMonth.lengthOfMonth();
        int passedDays = start.getDayOfMonth() - 1;
        int validDays = monthLength - passedDays;

        return (repoItem.getAmount() / monthLength) * validDays;
    }

    private int getMoneyInEnd(LocalDate end, Budget repoItem) {
        YearMonth yearMonth = YearMonth.of(end.getYear(), end.getMonth());
        int monthLength = yearMonth.lengthOfMonth();

        int validDays = end.getDayOfMonth();

        return (repoItem.getAmount() / monthLength) * validDays;
    }
}

package com.odde.budget;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetTest {

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

    private final BudgetService budgetService = new BudgetService(budgetRepo);

    @Test
    public void GIVEN_start_later_than_budget_THEN_0_yuan() {
        long budgetAmount = budgetService.queryBudget(
                LocalDate.of(2023, 11, 30),
                LocalDate.of(2023, 12, 1)
        );

        assertEquals(0, budgetAmount);
    }

    @Test
    public void GIVEN_end_earlier_than_budget_start_THEN_0_yuan() {
        long budgetAmount = budgetService.queryBudget(
                LocalDate.of(2021, 11, 30),
                LocalDate.of(2021, 12, 1)
        );

        assertEquals(0, budgetAmount);
    }

    @Test
    public void GIVEN_in_11month_30yuan_12month_62yuan_THEN_920_to_1201_3yuan() {
        long budgetAmount = budgetService.queryBudget(
                LocalDate.of(2022, 9, 20),
                LocalDate.of(2022, 11, 1)
        );

        assertEquals(32, budgetAmount);
    }

    @Test
    public void GIVEN_in_11month_30yuan_12month_62yuan_THEN_1130_to_1201_3yuan() {
        long budgetAmount = budgetService.queryBudget(
                LocalDate.of(2022, 11, 30),
                LocalDate.of(2022, 12, 1)
        );

        assertEquals(3, budgetAmount);
    }

    @Test
    public void GIVEN_in_11month_30yuan_12month_62yuan_THEN_1130_to_102_3yuan() {
        long budgetAmount = budgetService.queryBudget(
                LocalDate.of(2022, 11, 30),
                LocalDate.of(2023, 1, 2)
        );

        assertEquals(63, budgetAmount);
    }
}

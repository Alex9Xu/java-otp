package com.odde.budget;

import java.time.YearMonth;

public class Budget {

    private final YearMonth yearMonth;
    private final int amount;

    Budget(YearMonth time, int money) {
        yearMonth = time;
        amount = money;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public int getAmount() {
        return amount;
    }

    public int getDailyAmount() {
        return  amount / yearMonth.lengthOfMonth();
    }
}

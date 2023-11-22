package com.odde.budget;

import java.time.LocalDate;
import java.time.YearMonth;

public class TimePeriod {
    private final LocalDate start;
    private final LocalDate end;

    TimePeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public int getDaysInPeriod(YearMonth yearMonth) {
        if (yearMonth.equals(YearMonth.of(start.getYear(), start.getMonth())))
            return YearMonth.of(start.getYear(), start.getMonth()).lengthOfMonth() - start.getDayOfMonth() + 1;
        else if (yearMonth.equals(YearMonth.of(end.getYear(), end.getMonth())))
            return end.getDayOfMonth();
        else if (yearMonth.isAfter(YearMonth.of(start.getYear(), start.getMonth()))
                && yearMonth.isBefore(YearMonth.of(end.getYear(), end.getMonth())))
            return yearMonth.lengthOfMonth();
        else
            return 0;
    }
}

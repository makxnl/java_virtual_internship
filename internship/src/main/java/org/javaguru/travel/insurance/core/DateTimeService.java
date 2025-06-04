package org.javaguru.travel.insurance.core;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class DateTimeService {

    public long getDaysBetween(Date date1, Date date2) {
        if (date1.after(date2) || date1.equals(date2)) {
            throw new IllegalArgumentException("Начальная дата должна быть раньше конечной");
        }
        long diffInMillis = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

}
package org.javaguru.travel.insurance.core;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class DateTimeService {

    public long getDaysBetween(Date date1, Date date2) {
        if (date1.after(date2) || date1.equals(date2)) {
            throw new RuntimeException();
        }
        long diffInMillis = date1.getTime() - date2.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

}
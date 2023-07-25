package com.lms.profileservice.utility;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.ToIntBiFunction;

@Service
public class CommonUtility {

    public static ToIntBiFunction<Date, Date> getDifferenceDays = (from, to) -> {
        long diff = to.getTime() - from.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
    };

}

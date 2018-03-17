package com.banregio.examenbregio.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jordan on 16/03/2018.
 */

public class Utils {
    public static String DATE_BIRTHDAY_FORMAT = "dd/MM/yyyy";

    public static String getBirthDayDateFormat(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_BIRTHDAY_FORMAT);
        return sdf.format(date);
    }
}

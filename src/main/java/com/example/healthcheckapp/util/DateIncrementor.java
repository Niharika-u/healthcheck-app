package com.example.healthcheckapp.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created By MMT6540 on 09 Apr, 2018
 */
public class DateIncrementor {
    public static Date incrementDate(Date dateToIncrement, int noOfDaysToIncrement){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToIncrement);
        cal.add(Calendar.DATE, noOfDaysToIncrement);
        return cal.getTime();
    }
 }
package com.bmw.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.JulianFields;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private DateUtil() {

	}


	public static String toJulianString(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate formatted = LocalDate.parse(dateString,formatter);
		return toJulianString(formatted);
	}

    public static String toJulianString(LocalDate date) {
		long julianDate = JulianFields.JULIAN_DAY.getFrom(date);
		return String.valueOf(julianDate);
    }

    public static String toJulianString(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
		LocalDate localDate = LocalDate.of(
				calendar.get(Calendar.YEAR) ,
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));

		long julianDate = JulianFields.JULIAN_DAY.getFrom ( localDate );
		return String.valueOf(julianDate);
    }

    public static Date stringToDate(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate formatted = LocalDate.parse(dateStr,formatter);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = formatted.atStartOfDay().atZone(zone).toInstant();
		return Date.from(instant);
    }

    public static String dateToString(LocalDate date) {
    	DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
    	return formatter.format(date);
    }

    public static int compareDateString(String firstDateString, String secondDateString) {
    	Date firstDate = stringToDate(firstDateString);
    	Date secondDate = stringToDate(secondDateString);
    	return firstDate.compareTo(secondDate);
    }

//    public static void main(String ...dateStr) {
//    	System.out.print(dateToString(LocalDate.now()));
//    }
    
    public static Long intervalDays(String startDateString) {
          Date startDate = stringToDate(startDateString);
          Date endDate = new Date();
          long diff = endDate.getTime() - startDate.getTime();//这样得到的差值是微秒级别
          Long days = diff / (1000 * 60 * 60 * 24);
          return days;
    }
}

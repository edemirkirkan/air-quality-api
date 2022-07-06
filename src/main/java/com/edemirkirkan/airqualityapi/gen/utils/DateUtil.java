package com.edemirkirkan.airqualityapi.gen.utils;

import com.edemirkirkan.airqualityapi.gen.exceptions.BusinessException;
import com.edemirkirkan.airqualityapi.pol.enums.EnumPolPollutionErrorMessage;
import org.springframework.data.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class DateUtil {
    public static Date timestampToDate(Long timestamp) {
        return new Date(timestamp*1000);

    }

    public static Long dateToTimestamp(Date date) {
        return date.getTime() / 1000;
    }

    public static Date stringToDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException ex) {
            throw new BusinessException(EnumPolPollutionErrorMessage.WRONG_DATE_FORMAT);
        }
        return date;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String timestampToString(Long timestamp) {
        return dateToString(timestampToDate(timestamp));
    }

    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static List<Date> getDatesInRange(Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = getCalendar(startDate, true);
        Calendar endCalendar = getCalendar(endDate, false);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return datesInRange;
    }

    private static Calendar getCalendar(Date date, boolean start) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (start) {
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } else {
            calendar.set(Calendar.HOUR, 11);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
        }

        return calendar;
    }

    public static Pair<String, String> getLastWeek() {
        Calendar cal = Calendar.getInstance();
        String endDate = DateUtil.dateToString(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String startDate = DateUtil.dateToString(cal.getTime());
        return Pair.of(startDate, endDate);
    }

}

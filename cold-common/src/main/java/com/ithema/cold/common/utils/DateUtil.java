package com.ithema.cold.common.utils;

import org.springframework.cache.CacheManager;
import sun.util.resources.cldr.si.CalendarData_si_LK;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-20  9:27
 */
public class DateUtil {

    public static final SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取过去几天的日期
     * @param past
     * @return
     */

    public static Date getPastDate(int past){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)-past);;
        return prase(calendar.getTime());
    }

    /**
     * 获取前几个小时
     * @param past
     * @return
     */

    public static Date getPastHour(int past){
        Calendar calendar =Calendar.getInstance();
        calendar.set(calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)-past);
        return prase(calendar.getTime());
    }

    /**
     * 计算指定日期前或者后的多少分钟
     * @param date
     * @param past
     * @return
     */

    public static String getPastOrLateMinutes(Date date,int past){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,past);
        return format.format(calendar.getTime());
    }

    /**
     * 获取当前月份的第一天
     * @param date
     * @return
     */
  public static Date getFirstDayOfMonth(Date date){
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int dom = calendar.get(Calendar.DAY_OF_MONTH);
      calendar.set(calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)-dom+1);
      Date today = prase(calendar.getTime());
      return today;
  }


    public static Date getFirstDayOfLastMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH-1));
        return getFirstDayOfMonth(calendar.getTime());
    }


    public static Date getFirstDayOfWeek(int weekStartDayCode){
        Calendar cal = Calendar.getInstance();
        int dow = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int past = 0;
        if (weekStartDayCode > dow) {
            past = dow + (weekStartDayCode == 0 ? 0 : (7 - weekStartDayCode));
        } else {
            past = dow - weekStartDayCode;
        }
        return getPastDate(past);
    }

    public static Date prase(Date date){
        return prase(getDateString(date));
    }

    public static Date prase(String dateString){
        Date now = null;
        try {
            now = format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return now;
    }

    public static String getDateString(Date date) {
        return format.format(date);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(format.format(getPastHour(5).getTime()));
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }


}

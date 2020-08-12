package com.mbw.office.common.util.date;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author Mabowen
 * @date 2020-04-08 22:07
 */
public class DateUtil {
    public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String SHORT_TIME_PATTERN = "HH:mm:ss";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String HHmmss = "HHmmss";

    public static Date now() {
        return new Date();
    }

    public static String getCurrentDate() {
        return formatShort(now());
    }

    public static String getCurrentYear() {
        return format(now(), "yyyy");
    }

    /**
     * 获取指定日期的年份
     * @author Mabowen
     * @date 13:57 2020-08-12
     * @param date
     * @return
     */
    public static String getYearOfDate(Date date) {
        if (date != null) {
            return format(date, "yyyy");
        }

        throw new ServiceException("date cannot be null");
    }

    public static String getCurrentMonth() {
        return format(now(), "MM");
    }

    /**
     * 获取指定日期的月份
     * @author Mabowen
     * @date 13:57 2020-08-12
     * @param date
     * @return
     */
    public static String getMonthOfDate(Date date) {
        if (date != null) {
            return format(date, "MM");
        }

        throw new ServiceException("date cannot be null");
    }

    /**
     * 获取当前时间戳---精确到毫秒
     * @author bowen.M
     * @date 2020/02/20 14:57
     * @return
     */
    public static String getCurrentTimestamp() {
        return format(now(), TIMESTAMP_PATTERN);
    }

    public static String getDayOfWeek(String date, String pattern) {
        if (StrUtil.isNotBlank(date) && StrUtil.isNotBlank(pattern)) {
            return getDayOfWeek(parse(date, pattern));
        }

        throw new ServiceException("date str and pattern cannot be empty");
    }

    /**
     * 获取当前时间是星期几
     * @author Mabowen
     * @date 16:52 2020-07-24
     * @param date
     */
    public static String getDayOfWeek(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
            switch (day) {
                case 0:
                    return "星期日";
                case 1:
                    return "星期一";
                case 2:
                    return "星期二";
                case 3:
                    return "星期三";
                case 4:
                    return "星期四";
                case 5:
                    return "星期五";
                case 6:
                    return "星期六";
                default:
                    return "";
            }
        }

        throw new ServiceException("date cannot be null");
    }

    /**
     * 格式化时间
     * @author Mabowen
     * @date 22:12 2020-04-08
     * @param
     * @return
     */
    public static String format(Date date, String pattern) {
        if (StrUtil.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }

        if (null == date) {
            throw new ServiceException("date cannot be null");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String formatDefault(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    public static String formatShort(Date date) {
        return format(date, SHORT_DATE_PATTERN);
    }

    /**
     * 解析日期字符串
     * @author Mabowen
     * @date 09:50 2020-04-09
     */
    public static Date parse(String str, String pattern) {
        if (StrUtil.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }

        if (StrUtil.isBlank(str)) {
            throw new ServiceException("date str cannot be empty");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            throw new ServiceException("parse date str exception：" + e.getMessage(), e);
        }
    }

    public static Date parseDefault(String str) {
        return parse(str, DEFAULT_PATTERN);
    }

    public static Date parseShort(String str) {
        return parse(str, SHORT_DATE_PATTERN);
    }

    /**
     * 获取传入时间多少天之前的日期
     * @author Mabowen
     * @date 10:16 2020-04-15
     * @param current
     * @param days
     * @return
     */
    public static Date getBeforeDay(Date current, int days) {
        if (current == null) {
            throw new ServiceException("传入的日期不能为null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        if (days > 0) {
            days = -days;
        }

        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    /**
     * 获取传入时间多少天之后的日期
     * @author Mabowen
     * @date 10:16 2020-04-15
     * @param current
     * @param days
     * @return
     */
    public static Date getAfterDay(Date current, int days) {
        if (current == null) {
            throw new ServiceException("传入的日期不能为null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);

        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    public static Date getDayBeginTime(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(year, month, day, 0, 0, 0);

            return cal.getTime();
        }

        throw new ServiceException("date cannot be null");
    }

    /**
     * 获取一天的开始时间
     * @author Mabowen
     * @date 17:11 2020-06-24
     * @param date
     * @return
     */
    public static String getDayBegin(Date date) {
        return formatDefault(getDayBeginTime(date));
    }

    public static Date getDayEndTime(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(year, month, day, 23, 59, 59);

            return cal.getTime();
        }

        throw new ServiceException("date cannot be null");
    }

    /**
     * 获取一天的结束时间
     * @author Mabowen
     * @date 17:11 2020-06-24
     * @param date
     * @return
     */
    public static String getDayEnd(Date date) {
        return formatDefault(getDayEndTime(date));
    }

    /**
     * 获取传入时间月份的第一天
     * @param date
     * @return
     */
    public static Date getMonthBeginTime(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);

            return cal.getTime();
        }

        throw new ServiceException("date cannot be null");
    }

    public static String getMonthBegin(Date date) {
        return formatShort(getMonthBeginTime(date));
    }

    /**
     * 获取传入时间月份的最后一天
     * @param date
     * @return
     */
    public static Date getMonthEndTime(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);

            //2月份平年和瑞年
            int lastDay = 0;
            if (2 == month) {
                lastDay = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
            } else {
                lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            cal.set(Calendar.DAY_OF_MONTH, lastDay);

            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);

            return cal.getTime();
        }

        throw new ServiceException("date cannot be null");
    }

    public static String getMonthEnd(Date date) {
        return formatShort(getMonthEndTime(date));
    }

    /**
     * 获取指定年份的开始日期
     * @param date
     * @return
     */
    public static Date getYearBeginTime(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int year = cal.get(Calendar.YEAR);
            cal.set(year, Calendar.JANUARY, 1, 0, 0, 0);

            return cal.getTime();
        }

        throw new ServiceException("date cannot be null");
    }

    public static String getYearBegin(Date date) {
        return formatShort(getYearBeginTime(date));
    }

    /**
     * 获取指定年份的结束日期
     * @param date
     * @return
     */
    public static Date getYearEndTime(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int year = cal.get(Calendar.YEAR);
            cal.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

            return cal.getTime();
        }

        throw new ServiceException("date cannot be null");
    }

    public static String getYearEnd(Date date) {
        return formatShort(getYearEndTime(date));
    }

    /**
     * 判断两个时间是否是同一天
     * @author Mabowen
     * @date 16:44 2020-06-24
     * @param date1
     * @param date2
     * @return true: 同一天；false：不是同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            return isSameDay(cal1, cal2);
        }

        throw new ServiceException("date1 or date2 can mot is null");
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 计算日期相差多少
     * @author Mabowen
     * @date 10:02 2020-04-10
     * @param startTime
     * @param endTime
     * @return Period.getYears(), getMonths(), getDays()
     */
    public static Period calculateTowTimesRange(Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);
            LocalDate start = LocalDate.of(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));

            cal.setTime(endTime);
            LocalDate end = LocalDate.of(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));

            return Period.between(start, end);
        }

        throw new ServiceException("startTime or endTime cannot be null");
    }

    /**
     * 获取两个日期段之间的每一天
     * @author Mabowen
     * @date 2020-06-03 20:15
     * @param beginTime
     * @param endTime
     * @return
     */
    public static List<Date> listDateBetween(Date beginTime, Date endTime) {
        if (beginTime != null && endTime != null) {
            if (!endTime.after(beginTime)) {
                throw new ServiceException("now before startDate");
            }

            //两个时间段之间相差多少天
            int rangeDay = (int) ((endTime.getTime() - beginTime.getTime()) / (24 * 60 * 60 * 1000));
            List<Date> rangeDate = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(beginTime);
            for (int i = 0; i <= rangeDay; i++) {
                rangeDate.add(cal.getTime());
                cal.add(Calendar.DATE, 1);
            }

            return rangeDate;
        }

        throw new ServiceException("beginTime or endTime cannot be null");
    }

    public static List<String> listTwoDateStrBetween(Date beginTime, Date endTime, String pattern) {
        if (StrUtil.isBlank(pattern)) {
            pattern = SHORT_DATE_PATTERN;
        }

        List<Date> dates = listDateBetween(beginTime, endTime);
        List<String> ranges = new ArrayList<>();
        if (CollUtil.isNotEmpty(dates)) {
            for (Date date : dates) {
                ranges.add(format(date, pattern));
            }
        }

        return ranges;
    }

    /**
     * 获取两个日期之间的所有月份
     * @author Mabowen
     * @date 18:31 2020-08-06
     * @param beginDate
     * @return
     */
    public static List<Date> getMonthDateBetween(Date beginDate, Date endDate) {
        if (beginDate != null && endDate != null) {
            Calendar min = Calendar.getInstance();
            min.setTime(beginDate);
            int beginYear = min.get(Calendar.YEAR);
            int beginMonth = min.get(Calendar.MONTH);
            min.set(beginYear, beginMonth, 1);

            Calendar max = Calendar.getInstance();
            max.setTime(endDate);
            int endYear = max.get(Calendar.YEAR);
            int endMonth = max.get(Calendar.MONTH);
            max.set(endYear, endMonth, 2);

            List<Date> months = new ArrayList<>();
            while (min.before(max)) {
                months.add(min.getTime());
                min.add(Calendar.MONTH, 1);
            }

            return months;
        }

        throw new ServiceException("beginDate and endDate cannot be null");
    }

    /**
     * 获取两个日期之间的月份字符串
     * @author Mabowen
     * @date 19:04 2020-08-06
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<String> getMonthStrBetween(Date beginDate, Date endDate, String pattern) {
        List<Date> dates = getMonthDateBetween(beginDate, endDate);
        List<String> months = new ArrayList<>();
        if (StrUtil.isBlank(pattern)) {
            pattern = "yyyy-MM";
        }
        if (!dates.isEmpty()) {
            for (Date date : dates) {
                String month = format(date, pattern);
                months.add(month);
            }
        }
        return months;
    }

    /**
     *
     * @param date1
     * @param date2
     * @return true: date1在date2之前；false：不在前，有可能在同一天或者之后
     */
    public static boolean date1BeforeDate2(Date date1, Date date2) {
        return judgeTwoTimesBeforeAndAfter(date1, date2) == -1;
    }

    /**
     * 判断两个时间的前后
     * @author Mabowen
     * @date 16:02 2020-07-03
     * @param date1
     * @param date2
     * @return -1: date1 < date2; 0: date1 == date2; 1: date1 > date2
     */
    public static int judgeTwoTimesBeforeAndAfter(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new ServiceException("date1 and date2 cannot be null");
        }

        if (isSameDay(date1, date2)) {
            return 0;
        } else {
            return date1.compareTo(date2);
        }
    }
}

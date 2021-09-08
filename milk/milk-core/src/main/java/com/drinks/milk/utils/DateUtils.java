package com.drinks.milk.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtils {
    public final static String DATE_PATTERN3 = "yyyy-MM";
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_ALL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public final static String DATE_PATTERN2 = "yyyyMMdd";
    public final static String START_TIME=" 00:00:00";
    public final static String END_TIME=" 23:59:59";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static Date parse(String date) {
        try {
            return parse(date, DATE_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateTime(String date) {
        try {
            return parse(date, DATE_TIME_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseDateTimeStr2DateStr(String date) {
        try {
            return format(parse(date, DATE_TIME_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parse2(String date) {
        try {
            return parse(date, DATE_PATTERN2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parse(String date, String pattern) throws ParseException {
        if (StringUtils.isNotBlank(date)) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(date);
        }
        return null;
    }

    /**
     * 毫秒转时长
     */
    public static String formatTime(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        String res = "";
        if (days != 0) {
            res += days + "天";
        }
        if (hours != 0) {
            res += hours + "小时";
        }
        if (minutes != 0) {
            res += minutes + "分钟";
        }
        if (seconds != 0) {
            res += seconds + "秒";
        }
        if (res == "") {
            res = "0秒";
        }
        return res;
    }

    public static String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days) {
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        } else if (hours > 0) {
            return hours + "小时" + minutes + "分" + second + "秒";
        } else if (minutes > 0) {
            return minutes + "分" + second + "秒";
        } else {
            return second + "秒";
        }
    }

    public static int getAgeByBirth(String birthday, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return getAgeByBirth(sdf.parse(birthday));
    }


    public static int getAgeByBirth(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthday);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static long getHaoMiaoCha(Date begin, Date end) {
        if (begin == null || end == null) return 0l;
        return end.getTime() - begin.getTime();
    }

    /**
     * 计算两个日期相关的天数
     *
     * @param start 相对较大的时间
     * @param end 相对较小的时间
     * @return
     */
    public static long differentDaysByMillisecond(Date start, Date end) {
        Long l_d1 = start.getTime() / 1000;
        Long l_d2 = end.getTime() / 1000;
        long days = (l_d1 - l_d2) / (3600 * 24);
        return days;
    }

    /**
     * 计算生日提醒使用
     * 取当前日期、年份，几天之后的日期
     * 两个日期是否跨年
     *
     * @param days
     * @return
     */
    public static Map<String, Object> getDateRange(int days) {
        Calendar calendar = Calendar.getInstance();
        // 现在日期
        String nowdate = format(calendar.getTime());
        // 截取月日
        String wnowdate = nowdate.substring(5, 10);
        // 获取7天后的日期
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
        String futureDate = DateUtils.format(calendar.getTime());
        // 截取后的日期
        String wafter = futureDate.substring(5, 10);
        // 截取月 判断是否是跨年，如果跨年则月份有可能会小于之后几天的 就比如 12.28 七天后就是01.04 这样就是月份很小了
        int ny = Integer.parseInt(wnowdate.substring(0, 2));
        int ay = Integer.parseInt(wafter.substring(0, 2));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("NOWDATE", nowdate);
        result.put("NOW", wnowdate);
        result.put("AFTER", wafter);
        result.put("IS_CROSS_YEAR", ny > ay);
        return result;
    }

    /**
     * 获取近intervals天日期
     *
     * @param intervals
     * @return
     */
    public static List<String> getLastDate(int intervals) {
        List<String> pastDaysList = new ArrayList<>();
        //ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = intervals - 1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
            //fetureDaysList.add(getFetureDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }

    // past天后的时间
    public static Date getFetureTime(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        return today;
    }

    // long转时间
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    //10位时间戳转换时间
    public static String timestampToString(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    // 取次日零点时间
    public static Date getNextDayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date = new Date();
        date = calendar.getTime();
        return date;
    }

    //获取每月天数
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断是否在同一周
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameWeek(Date d1, Date d2) {

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);// 西方周日为一周的第一天，咱得将周一设为一周第一天
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.setTime(d1);
        cal2.setTime(d2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (subYear == 0)// subYear==0,说明是同一年
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) // subYear==1,说明cal比cal2大一年;java的一月用"0"标识，那么12月用"11"
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11)// subYear==-1,说明cal比cal2小一年
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    public static String longToDate(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    // 取本周周一日期
    public static String getMondayDate() {
        String monday = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        monday = DateUtils.format(cal.getTime());
        return monday;
    }

    // 取本周周日日期
    public static String getSundayDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        // 如果是周日直接返回
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            return DateUtils.format(c.getTime());
        }
        c.add(Calendar.DATE, 7 - c.get(Calendar.DAY_OF_WEEK) + 1);
        return DateUtils.format(c.getTime());
    }

    public static String getLastMondayDate() {
        Calendar calendar1 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        String lastBeginDate = DateUtils.format(calendar1.getTime());
        return lastBeginDate;
    }

    public static String getLastSundayDate() {
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar2.get(Calendar.DAY_OF_WEEK) - 1;
        int offset2 = 7 - dayOfWeek;
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastEndDate = DateUtils.format(calendar2.getTime());
        return lastEndDate;
    }

    // 本月月初日期
    public static String getMonthBeginningDate() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.format(cale.getTime());
    }

    // 本月月末
    public static String getMonthEndDate() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return DateUtils.format(cale.getTime());
    }

    // 上月月初日期
    public static String getLastMonthBeginningDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.format(calendar.getTime());
    }

    // 上月月末
    public static String getLastMonthEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return DateUtils.format(calendar.getTime());
    }

    public static Integer getAge(String nowDate, String birthday) throws Exception {
        return getAgeByBirth(birthday,nowDate,DateUtils.DATE_PATTERN);
    }


//	public static void main(String[] args) throws Exception {
//		Date d = DateUtils.parse("2020-01-01");
//		Calendar cale = Calendar.getInstance();
//		cale.setTime(d);
//        cale.add(Calendar.MONTH, 0);
//        cale.set(Calendar.DAY_OF_MONTH, 1);
//        System.out.println(DateUtils.format(cale.getTime()));
//
//
//        cale.add(Calendar.MONTH, 1);
//        cale.set(Calendar.DAY_OF_MONTH, 0);
//        System.out.println(DateUtils.format(cale.getTime()));
//	}


    /**
     * 获取当前日期是本月的第几周第几天
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static int getWeek(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        System.out.println("第" + week + "周");
        //第几天，从周一开始
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println("第" + day + "天");
        return week;
    }

    /**
     * 获取当前日期是本月的第几周第几天
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static int getDay(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        System.out.println("第" + week + "周");
        //第几天，从周一开始
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println("第" + day + "天");
        return day;
    }

    /**
     * 获取当前日期所在周的周一日期
     *
     * @param str
     * @throws ParseException
     */
    public static String convertWeekByDateMonday(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Date time = sdf.parse(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得传入日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得传入日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给传入日期减去星期几与一个星期第一天的差值
        String Monday = sdf.format(cal.getTime());

        cal.add(Calendar.DATE, 6);
        String Sunday = sdf.format(cal.getTime());
        return Monday;
    }

    /**
     * 获取当前日期所在周的周日日期
     *
     * @param str
     * @throws ParseException
     */
    public static String convertWeekByDateSunday(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Date time = sdf.parse(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得传入日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得传入日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给传入日期减去星期几与一个星期第一天的差值
        String Monday = sdf.format(cal.getTime());

        cal.add(Calendar.DATE, 6);
        String Sunday = sdf.format(cal.getTime());
        return Sunday;
    }

    /**
     * 获取当前日期的月份
     *
     * @param time
     * @return
     */
    public static int queryMonthByDate(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int month = cal.get(Calendar.MONTH) + 1;//获取月份，因bai为从0开始的，所以要加1
        return month;
    }

    /**
     * 获取当前日期的日期
     *
     * @param time
     * @return
     */
    public static int queryDayByDate(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int day = cal.get(Calendar.DAY_OF_MONTH);//获取天
        return day;
    }

    /**
     * 获取当前日期的年份
     *
     * @param time
     * @return
     */
    public static int queryYearByDate(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int year = cal.get(Calendar.YEAR);//获取年份
        return year;
    }


    /**
     * 获取当前时间的前后月数与天数的时间
     *
     * @param date
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getTimeBeforeOrAfter(Date date, int year, int month, int day) {
        if (null==date) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(calendar.getTime());
    }


    public static String getTimeBeforeOrAfter(Date date, int year, int month, int day,int hour,int minute,int seconds) {
        if (null==date) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, seconds);

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        return sdf.format(calendar.getTimeInMillis());
    }



    /**
     * 获取两个日期的分钟差
     */
    public static long getMincha(Date begin, Date end) {
        long haoMiaoCha = getHaoMiaoCha(begin, end);
        return haoMiaoCha / 1000 / 60;
    }

    /**
     * 日期相加减
     *
     * @param time 时间字符串 yyyy-MM-dd HH:mm:ss
     * @param num  加的数，-num就是减去
     * @return 减去相应的数量的年的日期
     * @throws ParseException
     */
    public static Date yearAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     * 判断当前时间是否在start和end之间
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isNowBetweenDate(Date start, Date end) {
        Date now = new Date();
        if (now.before(end) && now.after(start)) {
            return true;
        }
        return false;
    }

    /**
     * 获取年龄
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "生日日期不合法");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一

            }
        }
        return age;
    }


    /**
     * 获取当月的 天数
      */
     public static int getCurrentMonthDay(){
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     * 获取当前日期周日期
     * @return
     */
    public static String getWeek() {
        String week = "";
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            week = "周日";
        } else if (weekday == 2) {
            week = "周一";
        } else if (weekday == 3) {
            week = "周二";
        } else if (weekday == 4) {
            week = "周三";
        } else if (weekday == 5) {
            week = "周四";
        } else if (weekday == 6) {
            week = "周五";
        } else if (weekday == 7) {
            week = "周六";
        }
        return week;
    }

    /**
     * 获取当前日期所在月份天数
     * @param date
     * @return
     */
    public static Integer getDaysNum(String date){
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = 1;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日期相加减
     *
     * @param time 时间字符串 yyyy-MM-dd HH:mm:ss
     * @param num  加的数，-num就是减去
     * @return 减去相应的数量的月的日期
     * @throws ParseException
     */
    public static Date monthAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     * @Description: 获取当前时间戳
     * @return: long 当前时间戳
     * @Author: zhangrt
     * @Date: 2021/4/2 10:03
     */
    public static long getTimestamp(){
        return new Date().getTime();
    }

    /**
     * @Description: 获取当前日期 yyyy-MM-dd
     * @return: java.lang.String
     * @Author: zhangrt
     * @Date: 2021/4/7 09:26
     */
    public static String getCurrentDateString(){
        return format(new Date());
    }

    /**
     * @Description: 获取当前时间 yyyy-MM-dd HH:mm:ss
     * @return: java.lang.String
     * @Author: zhangrt
     * @Date: 2021/4/7 09:26
     */
    public static String getCurrentDateTimeString(){
        return format(new Date(),DATE_TIME_PATTERN);
    }

    /**
     * @Description: 获取当前时间 yyyy-MM-dd HH:mm:ss.SSS
     * @return: java.lang.String
     * @Author: zhangrt
     * @Date: 2021/4/7 09:26
     */
    public static String getCurrentAllDateTimeString(){
        return format(new Date(),DEFAULT_ALL_DATE_FORMAT);
    }


    /**
     * 获取两个日期之前的所有日期列表
     * @param dStart
     * @param dEnd
     * @return
     */
    public static List<String> findDates(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

        List dateList = new ArrayList();
        dateList.add(sdf.format(dStart));
        while (dEnd.after(cStart.getTime())) {
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(sdf.format(cStart.getTime()));
        }
        return dateList;
    }


    public static int getAgeByBirth(String birthdayStr,String endDateStr,String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date birthday = sdf.parse(birthdayStr);
        Date endDate = sdf.parse(endDateStr);

        return DateUtil.age(birthday,endDate);
/*
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthday);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;*/
    }

    public static String getNextDateStr(int days) throws ParseException {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sf.format(date);
        Calendar cal = Calendar.getInstance();

        cal.setTime(sf.parse(nowDate));

        cal.add(Calendar.DAY_OF_YEAR, + days);

        return sf.format(cal.getTime());
    }

    public static long getBetweenDays(String dStart, String dEnd) throws ParseException {
        Date bir = DateUtils.parse(dStart, DateUtils.DATE_PATTERN);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bir);
        long time1 = cal.getTimeInMillis();
        Date effictiveDate = DateUtils.parse(dEnd, DateUtils.DATE_PATTERN);
        cal.setTime(effictiveDate);
        long time2 = cal.getTimeInMillis();
        return (time2 - time1) / (1000 * 3600 * 24);
    }

    public static long getBetweenYears(String dStart, String dEnd) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(dEnd));
        aft.setTime(sdf.parse(dStart));
        int year = bef.get(Calendar.YEAR) - aft.get(Calendar.YEAR);
        return year;
    }

    public static String addDays(String dateString, int addDay) {
        Date date = parse(dateString);
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (addDay != 0){
                cal.add(Calendar.DAY_OF_YEAR, addDay);
            }
            return format(cal.getTime());
        } else {
            return null;
        }
    }

    /**
     * @Author xiongfeng
     * @Description //判断某个日期是否为生日  yyyy-MM-dd
     * @Date 2021/4/28
     * @Param
     * @Return
     **/
    public static boolean isBirthday(String date, String birthday) {
        try {
            //int ageNow = getAge(date,birthday);
            //String oneDayBefore = getTimeBeforeOrAfter(parse(date),0,0,-1);
            //int ageBefore = getAge(oneDayBefore,birthday);
            //return ageNow > ageBefore;
            return birthday.substring(5,10).equals(date.substring(5,10));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取某一个时间点N年后的日期
     * @param startDate 时间点
     * @param years N年
     * @date 2021/5/8
     * @author panqiang
     */
    public static String getDateYears(String startDate,int years){
        Date date = parse(startDate);
        Calendar bef = Calendar.getInstance();
        bef.setTime(date);
        int year = bef.get(Calendar.YEAR) + years;
        bef.set(Calendar.YEAR,year);
        return format(bef.getTime());
    }

    public static String getNextDateTimeStr(String dateTime,int days) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(DATE_TIME_PATTERN);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sf.parse(dateTime));
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.add(Calendar.DATE, 1);
        return sf.format(cal.getTime());
    }


    public static Integer getRemainSecondsOneDay(Date currentDate) {
        Calendar midnight=Calendar.getInstance();
        midnight.setTime(currentDate);
        midnight.add(midnight.DAY_OF_MONTH,1);
        midnight.set(midnight.HOUR_OF_DAY,0);
        midnight.set(midnight.MINUTE,0);
        midnight.set(midnight.SECOND,0);
        midnight.set(midnight.MILLISECOND,0);
        Integer seconds=(int)((midnight.getTime().getTime()-currentDate.getTime())/1000);
        return seconds;
    }
}

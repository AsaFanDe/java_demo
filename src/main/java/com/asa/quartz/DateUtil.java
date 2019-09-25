/**
 * Copyright (c) 2012-2020 wadata, Inc. All Rights Reserved.
 */

package com.asa.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * 日期工具类
 * 
 * @author
 * @version 1.0
 * @history
 */
@Slf4j
public class DateUtil {

	/** 定义一天的毫妙数 */
	public static final long MILLSECOND_OF_DAY = 86400000;

	/**
	 * 得到当前月份的周期开始日期
	 * 
	 * @param currentDate 当前日期
	 * @return 当前月份的周期开始日期
	 */
	public static Date getCurBeginCycleDate(Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		String year = "" + calendar.get(Calendar.YEAR);
		String month = calendar.get(Calendar.MONTH) + 1 + "";
		if (month.length() < 2) {
			month = "0" + month;
		}
		String dateStr = year + "-" + month + "-01 00:00:00";
		return DateUtil.parser(dateStr);
	}

	/**
	 * 取得当前周期的周期结束日期
	 * 
	 * @param currentDate 当前日期
	 * @return 当前周期的周期结束日期
	 */
	public static Date getCurEndCycleDate(Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		String year = "" + calendar.get(Calendar.YEAR);
		String month = calendar.get(Calendar.MONTH) + 2 + "";
		if (month.length() < 2) {
			month = "0" + month;
		}
		String dateStr = year + "-" + month + "-01 23:59:59";
		calendar.setTime(DateUtil.parser(dateStr));
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获得日期的年或月或日
	 * 
	 * @param date
	 * @param field
	 * @return
	 */
	public static long getDateField(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long res = cal.get(field);
		return field == Calendar.MONTH ? res + 1 : res;
	}

	/**
	 * 获取开始和结束日期之间的间隔日期
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param roundingMode 舍入方式 见 BigDecimal的定义
	 * @return 相隔的日期数
	 */
	public static long getDaysBetweenDate(Date startDate, Date endDate, int roundingMode) {
		BigDecimal bStart = new BigDecimal(startDate.getTime());
		BigDecimal bEnd = new BigDecimal(endDate.getTime());
		BigDecimal bUnit = new BigDecimal(DateUtil.MILLSECOND_OF_DAY);
		return bEnd.subtract(bStart).divide(bUnit, roundingMode).longValue();
	}

	/**
	 * 获取开始和结束日期之间的间隔日期
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 相隔的日期数
	 */
	public static long getDaysBetweenDateWithoutTime(Date startDate, Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(startDate);
		cal2.setTime(endDate);

		cal1.clear(Calendar.MILLISECOND);
		cal1.clear(Calendar.SECOND);
		cal1.clear(Calendar.MINUTE);
		cal1.clear(Calendar.HOUR_OF_DAY);

		cal2.clear(Calendar.MILLISECOND);
		cal2.clear(Calendar.SECOND);
		cal2.clear(Calendar.MINUTE);
		cal2.clear(Calendar.HOUR_OF_DAY);

		return (cal2.getTime().getTime() - cal1.getTime().getTime()) / DateUtil.MILLSECOND_OF_DAY;
	}

	/**
	 * 获取两个日期之间相差的月份数
	 * 
	 * @param cal1 开始日期
	 * @param cal2 结束日期
	 * @param flag false 为全月舍
	 * @return 返回的月份数
	 */
	public static long getMonthsBetweenDate(Calendar cal1, Calendar cal2, boolean flag) {
		long month = 0L;
		while (cal1.before(cal2)) {
			cal1.add(Calendar.MONTH, 1);
			month++;
			if (flag) {
				if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_MONTH) > cal2.get(Calendar.DAY_OF_MONTH)) {
					month--;
					break;
				}

				if (cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
					month--;
					break;
				}
			}
		}
		// 对12月份单独处理
		if (cal1.get(Calendar.MONTH) == 0 && cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH)) {
			month--;
		}
		return month;
	}

	/**
	 * 获取两个日期之间相差的月份数
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param flag false 为全月舍
	 * @return 返回的月份数
	 */
	public static long getMonthsBetweenDate(Date startDate, Date endDate, boolean flag) {
		Calendar cal1 = Calendar.getInstance();

		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		if (endDate.before(startDate)) {
			cal1.setTime(endDate);
			cal2.setTime(startDate);
		}

		cal1.clear(Calendar.MILLISECOND);
		cal1.clear(Calendar.SECOND);
		cal1.clear(Calendar.MINUTE);
		cal1.clear(Calendar.HOUR_OF_DAY);

		cal2.clear(Calendar.MILLISECOND);
		cal2.clear(Calendar.SECOND);
		cal2.clear(Calendar.MINUTE);
		cal2.clear(Calendar.HOUR_OF_DAY);

		return DateUtil.getMonthsBetweenDate(cal1, cal2, flag);

	}

	/**
	 * 得到下nextCycle周期的月份
	 * 
	 * @param currentDate 当前日期
	 * @param nextCycle 下nextCycle周期
	 * @return 下nextCycle周期
	 */
	public static Date getNextCycleDate(Date currentDate, long nextCycle) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		String year = "" + calendar.get(Calendar.YEAR);
		nextCycle++;
		String month = calendar.get(Calendar.MONTH) + nextCycle + "";
		if (month.length() < 2) {
            month = "0" + month;
        }
		String dateStr = year + "-" + month + "-01 00:00:00";
		return DateUtil.parser(dateStr);
	}

	/**
	 * 获得明天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTomorrowDate(Date date) {
		if (date == null) { return null; }
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得指定日期几天后的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getAfterDate(Date date, int day) {
		if (date == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);
			return cal.getTime();
		}
	}
	/**
	 * 获得指定日期几天前的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getbeforeDay(Date date , int day) {
		if (date == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);
			return cal.getTime();
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @param target 符合格式的字符串
	 * @return 格式后的日期
	 */
	public static Date parser(Date target) {
		if (null == target) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(target);
		calendar.clear(Calendar.MILLISECOND);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.HOUR_OF_DAY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sdf.format(calendar.getTime()));
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 格式化日期
	 *
	 * @param testDate 符合格式的字符串
	 * @return 格式后的日期
	 */
	public static String parserDate2String(Date testDate) {
		if (testDate == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.format(testDate);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 格式化日期
	 * 
	 * @param strDate 符合格式的字符串
	 * @return 格式后的日期
	 */
	public static Date parser(String strDate) {
		if (ObjectUtils.isEmpty(strDate)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * date to string ,默认 格式 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String parserDateToDefaultString(Date date) {
		if (date == null) { return null; }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * date to string ,默认 格式 yyyy-MM-dd hh:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String parserDateToDefault2String(Date date) {
		if (date == null) { return null; }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String parserDateToChineseString(Date date) {
		if (date == null) { return null; }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * date to string ,自定义格式
	 * 
	 * @param date
	 * @return
	 */
	public static String parserDateToPatternString(Date date, String pattern) {
		if (date == null) { return null; }
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @param strDate 符合格式的字符串
	 * @return 格式后的日期
	 */
	public static Date parserTo(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @功能 计算当前日期某年的第几周
	 * @return interger
	 * @throws ParseException
	 */
	public static int getWeekNumOfYear() {
		Calendar calendar = Calendar.getInstance();
		int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/**
	 * @功能 计算指定日期某年的第几周
	 * @return interger
	 * @throws ParseException
	 */
	public static int getWeekNumOfYearDay(String strDate) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = format.parse(strDate);
		calendar.setTime(curDate);
		int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/**
	 * @功能 计算某年某周的开始日期
	 * @return interger
	 * @throws ParseException
	 */
	public static String getYearWeekFirstDay(int yearNum, int weekNum) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * @功能 计算某年某周的结束日期
	 * @return interger
	 * @throws ParseException
	 */
	public static String getYearWeekEndDay(int yearNum, int weekNum) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * 取得指定时间的给定格式()
	 * @return String
	 * @throws ParseException
	 */
	public static String SetDateFormat(String myDate, String strFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String sDate = sdf.format(sdf.parse(myDate));
		return sDate;
	}

	/**
	 * 计算从出生时间到指定时间的年龄 年龄=当前年份-出生年份-if((当前日期>=出生日期),0,1)）
	 * 
	 * @param birthday 出生时间
	 * @param endDate 计算的终止时间
	 * @return int
	 */
	@SuppressWarnings("unused")
	public static int getAge(Date birthday, Date endDate) {
		boolean endDateDYBirthdayMonthAndDay = true;
		if (getDateField(endDate, Calendar.MONTH) > getDateField(birthday, Calendar.MONTH)) {
			endDateDYBirthdayMonthAndDay = true;
		} else if (getDateField(endDate, Calendar.MONTH) == getDateField(birthday, Calendar.MONTH)) {

			if (getDateField(endDate, Calendar.DAY_OF_MONTH) >= getDateField(birthday, Calendar.DAY_OF_MONTH)) {
				endDateDYBirthdayMonthAndDay = true;
			} else {
				endDateDYBirthdayMonthAndDay = false;
			}
		} else if (getDateField(endDate, Calendar.MONTH) < getDateField(birthday, Calendar.MONTH)) {
			endDateDYBirthdayMonthAndDay = false;
		}

		int age = new Long((getDateField(endDate, Calendar.YEAR) - getDateField(birthday, Calendar.YEAR)) - (endDateDYBirthdayMonthAndDay == true ? 0 : 1)).intValue();
		return age;
	}

	/**
	 * 获取周岁年龄
	 * 
	 * @param birthDay 出生日期
	 * @param endDate 终止日期
	 * @return Long 周岁年龄
	 */
	public static Long getYearAge(Date birthDay, Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		if (cal.before(birthDay)) { throw new IllegalArgumentException("参数异常：结束日期大于出生日期!"); }

		int yearEnd = cal.get(Calendar.YEAR);
		int monthEnd = cal.get(Calendar.MONTH);
		int dayOfMonthEnd = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		long yearAge = yearEnd - yearBirth;
		if (monthEnd <= monthBirth) {
			if (monthEnd == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthEnd < dayOfMonthBirth) {
					yearAge--;
				}
			} else {
				// monthNow>monthBirth
				yearAge--;
			}
		}

		return yearAge;

	}

	// 根据年龄，计算生日（比如，得到年龄在6岁内的人的出生日期开始时间）
	public static String getBirthdayByAge(int age) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -age);
		Date birthdate = cal.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(birthdate);
	}

	public static String getBirthdayByAge(String age) {
		return getBirthdayByAge(Integer.parseInt(age));
	}

	/** 计算两日期间隔的周数与天数（计算孕周） */
	public static Map<String, Integer> countWeeksAndDays(Date beginDate, Date endDate) {
		Map<String, Integer> res = new HashMap<String, Integer>();
		if (beginDate != null && endDate != null) {
			int days = new Long(getDaysBetweenDateWithoutTime(beginDate, endDate) + 1).intValue();
			int weeks = new Double(Math.floor(days / 7)).intValue();
			int day = (days - 1) % 7;
			res.put("totalDays", days);//总天数
			res.put("weeks", day == 6 ? weeks - 1 : weeks);//周数（取floor）。正好6天的话，周数需要-1
			res.put("days", day);//天数（0-6）
		}
		return res;
	}

	//	public static void main(String[] args) {
	//		// DateUtil test = new DateUtil();
	//		// System.out.print(test.getAge(test.parserTo("2008-12-25"),
	//		// test.parserTo("2009-12-26")));
	//	}
	
	/**
	 * 新增格式自定义构造方法
	 * @param strDate 符合格式的字符串
	 * @return 格式后的日期
	 */
	public static Date stringToDate(String strDate,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 修改时间
	 * @param target 目标时间
	 * @param type   修改类型 年 月 日 时 分 秒
	 * @param i      修改数量  正数为+ 负数为减
	 * @return
	 */
	public static Date modifyTime(Date target, int type, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(target);
		calendar.add(type, i);
		return calendar.getTime();
	}

	/***********************************************************************************/

	public static final String yyMd = "yyyy-MM-dd";
	public static final String yyMd2 = "yyyyMMdd";
	public static final String yyMdHms = "yyyy-MM-dd HH:mm:ss";
	public static final String yyMdHms1 = "yyyy年MM月dd日 HH:mm:ss";

	/**
	 * 格式化时间To String
	 * @param target
	 * @param patter
	 * @return
	 */
	public static String format2String(Date target, String patter) {
		if (target == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(patter);
		try {
			return sdf.format(target);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解析String To Date
	 * @param target
	 * @param patter
	 * @return
	 */
	public static Date parse2Date(String target, String patter) {
		if (null == target || "".equals(target)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(patter);
		try {
			return sdf.parse(target);
		} catch (Exception e) {
			throw new RuntimeException("字符串转时间时，Date格式有误");
		}
	}

	public static Date changeDays(Date target, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(target);
		calendar.add(calendar.DATE,i); //把日期往后增加一天,整数  往后推,负数往前移动
		return calendar.getTime(); //这个时间就是日期往后推一天的结果
	}

	/**
	 * 生成上周一的时间
	 * @param target
	 * @return
	 */
	public static Date getLastWeekMon(Date target) {
		LocalDate localDate = dateToLocalDate(target);
		return localDateToDate(localDate.minusWeeks(1).with(DayOfWeek.MONDAY));
	}

	/**
	 * 获取这周一的时间
	 * @param target
	 * @return
	 */
	public static Date getLastWeekSun(Date target) {
		LocalDate localDate = dateToLocalDate(target);
		return localDateToDate(localDate.with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1));
	}

	/**
	 * 获取其这个月的第一天
	 * @param target
	 * @return
	 */
	public static Date getThisMonthStartDay(Date target) {
		LocalDate localDate = dateToLocalDate(target);
		return localDateToDate(LocalDate.of(localDate.getYear(),localDate.getMonth(),1));
	}

	/**
	 * 获取其这个月的最后一天
	 * @param target
	 * @return
	 */
	public static Date getThisMonthLastDay(Date target) {
		LocalDate localDate = dateToLocalDate(target);
		return localDateToDate(localDate.with(TemporalAdjusters.lastDayOfMonth()));
	}

	/**
	 * 获取其上个月的第一天
	 * @param target
	 * @return
	 */
	public static Date getLastMouthStartDate(Date target) {
		LocalDate localDate = dateToLocalDate(target);
		LocalDate lastMoth = localDate.minusMonths(1);
		return localDateToDate(LocalDate.of(lastMoth.getYear(),lastMoth.getMonth(),1));
	}

	/**
	 * 获取其上个月的最后一天
	 * @param target
	 * @return
	 */
	public static Date getLastMonthLastDay(Date target) {
		LocalDate localDate = dateToLocalDate(target);
		return localDateToDate(localDate.with(TemporalAdjusters.lastDayOfMonth()));
	}


	public static Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate dateToLocalDate(Date target) {
		return target.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static void main(String[] args) {
		log.info(format2String(getLastWeekMon(new Date()), DateUtil.yyMdHms));
	}
}

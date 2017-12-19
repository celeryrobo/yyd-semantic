package com.yyd.semantic.services.impl.festival;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 给定阳历格式的日期返回日期实体
	 * 
	 * @param enDate
	 *            2017-12-18
	 * @return DateEntity
	 */
	public static DateEntity getDateEntityBySolar(String enDate) {
		DateEntity de = new DateEntity();
		de.setCnDate(getCnDate(enDate));
		de.setLunarDate(solarToLunarDate(enDate));
		de.setEnDate(enDate);
		de.setWeekDate(getWeek(enDate));
		return de;
	}

	// public static DateEntity getDateEntityByLunar(String lunarDate) {
	// DateEntity de = new DateEntity();
	// de.setCnDate(getCnDate(enDate));
	// de.setLunarDate(solarToLunarDate(enDate));
	// de.setEnDate(enDate);
	// de.setWeekDate(getWeek(enDate));
	// return de;
	// }
	/**
	 * 给定阳历日期格式返回中文日期格式
	 * 
	 * @param enDate
	 *            2017-12-18
	 * @return 2017年12月18日
	 */
	public static String getCnDate(String enDate) {
		StringBuffer sb = new StringBuffer();
		sb.append(enDate.substring(0, 4));
		sb.append("年");
		sb.append(Integer.parseInt(enDate.substring(5, 7)));
		sb.append("月");
		sb.append(Integer.parseInt(enDate.substring(8, 10)));
		sb.append("日");
		return sb.toString();
	}

	/**
	 * 给定阳历日期格式返回农历日期
	 * 
	 * @param enDate
	 *            2017-12-18
	 * @return 丁酉年十一月初一
	 */
	public static String solarToLunarDate(String enDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lunarDate = "";
		try {
			lunarDate = new Lunar(sdf.parse(enDate)).getLunarDateString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lunarDate;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String lunarToSolarDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer solarDate = new StringBuffer();
		Calendar cl = Calendar.getInstance();
		LunarDate ld = new LunarDate();
		Date solar;
		try {
			solar = sdf.parse(date);
			cl.setTime(solar);
			ld.isleap = new Lunar(solar).isLeapYear();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ld.lunarYear = cl.get(Calendar.YEAR);
		ld.lunarMonth = cl.get(Calendar.MONTH);
		ld.lunarDay = cl.get(Calendar.DAY_OF_MONTH);

		SolarDate sd = LunarSolarConverter.LunarToSolar(ld);
		solarDate.append(sd.solarYear).append("年").append(String.valueOf(sd.solarMonth)).append("月")
				.append(String.valueOf(sd.solarDay)).append("日");

		return "";

	}

	/**
	 * 给定阳历日期格式返回星期几
	 * 
	 * @param enDate
	 *            2017-12-18
	 * @return 星期一
	 */
	public static String getWeek(String enDate) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(enDate);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static int getCurrentYear() {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		return cl.get(Calendar.YEAR);
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return date1和date2之间间隔的天数
	 */
	public static int getDifferentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) {
			// 同一年
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
					// 闰年
					timeDistance += 366;
				} else {
					// 不是闰年
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else {
			// 不同年
			// System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2 - day1;
		}
	}

	/**
	 * 指定阳历格式的农历节日的日期
	 * 
	 * @param date
	 *            中秋节：2017-08-15
	 * @return 返回阳历日期格式的阳历日期 2017-10-04
	 */
	public static String getEnDate(String date) {
		// 节日的阴历时间
		int feYear = Integer.parseInt(date.substring(0, 4));
		int feMonth = Integer.parseInt(date.substring(5, 7));
		int feDay = Integer.parseInt(date.substring(8, 10));
		LunarDate lunar = new LunarDate();
		// if (feYear % 4 == 0 && feYear % 100 != 0 || feYear % 400 == 0) {
		// // 闰年
		// lunar.isleap = true;
		// } else {
		// // 不是闰年
		// lunar.isleap = false;
		// }
		lunar.isleap = false;
		lunar.lunarYear = feYear;
		lunar.lunarMonth = feMonth;
		lunar.lunarDay = feDay;
		// 节日的阳历时间
		SolarDate solar = LunarSolarConverter.LunarToSolar(lunar);
		String year = String.valueOf(solar.solarYear);
		String month = String.valueOf(solar.solarMonth);
		String day = String.valueOf(solar.solarDay);

		if (solar.solarMonth < 10) {
			month = "0" + String.valueOf(solar.solarMonth);
		}
		if (solar.solarDay < 10) {
			day = "0" + String.valueOf(solar.solarDay);
		}
		return year + "-" + month + "-" + day;
	}

	/**
	 * 给定阳历节日日期，判断该阳历节日是否已经过完
	 * 
	 * @param date
	 * @return 过完为真，否则为假
	 */
	public static boolean isFestivalPassed(String date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		boolean isPassed = false;
		int m = cl.get(Calendar.MONTH);
		int d = cl.get(Calendar.DATE);
		int mm = Integer.parseInt(date.substring(0, 2));
		int dd = Integer.parseInt(date.substring(3, 5));
		if (mm == m) {
			if (dd < d) {
				isPassed = true;
			}
		} else if (mm < m) {
			isPassed = true;
		}
		return isPassed;
	}

	/**
	 * 给定阴历节日日期，判断该阴历节日是否已经过完
	 * 
	 * @param date
	 * @return 过完返回真，否则为假
	 */
	public static boolean isLunarFestivalPassed(String date) {
		boolean isPassed = false;
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		int feMonth = Integer.parseInt(date.substring(0, 2));
		int feDay = Integer.parseInt(date.substring(3, 5));
		SolarDate solar = new SolarDate();
		solar.solarYear = cl.get(Calendar.YEAR);
		solar.solarMonth = cl.get(Calendar.MONTH);
		solar.solarDay = cl.get(Calendar.DAY_OF_MONTH);
		// 现在阴历时间
		LunarDate lunar = LunarSolarConverter.SolarToLunar(solar);
		if (feMonth == lunar.lunarMonth) {
			if (feDay < lunar.lunarDay) {
				isPassed = true;
			}
		} else if (feMonth < lunar.lunarMonth) {
			isPassed = true;
		}
		return isPassed;
	}

	/**
	 * 放回当前阴历年份
	 * 
	 * @return
	 */
	public static int getCurrentLunarYear() {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		SolarDate solar = new SolarDate();
		solar.solarYear = cl.get(Calendar.YEAR);
		solar.solarMonth = cl.get(Calendar.MONTH);
		solar.solarDay = cl.get(Calendar.DAY_OF_MONTH);
		// 现在阴历时间
		return LunarSolarConverter.SolarToLunar(solar).lunarYear;
	}
}

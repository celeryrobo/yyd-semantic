package com.yyd.semantic.services.impl.festival;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static DateEntity getDateEntityBySolar(String enDate) {
		DateEntity de = new DateEntity();
		de.setCnDate(getCnDate(enDate));
		de.setLunarDate(solarToLunarDate(enDate));
		de.setEnDate(enDate);
		de.setWeekDate(getWeek(enDate));
		return de;
	}
	
//	public static DateEntity getDateEntityByLunar(String lunarDate) {
//		DateEntity de = new DateEntity();
//		de.setCnDate(getCnDate(enDate));
//		de.setLunarDate(solarToLunarDate(enDate));
//		de.setEnDate(enDate);
//		de.setWeekDate(getWeek(enDate));
//		return de;
//	}

	public static String getCnDate(String date) {
		StringBuffer sb = new StringBuffer();
		sb.append(date.substring(0, 4));
		sb.append("年");
		sb.append(Integer.parseInt(date.substring(5, 7)));
		sb.append("月");
		sb.append(Integer.parseInt(date.substring(8, 10)));
		sb.append("日");
		return sb.toString();
	}

	public static String solarToLunarDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lunarDate = "";
		try {
			lunarDate = new Lunar(sdf.parse(date)).getLunarDateString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lunarDate;
	}

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

	public static String getWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
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

	public static int getDifferentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			// System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2 - day1;
		}
	}

	public static boolean isFestivalPassed(String mmdd) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		int m = cl.get(Calendar.MONTH);
		int d = cl.get(Calendar.DATE);
		String mmStr = mmdd.substring(0, 2);
		String ddStr = mmdd.substring(3, 5);
		int mm = Integer.parseInt(mmStr);
		int dd = Integer.parseInt(ddStr);
		if (mm == m) {
			if (dd == d) {
				// 节日没过，报今年的
				return false;
			} else if (dd < d) {
				// 节日已过，报明年的
				return true;

			} else {
				// 节日没过，报今年的
				return false;
			}
		} else if (mm < m) {
			// 节日已过，报明年的
			return true;
		} else {
			// 节日没过，报今年的
			return false;
		}
	}
}

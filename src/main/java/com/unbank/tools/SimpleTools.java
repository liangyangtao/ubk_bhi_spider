package com.unbank.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimpleTools {
	public static String dateToString(Date data, String format) {
		if (data == null) {
			data = new Date();
		}
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(format);
		String ctime = formatter.format(data);
		return ctime;
	}

	public static String getyyyyMMddTimeString(Date date, int num) {
		date = getMyDate(date, num);
		return dateToString(date, "yyyy-MM-dd");
	}

	public static Date getMyDate(Date date, int num) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DATE, num);
		date = calendar.getTime();
		date = stringToDate(dateToString(date, "yyyy-MM-dd"));
		return date;
	}

	public static Date stringToDate(String time) {
		SimpleDateFormat formatter = null;
		Date ctime = null;
		try {
			String format = "yyyy-MM-dd";
			formatter = new SimpleDateFormat(format);
			ctime = formatter.parse(time);
		} catch (ParseException e) {
			ctime = new Date();
		}
		return ctime;
	}

}

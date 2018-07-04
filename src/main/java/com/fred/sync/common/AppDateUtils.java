package com.fred.sync.common;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.commons.lang3.time.DateUtils;

public class AppDateUtils {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

	public static Date parseDate(String date) throws ParseException {
		return new Date(DateUtils.parseDate(date, DATE_FORMAT).getTime());
	}

	public static Date parseDate(String date, String format) throws ParseException {
		return new Date(DateUtils.parseDate(date, format).getTime());
	}

	public static String dateString(java.util.Date date) {
		return SIMPLE_DATE_FORMAT.format(date);
	}

	public static LocalDate getLocalDate(String date) throws ParseException {
		return DateUtils.parseDate(date, DATE_FORMAT).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}

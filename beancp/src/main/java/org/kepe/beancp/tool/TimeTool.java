package org.kepe.beancp.tool;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeTool {
	public static final DateTimeFormatter DATEFORMAT8;
	public static final DateTimeFormatter DATEFORMAT10;
	public static final DateTimeFormatter DATETIMEFORMAT;
	public static final DateTimeFormatter DEFALUTFORMAT;
	public static final DateTimeFormatter YYYYMMDD;
	public static final DateTimeFormatter YYYY_MM_DD;
	public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS;
	public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_SSS;

	static {
		DATEFORMAT8 = getFormatter("yyyyMMdd");
		DATEFORMAT10 = getFormatter("yyyy-MM-dd");
		DATETIMEFORMAT = getFormatter("yyyy-MM-dd HH:mm:ss");
		DEFALUTFORMAT = getFormatter("yyyy-MM-dd HH:mm:ss.SSS");
		YYYYMMDD = DATEFORMAT8;
		YYYY_MM_DD = DATEFORMAT10;
		YYYY_MM_DD_HH_MM_SS = DATETIMEFORMAT;
		YYYY_MM_DD_HH_MM_SS_SSS = DEFALUTFORMAT;
	}

	public static Date instant2Date(Instant instant) {
		return Date.from(instant);
	}

	public static Instant date2Instant(Date date) {
		return date.toInstant();
	}

	public static LocalDateTime date2LocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static LocalDate date2LocalDate(Date date) {
		return date2LocalDateTime(date).toLocalDate();
	}

	public static Date localDateTime2Date(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}

	public static Date milli2Date(long milli) {
		return new Date(milli);
	}

	public static long date2Milli(Date date) {
		return date.getTime();
	}

	public static LocalDateTime milli2LocalDateTime(long milli) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault());
	}

	public static long localDateTime2Milli(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.toEpochMilli();
	}

	public static Instant milli2Instant(long milli) {
		return Instant.ofEpochMilli(milli);
	}

	public static long instant2Milli(Instant instant) {
		return instant.toEpochMilli();
	}

	private static DateTimeFormatter getFormatterByDatestr(String datestr) {
		if (datestr.length() == 8) {
			return DATEFORMAT8;
		} else if (datestr.length() == 10) {
			return DATEFORMAT10;
		} else if (datestr.length() == 19) {
			return DATETIMEFORMAT;
		} else if (datestr.length() == 23) {
			return DEFALUTFORMAT;
		} else {
			return DEFALUTFORMAT;
		}
	}

	public static Date datestr2Date(String datestr) {
		return datestr2Date(datestr, getFormatterByDatestr(datestr));
	}

	public static Date datestr2Date(String datestr, DateTimeFormatter formatter) {
		return Date.from(datestr2Instant(datestr, formatter));
	}

	public static String date2Datestr(Date date) {
		return date2Datestr(date, DEFALUTFORMAT);
	}

	public static String date2Datestr(Date date, DateTimeFormatter formatter) {
		return formatter.format(date.toInstant());
	}

	public static Instant datestr2Instant(String datestr) {
		return datestr2Instant(datestr, getFormatterByDatestr(datestr));
	}

	public static Instant datestr2Instant(String datestr, DateTimeFormatter formatter) {
		try {
			return formatter.parse(datestr, Instant::from);
		} catch (Exception e) {
			return formatter.parse(datestr, LocalDate::from).atStartOfDay(ZoneId.systemDefault()).toInstant();
		}
	}

	public static String instant2Datestr(Instant instant) {
		return instant2Datestr(instant, DEFALUTFORMAT);
	}

	public static String instant2Datestr(Instant instant, DateTimeFormatter formatter) {
		return formatter.format(instant);
	}

	public static LocalDateTime datestr2LocalDateTime(String datestr) {
		return datestr2LocalDateTime(datestr, getFormatterByDatestr(datestr));
	}

	public static LocalDateTime datestr2LocalDateTime(String datestr, DateTimeFormatter formatter) {
		try {
			return LocalDateTime.parse(datestr, formatter);
		} catch (Exception e) {
			return LocalDate.parse(datestr, formatter).atStartOfDay();
		}
	}

	public static String localDateTime2Datestr(LocalDateTime localDateTime) {
		return localDateTime2Datestr(localDateTime, DEFALUTFORMAT);
	}

	public static String localDateTime2Datestr(LocalDateTime localDateTime, DateTimeFormatter formatter) {
		return formatter.format(localDateTime);
	}

	public static DateTimeFormatter getFormatter(String formatterStr) {
		return Dates.getFormatter(formatterStr);
	}

	public static long currentMillis() {
		return System.currentTimeMillis();
	}

	public static String currentDatestr() {
		return localDateTime2Datestr(LocalDateTime.now());
	}

	public static String currentDatestr4ymd() {
		return localDateTime2Datestr(LocalDateTime.now(), DATEFORMAT10);
	}

}

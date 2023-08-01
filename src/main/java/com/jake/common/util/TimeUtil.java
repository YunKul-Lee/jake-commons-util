package com.jake.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ZoneId.systemDefault() 변경 조건</br>
 * VM arguments</br>
 * -Duser.timezone=UTC</br>
 * JVM 구동때 user.timezone 미 지정시 구동 OS의 타임존을 참고함</br>
 * ZoneId.systemDefault() 또한 구동 OS의 타임존을 참고함</br>
 */
public final class TimeUtil {

	public static final ZoneId SYS_ZID = ZoneId.of("UTC");
	public static ZoneOffset SYS = toZoneOffset();

	public static final DateTimeFormatter DATE_ID_FMT = DateTimeFormatter.ofPattern("yyMMdd").withLocale(Locale.ENGLISH)
			.withZone(SYS_ZID);

	public static final DateTimeFormatter HOUR_ID_FMT = DateTimeFormatter.ofPattern("yyMMddHH").withLocale(Locale.ENGLISH)
			.withZone(SYS_ZID);

	public static ZoneOffset toZoneOffset() {
		return SYS_ZID.getRules().getOffset(Instant.now());
	}

	public static LocalDateTime toDateTime(long epochMilli) {
		return toDateTime(epochMilli, SYS_ZID);
	}

	public static LocalDateTime toDateTime(long epochMilli, ZoneId zoneId) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), zoneId);
	}

	public static LocalDateTime toDateTime(Date date) {
		return toDateTime(date, SYS_ZID);
	}

	public static LocalDateTime toDateTime(Date date, ZoneId zoneId) {
		return LocalDateTime.ofInstant(date.toInstant(), zoneId);
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.toInstant(SYS));
	}

	public static boolean isAfter(LocalDateTime datetime1, LocalDateTime datetime2) {
		return datetime2.isAfter(datetime1);
	}

	public static boolean isAfter(Date date1, Date date2) {
		LocalDateTime datetime1 = toDateTime(date1);
		LocalDateTime datetime2 = toDateTime(date2);
		return datetime2.isAfter(datetime1);
	}

	public static boolean isAfter(Date date1, ZoneId zoneId1, Date date2, ZoneId zoneId2) {
		LocalDateTime datetime1 = toDateTime(date1, zoneId1);
		LocalDateTime datetime2 = toDateTime(date2, zoneId2);
		return datetime2.isAfter(datetime1);
	}

	public static int getDayOfWeek(long epochMilli) {
		return getDayOfWeek(epochMilli, SYS_ZID);
	}

	public static int getDayOfWeek(long epochMilli, ZoneId zoneId) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), zoneId);
		DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
		return dayOfWeek.getValue();
	}

	public static int getDateId(long time) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), SYS_ZID);
		int dateId = Integer.parseInt(ZonedDateTime.of(dateTime, SYS_ZID).format(DATE_ID_FMT));

		return dateId;
	}

	public static int getHourId(long time) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), SYS_ZID);
		int hourId = Integer.parseInt(ZonedDateTime.of(dateTime, SYS_ZID).format(HOUR_ID_FMT));

		return hourId;
	}

	public static long truncateMillis(Date date) {
		return truncateMillis(date.getTime());
	}

	public static long truncateMillis(long time) {
		time = (time / 1000L) * 1000L;
		return time;
	}

	public static long getZeroTimeOfSameDay(long timeMillis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMillis);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
}

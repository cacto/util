package me.cacto.util.format;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToString {
	public static String valueOf(Object value, String pattern) {
		if (value == null)
			return null;

		if (pattern == null)
			pattern = "yyyy-MM-dd HH:mm:ss";

		Class<?> type = value.getClass();

		if (type.equals(Object.class)) {
			return value.toString();
		} else if (type.equals(String.class)) {
			return (String) value;
		} else if (type.equals(Boolean.class)) {
			return value.toString().toUpperCase();
		} else if (Number.class.isAssignableFrom(type)) {
			Number number = (Number) value;

			if (type.equals(Byte.class)) {
				return ToString.numberToString(number.byteValue(), pattern);
			} else if (type.equals(Short.class)) {
				return ToString.numberToString(number.shortValue(), pattern);
			} else if (type.equals(Integer.class)) {
				return ToString.numberToString(number.intValue(), pattern);
			} else if (type.equals(Long.class)) {
				return ToString.numberToString(number.longValue(), pattern);
			} else if (type.equals(Float.class)) {
				return ToString.numberToString(number.floatValue(), pattern);
			} else if (type.equals(Double.class)) {
				return ToString.numberToString(number.doubleValue(), pattern);
			} else if (type.equals(BigInteger.class)) {
				return ToString.numberToString(number.doubleValue(), pattern);
			} else if (type.equals(BigDecimal.class)) {
				return ToString.numberToString(number.doubleValue(), pattern);
			} else {
				return number.toString();
			}
		} else if (type.equals(Calendar.class)) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(((Calendar) value).getTime());
		} else if (type.equals(Date.class)) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format((Date) value);
		} else if (type.equals(Timestamp.class)) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format((Timestamp) value);
		} else if (Enum.class.isAssignableFrom(type)) {
			return ((Enum<?>) value).name();
		}

		return value.toString();
	}

	private static String numberToString(Number value, String pattern) {
		if (pattern != null) {
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			return decimalFormat.format(value);
		} else
			return value.toString();
	}
}

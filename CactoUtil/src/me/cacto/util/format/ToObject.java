package me.cacto.util.format;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToObject {
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> T valueOf(Class<? extends T> type, String value, String pattern) throws ParseException {
		if (value == null)
			return null;

		if (type.equals(Object.class)) {
			return (T) value;
		} else if (type.equals(String.class)) {
			return (T) value;
		} else if (type.equals(Boolean.class)) {
			return (T) new Boolean(value.matches("(?i)([1T]|(TRUE))"));
		} else if (Number.class.isAssignableFrom(type)) {
			Double number;

			if (pattern != null) {
				number = new DecimalFormat(pattern).parse(value).doubleValue();
			} else {
				number = new Double(value);
			}

			if (type.equals(Byte.class)) {
				return (T) new Byte(number.byteValue());
			} else if (type.equals(Short.class)) {
				return (T) new Short(number.shortValue());
			} else if (type.equals(Integer.class)) {
				return (T) new Integer(number.intValue());
			} else if (type.equals(Long.class)) {
				return (T) new Long(number.longValue());
			} else if (type.equals(Float.class)) {
				return (T) new Float(number.floatValue());
			} else if (type.equals(Double.class)) {
				return (T) number;
			} else if (type.equals(BigInteger.class)) {
				return (T) new BigDecimal(number.doubleValue()).toBigInteger();
			} else if (type.equals(BigDecimal.class)) {
				return (T) new BigDecimal(number.doubleValue());
			} else {
				throw new ParseException("Not implemented: " + type, 0);
			}
		} else if (type.equals(Calendar.class)) {
			if (pattern == null)
				throw new ParseException("Pattern is null", 0);

			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(value));
			return (T) calendar;
		} else if (type.equals(Date.class)) {
			if (value.trim().isEmpty())
				return null;

			if (pattern == null || pattern.trim().isEmpty())
				throw new ParseException("Pattern is null", 0);

			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return (T) sdf.parse(value);
		} else if (type.equals(Timestamp.class)) {
			return (T) Timestamp.valueOf(value); //yyyy-[M]M-[d]d HH:mm:ss[.f...]
		} else if (Enum.class.isAssignableFrom(type)) {
			try {
				return (T) Enum.valueOf((Class<? extends Enum>) type, value);
			} catch (IllegalArgumentException ex) {
				throw new ParseException(ex.getMessage(), 0);
			}
		}

		return null;
	}

	public static Object valueOf(Class<?> type, Object value) throws ParseException {
		if (value == null)
			return null;

		if (Boolean.class.isAssignableFrom(type)) {
			return ToObject.valueOf(Boolean.class, value.toString(), null);
		}

		String pattern = "yyyy-MM-dd HH:mm:ss";
		if (Timestamp.class.isAssignableFrom(value.getClass())) {
			value = ToString.valueOf(value, pattern);
		} else if (Date.class.isAssignableFrom(value.getClass())) {
			value = ToString.valueOf(value, pattern);
		} else
			pattern = null;

		value = ToObject.valueOf(type, value.toString(), pattern);
		return value;
	}

}

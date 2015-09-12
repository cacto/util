package me.cacto.util.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */

public class FieldUtil {
	private static Map<Class<?>, List<Field>> mapAllFields = new HashMap<>();
	private static ExceptionNotifier exceptionNotifier;

	public static void setExceptionNotifier(ExceptionNotifier exceptionNotifier) {
		FieldUtil.exceptionNotifier = exceptionNotifier;
	}

	public static Field getField(Class<?> type, String name) {
		List<Field> fields = FieldUtil.listFields(type);

		for (Field field : fields) {
			if (!field.getName().equalsIgnoreCase(name))
				continue;

			return field;
		}

		return null;
	}

	public static List<Field> listFields(Class<?> type, Class<? extends Annotation> annotationClass) {
		List<Field> fields = FieldUtil.listFields(type);
		List<Field> resultFields = new ArrayList<Field>();

		for (Field field : fields) {
			if (!field.isAnnotationPresent(annotationClass))
				continue;

			resultFields.add(field);
		}

		return resultFields;
	}

	public static List<Field> listFields(Class<?> type) {
		List<Field> listFields = FieldUtil.mapAllFields.get(type);

		if (listFields != null)
			return listFields;

		listFields = new ArrayList<>();

		FieldUtil.mapAllFields.put(type, listFields);

		do {
			for (Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				listFields.add(field);
			}

			for (Field field : type.getFields()) {
				field.setAccessible(true);
				listFields.add(field);
			}

			if (type.equals(Object.class))
				break;

			type = type.getSuperclass();
		} while (true);

		return listFields;
	}

	public static void set(Class<?> type, String fieldName, Object object, Object value) {
		FieldUtil.set(type, fieldName, object, value, FieldUtil.exceptionNotifier);
	}

	public static void set(Class<?> type, String fieldName, Object object, Object value, ExceptionNotifier exceptionNotifier) {
		FieldUtil.set(FieldUtil.getField(type, fieldName), object, value, exceptionNotifier);
	}

	public static void set(Field field, Object object, Object value) {
		FieldUtil.set(field, object, value, FieldUtil.exceptionNotifier);
	}

	public static void set(Field field, Object object, Object value, ExceptionNotifier exceptionNotifier) {
		try {
			field.set(object, value);
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			if (exceptionNotifier != null)
				exceptionNotifier.exceptionNotifier(ex);
		}
	}

	public static Object get(Class<?> type, String fieldName, Object object) {
		return FieldUtil.get(type, fieldName, object, FieldUtil.exceptionNotifier);
	}

	public static Object get(Class<?> type, String fieldName, Object object, ExceptionNotifier exceptionNotifier) {
		return FieldUtil.get(FieldUtil.getField(type, fieldName), object, exceptionNotifier);
	}

	public static Object get(Field field, Object object) {
		return FieldUtil.get(field, object, FieldUtil.exceptionNotifier);
	}

	public static Object get(Field field, Object object, ExceptionNotifier exceptionNotifier) {
		try {
			return field.get(object);
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			if (exceptionNotifier != null)
				exceptionNotifier.exceptionNotifier(ex);

			return null;
		}
	}

	//	public static String serialize(Object object) {
	//		SortedMap<String, Object> mapSerialize = new TreeMap<>();
	//		List<Field> fields = FieldUtil.listFields(object.getClass());
	//		for (Field field : fields) {
	//
	//			Object value = FieldUtil.get(field, object);
	//			if (value instanceof String)
	//				value = String.format("\"%s\"", value.toString().replace("\"", "\\\""));
	//
	//			mapSerialize.put(field.getName(), value);
	//		}
	//
	//		return mapSerialize.toString();
	//	}
}

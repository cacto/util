package me.cacto.util.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */

public class MethodUtil {
	private static Map<Class<?>, List<Method>> mapAllMethods = new HashMap<>();
	private static ExceptionNotifier exceptionNotifier;

	public static void setExceptionNotifier(ExceptionNotifier exceptionNotifier) {
		MethodUtil.exceptionNotifier = exceptionNotifier;
	}

	public static Method getMethod(Class<?> type, String name, Class<?>... parameters) {
		List<Method> methods = MethodUtil.listMethods(type, name);

		for (Method method : methods) {
			if (Arrays.equals(method.getParameterTypes(), parameters))
				return method;
		}

		if ((parameters == null || parameters.length == 0) && methods.size() > 0)
			return methods.get(0);

		return null;
	}

	public static List<Method> listMethods(Class<?> type, Class<? extends Annotation> annotationClass) {
		List<Method> methods = MethodUtil.listMethods(type);
		List<Method> resultMethods = new ArrayList<Method>();

		for (Method method : methods) {
			if (!method.isAnnotationPresent(annotationClass))
				continue;

			resultMethods.add(method);
		}

		return resultMethods;
	}

	public static List<Method> listMethods(Class<?> type, String name) {
		List<Method> methods = MethodUtil.listMethods(type);
		List<Method> resultMethods = new ArrayList<Method>();

		for (Method method : methods) {
			if (!method.getName().equalsIgnoreCase(name))
				continue;

			resultMethods.add(method);
		}

		return resultMethods;
	}

	public static List<Method> listMethods(Class<?> type) {
		List<Method> listMethods = MethodUtil.mapAllMethods.get(type);

		if (listMethods != null)
			return listMethods;

		listMethods = new ArrayList<>();

		MethodUtil.mapAllMethods.put(type, listMethods);

		do {
			for (Method method : type.getDeclaredMethods()) {
				method.setAccessible(true);
				listMethods.add(method);
			}

			for (Method method : type.getMethods()) {
				method.setAccessible(true);
				listMethods.add(method);
			}

			if (type.equals(Object.class))
				break;

			type = type.getSuperclass();
		} while (true);

		return listMethods;
	}

	public static Object invoke(Method method, Object object, Object... args) {
		return MethodUtil.invoke(method, object, MethodUtil.exceptionNotifier, args);
	}

	public static Object invoke(Method method, Object object, ExceptionNotifier exceptionNotifier, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			if (exceptionNotifier != null)
				exceptionNotifier.exceptionNotifier(ex);

			return null;
		}
	}
}

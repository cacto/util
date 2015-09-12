package me.cacto.util.lang;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */

public class ClassUtil {
	private static ExceptionNotifier exceptionNotifier;
	public static void setExceptionNotifier(ExceptionNotifier exceptionNotifier) {
		ClassUtil.exceptionNotifier = exceptionNotifier;
	}

	public static <T> T newInstance(Class<? extends T> type) {
		return ClassUtil.newInstance(type, ClassUtil.exceptionNotifier);
	}

	public static <T> T newInstance(Class<? extends T> type, ExceptionNotifier exceptionNotifier) {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			if (exceptionNotifier != null)
				exceptionNotifier.exceptionNotifier(ex);

			return null;
		}
	}
}

package me.cacto.util.lang;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */

public interface ExceptionNotifier {
	public void exceptionNotifier(Throwable e) throws RuntimeException;
}

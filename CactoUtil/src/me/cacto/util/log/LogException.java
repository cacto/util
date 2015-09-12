package me.cacto.util.log;

public class LogException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LogException(Exception ex) {
		super(ex);
	}
}

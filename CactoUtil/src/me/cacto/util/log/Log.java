package me.cacto.util.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	private final String application;
	private final String user;
	private final LogFile logFile;
	private String style = "%line\t(%name)\t[%user]\t%timestamp\t%level\t\"%text\";\n";

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyle() {
		return this.style;
	}

	public Log(String application, String user, LogFile logFile) {
		this.application = application;
		this.user = user;
		this.logFile = logFile;
	}

	public void auditory(String user, Object object) {
		this.write(false, user, LogLevel.AUDITORY, object);
	}

	public void emergency(String user, Object object) {
		this.write(false, user, LogLevel.EMERGENCY, object);
	}

	public void alert(String user, Object object) {
		this.write(false, user, LogLevel.ALERT, object);
	}

	public void critical(String user, Object object) {
		this.write(false, user, LogLevel.CRITICAL, object);
	}

	public void error(String user, Object object) {
		this.write(false, user, LogLevel.ERROR, object);
	}

	public void warning(String user, Object object) {
		this.write(false, user, LogLevel.WARNING, object);
	}

	public void notice(String user, Object object) {
		this.write(false, user, LogLevel.NOTICE, object);
	}

	public void information(String user, Object object) {
		this.write(false, user, LogLevel.INFORMATION, object);
	}

	public void debug(String user, Object object) {
		this.write(false, user, LogLevel.DEBUG, object);
	}

	public void custom(LogLevel level, String user, Object object) {
		this.write(false, user, level, object);
	}

	public void force(LogLevel level, String user, Object object) {
		this.write(true, user, level, object);
	}

	//Write no user
	public void auditory(Object object) {
		this.write(false, this.user, LogLevel.AUDITORY, object);
	}

	public void emergency(Object object) {
		this.write(false, this.user, LogLevel.EMERGENCY, object);
	}

	public void alert(Object object) {
		this.write(false, this.user, LogLevel.ALERT, object);
	}

	public void critical(Object object) {
		this.write(false, this.user, LogLevel.CRITICAL, object);
	}

	public void error(Object object) {
		this.write(false, this.user, LogLevel.ERROR, object);
	}

	public void warning(Object object) {
		this.write(false, this.user, LogLevel.WARNING, object);
	}

	public void notice(Object object) {
		this.write(false, this.user, LogLevel.NOTICE, object);
	}

	public void information(Object object) {
		this.write(false, this.user, LogLevel.INFORMATION, object);
	}

	public void debug(Object object) {
		this.write(false, this.user, LogLevel.DEBUG, object);
	}

	public void custom(LogLevel level, Object object) {
		this.write(false, this.user, level, object);
	}

	public void force(LogLevel level, Object object) {
		this.write(true, this.user, level, object);
	}

	private void write(Boolean force, String user, LogLevel level, Object o) {
		if (!force && this.logFile.getLevel().ordinal() < level.ordinal())
			return;

		if (user == null)
			user = "";

		String s = this.style;
		s = s.replace("%line", this.logFile.getCurrentLine().toString());
		s = s.replace("%name", this.application);
		s = s.replace("%user", user.trim());
		s = s.replace("%timestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date()));
		if (force)
			s = s.replace("%level", "FORCE-" + level.name());
		else
			s = s.replace("%level", level.name());

		if (o == null)
			o = "null";
		else if (o instanceof Exception) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			((Exception) o).printStackTrace(new PrintStream(baos));
			o = new String(baos.toByteArray());
		}

		s = s.replace("%text", o.toString());

		this.logFile.write(s);
	}
}

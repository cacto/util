package me.cacto.util.i18n;

import java.util.Locale;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */
public interface I18nResource {
	public String getValue(Locale locale, String tag);
	public Boolean existLocale(Locale locale);
}

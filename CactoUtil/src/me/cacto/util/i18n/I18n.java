package me.cacto.util.i18n;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */
public class I18n {
	private final List<String> bundleList = new ArrayList<>();
	private final Map<String, Properties> mapProperties = new HashMap<>();
	private I18nResource i18nResource;

	public void registerI18nResource(I18nResource i18nResource) {
		this.i18nResource = i18nResource;
	}

	public void register(String baseName) {
		this.bundleList.add(baseName);
		this.mapProperties.clear();
	}

	//	public void remove(String baseName) {
	//		this.bundleList.add(baseName);
	//		this.mapProperties.clear();
	//	}

	//	public void clear() {
	//		this.mapProperties.clear();
	//	}

	public void clearBundles() {
		this.bundleList.clear();
	}

	public String get(Locale locale, String tag) {
		Properties properties = this.getProperties(locale);
		String value = properties.getProperty(tag);

		if (value == null && this.i18nResource != null)
			value = this.i18nResource.getValue(locale, tag);

		return value;
	}

	//	public void add(Locale locale, String tag, String value) {
	//		Properties properties = this.getProperties(locale);
	//		properties.put(tag, value);
	//	}

	public Set<Object> keySet(Locale locale) {
		Properties properties = this.getProperties(locale);
		return properties.keySet();
	}

	private Properties getProperties(Locale locale) {
		Properties properties = this.mapProperties.get(locale.toLanguageTag().toLowerCase());

		if (properties == null) {
			properties = new Properties();
			this.mapProperties.put(locale.toLanguageTag().toLowerCase(), properties);

			for (String baseName : this.bundleList) {
				ResourceBundle resourceBundle = this.loadResourceBundle(baseName, locale);
				if (resourceBundle == null)
					continue;

				properties.put("i18n.baseName", baseName);
				properties.put("i18n.language", locale.getLanguage().toLowerCase());
				properties.put("i18n.country", locale.getCountry().toUpperCase());

				for (Enumeration<String> e = resourceBundle.getKeys(); e.hasMoreElements();) {
					String key = e.nextElement();
					properties.put(key, resourceBundle.getObject(key));
					if (!properties.containsKey(key.toLowerCase()))
						properties.put(key.toLowerCase(), resourceBundle.getObject(key));
				}
			}
		}

		return properties;
	}

	public Boolean existLocale(Locale locale) {
		if (locale == null)
			return Boolean.FALSE;

		Boolean exist = this.getProperties(locale).size() > 3;

		if (!exist) {
			if (this.i18nResource != null)
				exist = this.i18nResource.existLocale(locale);
		}

		return exist;
	}

	private ResourceBundle loadResourceBundle(String baseName, Locale locale) {
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
			return resourceBundle;
		} catch (MissingResourceException e) {
			return null;
		}
	}
}

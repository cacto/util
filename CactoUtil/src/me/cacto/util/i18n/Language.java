package me.cacto.util.i18n;

import java.util.Locale;

public enum Language {
	/** No locale */
	NO_LANGUAGE(null),
	/** Bulgarian (Bulgaria) */
	BG_BG(new Locale("bg", "BG")),
	/** Catalan (Spain) */
	CA_ES(new Locale("ca", "ES")),
	/** Dutch (Belgium) */
	NL_BR(new Locale("nl", "BE")),
	/** Dutch (Netherlands) */
	NL_NL(new Locale("nl", "NL")),
	/** English (Australia) */
	EN_AU(new Locale("en", "AU")),
	/** English (Canada) */
	EN_CA(new Locale("en", "CA")),
	/** English (Ireland) */
	EN_IE(new Locale("en", "IE")),
	/** English (New Zealand) */
	EN_NZ(new Locale("en", "NZ")),
	/** English (Singapore) */
	EN_SG(new Locale("en", "SG")),
	/** English (United Kingdom) */
	EN_UK(new Locale("en", "UK")),
	/** English (United States) */
	EN_US(new Locale("en", "US")),
	/** French (Belgium) */
	FR_BE(new Locale("fr", "BE")),
	/** French (Canada) */
	FR_CA(new Locale("fr", "CA")),
	/** French (France) */
	FR_FR(new Locale("fr", "FR")),
	/** French (Luxembourg) */
	FR_LU(new Locale("fr", "LU")),
	/** French (Switzerland) */
	FR_CH(new Locale("fr", "CH")),
	/** German (Austria) */
	DE_AT(new Locale("de", "AT")),
	/** German (Germany) */
	DE_DE(new Locale("de", "DE")),
	/** German (Luxembourg) */
	DE_LU(new Locale("de", "LU")),
	/** German (Switzerland) */
	DE_CH(new Locale("de", "CH")),
	/** Italian (Italy) */
	IT_LU(new Locale("it", "IT")),
	/** Italian (Switzerland) */
	IT_CH(new Locale("it", "CH")),
	/** Portuguese (Brazil) */
	PT_BR(new Locale("pt", "BR")),
	/** Portuguese (Portugal) */
	PT_PT(new Locale("pt", "PT")),
	/** Spanish (Argentina) */
	ES_AR(new Locale("es", "AR")),
	/** Spanish (Bolivia) */
	ES_BO(new Locale("es", "BO")),
	/** Spanish (Chile) */
	ES_CL(new Locale("es", "CL")),
	/** Spanish (Colombia) */
	ES_CO(new Locale("es", "CO")),
	/** Spanish (Costa Rica) */
	ES_CR(new Locale("es", "CR")),
	/** Spanish (Dominican Republic) */
	ES_DO(new Locale("es", "DO")),
	/** Spanish (El Salvador) */
	ES_SV(new Locale("es", "SV")),
	/** Spanish (Ecuador) */
	ES_EC(new Locale("es", "EC")),
	/** Spanish (Guatemala) */
	ES_GT(new Locale("es", "GT")),
	/** Spanish (Honduras) */
	ES_HN(new Locale("es", "HN")),
	/** Spanish (Mexico) */
	ES_MX(new Locale("es", "MX")),
	/** Spanish (Nicaragua) */
	ES_NI(new Locale("es", "NI")),
	/** Spanish (Panama) */
	ES_PA(new Locale("es", "PA")),
	/** Spanish (Paraguay) */
	ES_PY(new Locale("es", "PY")),
	/** Spanish (Peru) */
	ES_PE(new Locale("es", "PE")),
	/** Spanish (Puerto Rico) */
	ES_PR(new Locale("es", "PR")),
	/** Spanish (Spain) */
	ES_ES(new Locale("es", "ES")),
	/** Spanish (Uruguay) */
	ES_UY(new Locale("es", "UY")),
	/** Spanish (Venezuela) */
	ES_VE(new Locale("es", "VE")),
	/** Swedish (Sweden) */
	SV_SE(new Locale("sv", "SE"));

	private Locale locale;
	private Language(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public Language getLanguage() {
		return this;
	}

	public String getLanguageString() {
		Locale locale = this.getLocale();
		if (locale == null)
			return null;

		return locale.getLanguage().toLowerCase();
	}

	public String getCountryString() {
		Locale locale = this.getLocale();
		if (locale == null)
			return null;

		return locale.getCountry().toUpperCase();
	}
}

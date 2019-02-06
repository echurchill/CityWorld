package me.daddychurchill.CityWorld;

interface CityWorldLog {

	void reportMessage(String message);

	void reportMessage(String message1, String message2);

	void reportFormatted(String format, Object... objects);

	void reportException(String message, Exception e);

}

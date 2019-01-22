package me.daddychurchill.CityWorld;

public interface CityWorldLog {

	public void reportMessage(String message);

	public void reportMessage(String message1, String message2);

	public void reportFormatted(String format, Object ... objects);

	public void reportException(String message, Exception e);

}

package util;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class GenUtils {

	private static Logger log = Logger.getLogger("SeleniumLog");

	public static final String TIME_FORMAT_SSS = "SSS";
	public static final String TIME_FORMAT_ddMMyyHHmmSSS = "ddMMyyHHmmSSS";
	public static final String TIME_FORMAT_ddMMMyyhhmmss = "dd-MMM-yy hh:mm:ss a";


	public static synchronized void sleepSeconds(long seconds) {
//		log.debug("Sleep for " + seconds + " seconds");
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
		}
	}

	public static synchronized void sleepMinutes(long minutes) {
//		log.debug("Sleep for " + minutes + " minutes");
		try {
			TimeUnit.MINUTES.sleep(minutes);
		} catch (InterruptedException e) {
		}
	}

	public static synchronized void sleepMillis(long millis) {
//		log.debug("Sleep for " + millis + " milliseconds");
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	public static synchronized String replaceInString(String locator, Object... placeholders) {
		if (placeholders.length < 1)
			return MessageFormat.format(locator.replace("'", "\""), 0);
		else
			return MessageFormat.format(locator.replace("'", "\""), placeholders);
	}


	private static synchronized String getTimestamp(String format, Date date) {
		return new SimpleDateFormat(format, Locale.ENGLISH).format(new Timestamp(date.getTime()));
	}

	public static synchronized String getCurrentTimestamp(String format) {
		return getTimestamp(format, new Date());
	}

	
}

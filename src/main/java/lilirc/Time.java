package lilirc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time class convert given date to string using time stamp
 * @author 3D
 *
 */
public class Time {
	private static String timeStamp= "[HH:mm:ss]";
	private static SimpleDateFormat simpleDateFormat= new SimpleDateFormat(timeStamp);
	
	/**
	 * return string of current time with default format
	 * @return String
	 */
	public static String is() {
		return simpleDateFormat.format(new Date());
	}
	/**
	 * return string of date with default format  
	 * @param date
	 * @return String
	 */
	public static String is(Date date) {
		return simpleDateFormat.format(date);
	}
	/**
	 * return string of date with tmStamp format
	 * @param date
	 * @param tmStamp
	 * @return String
	 */
	public static String is(Date date, String tmStamp) {
		return (new SimpleDateFormat(tmStamp)).format(date);
	}
	/**
	 * change default time stamp
	 * @param ts
	 * @return this
	 */
	public static void timeStamp(String ts) {
		simpleDateFormat= new SimpleDateFormat(timeStamp= ts);
	}
}

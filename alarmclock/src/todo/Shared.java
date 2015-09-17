package todo;

/**
 * Shared data storage and modification.
 */
public class Shared {
	public static int currentTime;
	public static int alarmTime;
	// Mutex?

	public static void setAlarm(int i) {
		alarmTime = i;
	}

	public static void setTime(int i) {
		currentTime = i;
	}

	public static boolean checkAlarm() {
		if (currentTime == alarmTime) {
			return true;
		}
		return false;
	}

	// Below are conversion methods
	public static int toMillis(int i) {
		Integer iInteger = new Integer(i);
		String readableTime = iInteger.toString();
		int l = readableTime.length();
		int d = 6 - l;
		for (int k = 0; k < d; k++) {
			readableTime = "0" + readableTime;
		}
		System.out.println("ReadableTime: " + readableTime);
		String sHour = readableTime.substring(0, 2);
		String sMinute = readableTime.substring(2, 4);
		String sSecond = readableTime.substring(4, 6);
		System.out.println(sHour);
		System.out.println(sMinute);
		System.out.println(sSecond);
		int iHour = Integer.parseInt(sHour);
		System.out.println("iHour: " + iHour);
		int iMinute = Integer.parseInt(sMinute);
		System.out.println("iMinute: " + iMinute);
		int iSecond = Integer.parseInt(sSecond);
		System.out.println("iSecond: " + iSecond);
		int time = iHour * 3600000 + iMinute * 60000 + iSecond * 1000;
		return time;
	}

	public static int toClockTime(long time) {
		long timeofday = time % 86400000;
		int hour = (int) (timeofday / 3600000);
		timeofday = timeofday % 3600000;
		int minute = (int) (timeofday / 60000);
		timeofday = timeofday % 60000;
		int second = (int) (timeofday / 1000);
		Integer iHour = new Integer(hour);
		Integer iMinute = new Integer(minute);
		Integer iSecond = new Integer(second);
		String hourpadding = "";
		String minutepadding = "";
		String secondpadding = "";
		if (hour < 10) {
			hourpadding = "0";
		}
		if (minute < 10) {
			minutepadding = "0";
		}
		if (second < 10) {
			secondpadding = "0";
		}
		String currTimeString = hourpadding + iHour.toString() + minutepadding + iMinute.toString() + secondpadding
				+ iSecond.toString();
		return Integer.parseInt(currTimeString);
	}
}

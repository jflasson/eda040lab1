package todo;

import se.lth.cs.realtime.semaphore.MutexSem;

/**
 * Shared data storage and modification.
 */
public class Shared {
	private static int currentTime;
	private static int alarmTime;
	private static boolean alarmIsRunning;
	private static int numOfBeeps;
	private static MutexSem mutex = new MutexSem();
	
	public static int getCurrentTime(){
		return currentTime;
	}
	
	public static void setCurrentTime(int time){
		mutex.take();
		currentTime = time;
		mutex.give();
	}
	
	public static void addToCurrentTime(int toAdd){
		mutex.take();
		currentTime += toAdd;
		mutex.give();
	}
	
	public static void resetNumOfBeeps(){
		numOfBeeps = 0;
	}
	
	public static int getNumOfBeeps(){
		return numOfBeeps;
	}
	
	public static void incrementNumOfBeeps(){
		numOfBeeps++;
	}

	public static void setAlarm(int i) {
		mutex.take();
		alarmTime = i;
		mutex.give();
	}

	public static void setTime(int i) {
		mutex.take();
		currentTime = i;
		mutex.give();
	}

	public static boolean checkAlarm() {
		System.out.println("CT:" + currentTime + " AT: " + alarmTime);
		if (currentTime == alarmTime) {
			return true;
		}
		return false;
	}
	
	public static void setAlarmStatus(boolean status){
		alarmIsRunning = status;
	}
	
	public static boolean getAlarmStatus(){
		return alarmIsRunning;
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
		String sHour = readableTime.substring(0, 2);
		String sMinute = readableTime.substring(2, 4);
		String sSecond = readableTime.substring(4, 6);
		int iHour = Integer.parseInt(sHour);
		int iMinute = Integer.parseInt(sMinute);
		int iSecond = Integer.parseInt(sSecond);
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

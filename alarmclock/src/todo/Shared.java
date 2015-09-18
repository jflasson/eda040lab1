package todo;

import done.ClockInput;
import done.ClockOutput;
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

	private static void setCurrentTime(int time) {
		mutex.take();
		currentTime = time;
		mutex.give();
	}

	private static void addToCurrentTime(int toAdd) {
		mutex.take();
		currentTime += toAdd;
		if (currentTime >= 86400000){
			currentTime = 0;
		}
		mutex.give();
	}

	public static void resetNumOfBeeps() {
		numOfBeeps = 0;
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

	private static boolean checkAlarm() {
		// System.out.println("CT:" + currentTime + " AT: " + alarmTime);
		if (currentTime == alarmTime) {
			return true;
		}
		return false;
	}

	// Below are conversion methods
	public static int toMillis(int i) {
		int seconds = i % 100;
		i = i / 100;
		int minutes = i % 100;
		i = i / 100;
		int hours = i;
		return hours * 3600000 + minutes * 60000 + seconds * 1000;
	}

	public static int toClockTime(long time) {
		long timeofday = time % 86400000;
		int hour = (int) (timeofday / 3600000);
		timeofday = timeofday % 3600000;
		int minute = (int) (timeofday / 60000);
		timeofday = timeofday % 60000;
		int second = (int) (timeofday / 1000);
		int timeOut = hour * 10000 + minute * 100 + second;
		return timeOut;
	}

	public static void incrementTime(ClockInput input, ClockOutput output) {
		addToCurrentTime(1000);
		//System.out.println(currentTime);
		output.showTime(toClockTime(currentTime));
		// System.out.println("Current millistime " + Shared.getCurrentTime());
		// System.out.println("Current clocktime " +
		// Shared.toClockTime(Shared.getCurrentTime()));
		if (input.getAlarmFlag()) {
			boolean alarm = checkAlarm();
			// System.out.println("Checkalarm: " + alarm);
			if (alarm) {
				alarmIsRunning = true;
			}
		}
		if (alarmIsRunning && numOfBeeps < 20) {
			output.doAlarm();
			numOfBeeps++;
			if (numOfBeeps == 20) {
				numOfBeeps = 0;
				alarmIsRunning = true;
			}
		}

	}

	public static void initTime(ClockInput input, ClockOutput output) {
		long t = System.currentTimeMillis() + 3600000 * 2; // Compensating for
															// GMT + 2
		setCurrentTime((int) (t % 86400000));
		setCurrentTime(currentTime / 1000);
		setCurrentTime(currentTime * 1000);
		// System.out.println("Time initialized to: " +
		// Shared.getCurrentTime());
		// System.out.println("Corresponding to clocktime: " +
		// Shared.toClockTime(Shared.getCurrentTime()));
		output.showTime(toClockTime(currentTime));
	}

	public static void handleInput(ClockInput input) {
		// System.out.println("Knapptråd");
		int choice = input.getChoice();
		// System.out.println(choice);
		if (alarmIsRunning) {
			if (choice == input.SHOW_TIME || choice == input.SET_TIME || choice == input.SET_ALARM) {
				// System.out.println("Alarm stopped!");
				alarmIsRunning = false;
				numOfBeeps = 0;
			}
		}
		if (choice == input.SET_TIME) {
			// Hantera inställning av tid
			Shared.setTime(Shared.toMillis(input.getValue()));
			// System.out.println("Time set!");
		} else if (choice == input.SET_ALARM) {
			// Hantera inställning av larmtid
			Shared.setAlarm(Shared.toMillis(input.getValue()));
			// System.out.println("Alarm set!");
		}

	}
}

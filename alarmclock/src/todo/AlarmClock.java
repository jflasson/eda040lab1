package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

/**
 * Handles time incrementation, display update and alarm detection.
 */
public class AlarmClock extends Thread {

	private static ClockInput input;
	private static ClockOutput output;
	
	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		initTime();
		ButtonHandler bHandler = new ButtonHandler(i);
		bHandler.start();
		Shared.resetNumOfBeeps();
	}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon
	// each keypress. To be modified in the lab.
	public void run() {
		while (true) {
			if (System.currentTimeMillis() % 1000 == 0) {
				long start = System.currentTimeMillis();
				System.out.println("TIME++");
				incrementTime();
				output.showTime(Shared.toClockTime(Shared.getCurrentTime()));
				System.out.println("Current millistime " + Shared.getCurrentTime());
				System.out.println("Current clocktime " + Shared.toClockTime(Shared.getCurrentTime()));
				if (input.getAlarmFlag()) {
					boolean alarm = Shared.checkAlarm();
					System.out.println("Checkalarm: " + alarm);
					if (alarm) {
						Shared.setAlarmStatus(true);
					}
				}
				if (Shared.getAlarmStatus() && Shared.getNumOfBeeps() < 20) {
					output.doAlarm();
					Shared.incrementNumOfBeeps();
					if (Shared.getNumOfBeeps() == 20) {
						Shared.resetNumOfBeeps();
						Shared.setAlarmStatus(false);
					}
				}
				long stop = System.currentTimeMillis();
				long ldiff = stop - start;
				int idiff = (int) ldiff;

				try {
					// hur länge ska man sleepa?
					sleep(1000 - idiff);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void incrementTime() {
		Shared.addToCurrentTime(1000);
	}

	private void initTime() {
		long t = System.currentTimeMillis() + 3600000 * 2; // Compensating for
															// GMT + 2
		Shared.setCurrentTime((int) (t % 86400000));
		Shared.setCurrentTime(Shared.getCurrentTime()/1000);
		Shared.setCurrentTime(Shared.getCurrentTime()*1000);
		System.out.println("Time initialized to: " + Shared.getCurrentTime());
		System.out.println("Corresponding to clocktime: " + Shared.toClockTime(Shared.getCurrentTime()));
		output.showTime(Shared.toClockTime(Shared.getCurrentTime()));
	}

}

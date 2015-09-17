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
	private static Semaphore sem;
//Vad gör man med semaforerna?
	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
		initTime();
		ButtonHandler bHandler = new ButtonHandler(i, o);
		bHandler.start();
	}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon
	// each keypress. To be modified in the lab.
	public void run() {
		while (true) {
			if (System.currentTimeMillis() % 1000 == 0) {
				System.out.println("TIME++");
				incrementTime();
				output.showTime(Shared.toClockTime(Shared.currentTime));
				System.out.println(Shared.currentTime);
				// Kolla larmet här
				if (input.getAlarmFlag()) {
					boolean alarm = Shared.checkAlarm();
					if (alarm) {
						output.doAlarm();
					}
				}
				try {
					//hur länge ska man sleepa?
					sleep(900);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void incrementTime() {
		Shared.currentTime += 1000;
	}

	private void initTime() {
		long t = System.currentTimeMillis() + 3600000 * 2; // Compensating for
															// GMT + 2
		Shared.currentTime = (int) (t % 86400000);
		output.showTime(Shared.currentTime);
	}

}

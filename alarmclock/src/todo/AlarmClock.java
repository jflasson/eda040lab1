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
		Shared.initTime(input, output);
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
		long t, diff;
		t = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() % 1000 == 0) {
				Shared.incrementTime(input, output);
				t += 1000;
				diff = t - System.currentTimeMillis();

				if (diff > 0) {
					try {
						Thread.sleep(diff);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}

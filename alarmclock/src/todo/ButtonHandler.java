package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

/**
 * Handles presses on buttons.
 */
public class ButtonHandler extends Thread {
	private static ClockInput input;
	private static ClockOutput output;
	private static Semaphore sem;

	public ButtonHandler(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
	}

	public void run() {
		while (true) {
			System.out.println("Knapptråd");
			sem.take();
			int choice = input.getChoice();
			System.out.println(choice);
			if (choice == input.SHOW_TIME) {
				try {
					//ska man sleepa här?
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (choice == input.SET_TIME) {
				// Hantera inställning av tid
				Shared.setTime(Shared.toMillis(input.getValue()));
				System.out.println("Time set!");
			} else if (choice == input.SET_ALARM) {
				// Hantera inställning av larmtid
				Shared.setAlarm(Shared.toMillis(input.getValue()));
				System.out.println("Alarm set!");
			}
		}
	}

}

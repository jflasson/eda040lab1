package todo;

import done.ClockInput;

import se.lth.cs.realtime.semaphore.Semaphore;

/**
 * Handles presses on buttons.
 */
public class ButtonHandler extends Thread {

	private static ClockInput input;
	private static Semaphore sem;

	public ButtonHandler(ClockInput i) {
		input = i;
		sem = input.getSemaphoreInstance();
	}

	public void run() {
		while (true) {
			sem.take();
			long start = System.currentTimeMillis();
			System.out.println("Knapptråd");
			int choice = input.getChoice();
			System.out.println(choice);
			if (Shared.getAlarmStatus()) {
				if (choice == input.SHOW_TIME || choice == input.SET_TIME || choice == input.SET_ALARM) {
					System.out.println("Alarm stopped!");
					Shared.setAlarmStatus(false);
					Shared.resetNumOfBeeps();
				}
			}
			if (choice == input.SET_TIME) {
				// Hantera inställning av tid
				Shared.setTime(Shared.toMillis(input.getValue()));
				System.out.println("Time set!");
			} else if (choice == input.SET_ALARM) {
				// Hantera inställning av larmtid
				Shared.setAlarm(Shared.toMillis(input.getValue()));
				System.out.println("Alarm set!");
			} else if (choice == input.SHOW_TIME) {
				long stop = System.currentTimeMillis();
				long ldiff = stop - start;
				int idiff = (int) ldiff;
				try {
					sleep(1000 - idiff);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}

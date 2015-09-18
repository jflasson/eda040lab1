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
			Shared.handleInput(input);
		}
	}

}

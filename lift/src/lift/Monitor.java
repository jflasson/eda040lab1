package lift;

public class Monitor {

	private int load = 0;
	private int currentFloor = 0;
	private int nextFloor = 1;
	private int[] waitEntry = new int[7];
	private int[] waitExit = new int[7];
	private LiftView lview;
	private boolean goingUp = true;
	private int peopleInSystem = 0;

	public Monitor(LiftView lview) {
		this.lview = lview;
	}

	public synchronized int[] moveLift() throws InterruptedException {
		currentFloor = nextFloor;
		notifyAll();
		while ((waitEntry[currentFloor] > 0 && load < 4) || waitExit[currentFloor] > 0 || peopleInSystem == 0) {
			wait();
		}
		if (goingUp) {
			if (currentFloor == 6) {
				nextFloor = 5;
				goingUp = false;
			} else {
				nextFloor++;
			}
		} else {
			if (currentFloor == 0) {
				nextFloor = 1;
				goingUp = true;
			} else {
				nextFloor--;
			}
		}
		// System.out.println("Current floor is: " + currentFloor);
		System.out.println("Load is: " + load);
		lview.drawLift(currentFloor, load);
		int[] returnArray = new int[2];
		returnArray[0] = currentFloor;
		returnArray[1] = nextFloor;
		notifyAll();
		return returnArray;
	}

	public synchronized void callLift(int startFloor, int endFloor) throws InterruptedException {
		peopleInSystem++;
		waitEntry[startFloor]++;
		lview.drawLevel(startFloor, waitEntry[startFloor]);
		notifyAll();
		while (currentFloor != startFloor || load > 3 || currentFloor != nextFloor) {
			wait();
		}
		waitEntry[startFloor]--;
		lview.drawLevel(startFloor, waitEntry[startFloor]);
		waitExit[endFloor]++;
		load++;
		lview.drawLift(currentFloor, load);
		notifyAll();
		System.out.println("Waiting on floor " + startFloor);

		while (currentFloor != endFloor || currentFloor != nextFloor) {
			wait();
		}
		waitExit[endFloor]--;
		load--;
		lview.drawLift(currentFloor, load);
		peopleInSystem--;
		notifyAll();
	}
}

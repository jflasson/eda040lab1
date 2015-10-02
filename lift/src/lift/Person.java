package lift;

public class Person extends Thread {

	public Monitor monitor;
	public int startFloor;
	public int endFloor;
	public boolean waiting;
	public boolean finished;
	public boolean newPerson;

	public Person(Monitor monitor) {
		this.newPerson = true;
		this.monitor = monitor;
		this.waiting = true;
		this.finished = false;
		int randStart = 0;
		int randEnd = 0;
		while (randStart == randEnd) {
			randStart = (int) (Math.random() * 6.0);
			randEnd = (int) (Math.random() * 6.0);
		}
		this.startFloor = randStart;
		this.endFloor = randEnd;
	}

	public void resetPerson() {
		this.waiting = true;
		this.finished = false;
		int randStart = 0;
		int randEnd = 0;
		while (randStart == randEnd) {
			randStart = (int) (Math.random() * 6.0);
			randEnd = (int) (Math.random() * 6.0);
		}
		this.startFloor = randStart;
		this.endFloor = randEnd;
		this.newPerson = true;
	}

	public void run() {
		while (true) {
			if (this.newPerson) {
				monitor.waitEntry[startFloor]++;
				monitor.lview.drawLevel(startFloor, monitor.waitEntry[startFloor]);
				this.newPerson = false;
			}
			if (waiting) {
				System.out.println("Waiting on floor " + startFloor);
				if (monitor.attemptToBoard(startFloor, endFloor)) {
					System.out.println("Entered elevator");
					waiting = false;
				}
			} else if (!finished) {
				System.out.println("In lift, wanting to get of at " + endFloor);
				if (monitor.attemptToExit(endFloor)) {
					monitor.load--;
					monitor.lview.drawLift(monitor.currentFloor, monitor.load);
					System.out.println("Left elevator");
					finished = true;
				}
			} else if (finished) {
				// code for reset
				int timeToWait = 1000 * (int) (Math.random() * /* 46.0 */ 1);
				try {
					Thread.sleep(timeToWait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				resetPerson();

			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

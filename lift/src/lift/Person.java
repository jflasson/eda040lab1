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
			randStart = (int) (Math.random() * 7.0);
			randEnd = (int) (Math.random() * 7.0);
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
			randStart = (int) (Math.random() * 7.0);
			randEnd = (int) (Math.random() * 7.0);
		}
		this.startFloor = randStart;
		this.endFloor = randEnd;
		this.newPerson = true;
	}

	public void run() {
		while (true) {
			int timeToWait = 1000 * (int)(Math.random()* /*46.0*/ 10);
			try {
				Thread.sleep(timeToWait);
				resetPerson();
				monitor.callLift(startFloor, endFloor);
			}
			catch (InterruptedException e) {
				e.printStackTrace(); 
			}
			
			
		}
	}
}

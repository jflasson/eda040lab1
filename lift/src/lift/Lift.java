package lift;

public class Lift extends Thread {

	public Monitor monitor;

	public Lift(Monitor monitor) {
		this.monitor = monitor;
	}

	public void run() {
		while (true) {
			monitor.moveLift();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

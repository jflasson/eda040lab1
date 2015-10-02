package lift;

public class Lift extends Thread {

	public Monitor monitor;
	public LiftView lview;

	public Lift(Monitor monitor, LiftView lview) {
		this.monitor = monitor;
		this.lview = lview;
	}

	public void run() {
		int[] returns = new int[2];
		while (true) {	
			try {
				returns = monitor.moveLift();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			lview.moveLift(returns[0], returns[1]);
		}
	}
}

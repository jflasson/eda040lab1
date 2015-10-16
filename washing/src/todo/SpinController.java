package todo;


import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;


public class SpinController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine mach;
	private SpinEvent se;
	// 0 is still, 1 is clockwise, 2 is counterclockwise

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
	//	System.out.println("Entering spin controller");
		se = (SpinEvent) this.mailbox.tryFetch();
		if(se != null){
		int spinMode = se.getMode();
		((RTThread)se.getSource()).putEvent(new RTEvent(this));
		
		mach.setSpin(spinMode);
		
	//	System.out.println("Exiting spin controller");
		}
	}
}

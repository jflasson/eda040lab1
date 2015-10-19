package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine mach;
	private SpinEvent se;
	private int rotation = 1;
	private double speed;
	private SpinEvent tempse;
	// 0 is still, 1 is clockwise, 2 is counterclockwise

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
		this.speed = speed;
	}

	public void perform() {
		
		//System.out.println("Entering spin controller");
		
		tempse = (SpinEvent) this.mailbox.tryFetch();
		
		if(tempse != null){
			se = tempse;
		}
		if (se != null) {
			int spinMode = se.getMode();
			if (spinMode == SpinEvent.SPIN_FAST) {
				//Centrifuge
				if (!(mach.getWaterLevel() > 0)) {
					mach.setSpin(spinMode);
				} else {
					System.out.println("Trying to centrifuge with water in machine!");
				}
			} else if(spinMode == SpinEvent.SPIN_SLOW){ 
				//Alternating clockwise and counterclockwise spin
				long time = System.currentTimeMillis();
				time = time*(long)speed;
				long minutes = time / 1000 / 60;
				System.out.println("in if");
				if(minutes % 2 == 0){
					mach.setSpin(mach.SPIN_LEFT);
				}else{
					mach.setSpin(mach.SPIN_RIGHT);
				}
			}else{
				//Stop rotation
				mach.setSpin(spinMode);
			}
		}
	}
}

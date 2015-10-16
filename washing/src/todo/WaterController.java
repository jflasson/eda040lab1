package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class WaterController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine mach;
	private double waterLevel = 0;
	private double targetWaterLevel;
	private int pumpMode;
	private WaterEvent we;

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
		// TODO: implement this method
	//	System.out.println("Entering water controller");
		we = (WaterEvent) this.mailbox.tryFetch();
		if (we != null) {
			targetWaterLevel = we.getLevel();
			pumpMode = we.getMode();
			((RTThread) we.getSource()).putEvent(new RTEvent(this));

			System.out.println("Target water level: " + targetWaterLevel);
			System.out.println("Current water level: " + mach.getWaterLevel());
			switch (pumpMode) {
			case 0:
				mach.setDrain(false);
				mach.setFill(false);
				break;
			case 1:
				if (mach.getWaterLevel() < targetWaterLevel) {
					mach.setDrain(false);
					mach.setFill(true);
				} else {
					mach.setDrain(false);
					mach.setFill(false);
				}
				break;
			case 2:
				mach.setDrain(true);
				mach.setFill(false);
				break;
			default:

			//	System.out.println("Exiting water controller");
			}
		}

		// if(waterLevel < targetWaterLevel){
		// mach.setFill(true);
		// }else if(waterLevel > targetWaterLevel){
		// mach.setDrain(true);
		// }else if(waterLevel == targetWaterLevel){
		// mach.setFill(false);
		// mach.setDrain(false);
		// }
	}
}

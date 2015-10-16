package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class WaterController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine mach;
	private double targetWaterLevel;
	private int pumpMode;
	private WaterEvent we;
	private WaterEvent te;
	private boolean first = true;

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
		te = (WaterEvent) this.mailbox.tryFetch();
		if (te != null) {
			first = true;
			we = te;
			targetWaterLevel = we.getLevel();
			pumpMode = we.getMode();
		}

		switch (pumpMode) {
		case WaterEvent.WATER_IDLE:
			mach.setDrain(false);
			mach.setFill(false);
			break;
		case WaterEvent.WATER_FILL:
			if (mach.getWaterLevel() < targetWaterLevel) {
				mach.setDrain(false);
				mach.setFill(true);
			} else {
				mach.setFill(false);
				if (first) {
					((RTThread) we.getSource()).putEvent(new RTEvent(this));
					first = false;
				}
			}
			break;
		case WaterEvent.WATER_DRAIN:
			if (mach.getWaterLevel() != 0) {
				mach.setDrain(true);
				mach.setFill(false);
			} else {
				mach.setDrain(false);
				if (first) {
					((RTThread) we.getSource()).putEvent(new RTEvent(this));
					first = false;
				}
			}
			break;
		default:

		}
	}
}

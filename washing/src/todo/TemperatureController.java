package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine mach;
	private TemperatureEvent te;
	private TemperatureEvent tempe;
	private double targetTemperature;
	private boolean first = true;
	private double onTemp = 0;
	private double offTemp = 0;

	public TemperatureController(AbstractWashingMachine mach, double speed) {

		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
		// TODO: implement this method
		tempe = (TemperatureEvent) this.mailbox.tryFetch();
		if (tempe != null) {

			te = tempe;
			first = true;
			targetTemperature = te.getTemperature();
			onTemp = targetTemperature - 2 + 0.2;
			offTemp = targetTemperature - 1;
		}
		if(te != null){
			switch (te.getMode()) {
			case TemperatureEvent.TEMP_IDLE:
				mach.setHeating(false);
				if (first && te != null) {
					((RTThread) te.getSource()).putEvent(new RTEvent(this));
					first = false;
				}
				break;
			case TemperatureEvent.TEMP_SET:
				if (mach.getTemperature() >= offTemp) {
					if (first) {
						((RTThread) te.getSource()).putEvent(new RTEvent(this));
						first = false;
					}
					mach.setHeating(false);
				} else if (mach.getTemperature() <= onTemp && mach.getWaterLevel() > 0) {
					mach.setHeating(true);
				}
				break;
			default:
			}
		}

	}

	// private void initThresholds() {
	// thresholds[0][0] = 0;
	// thresholds[1][0] = 0;
	// thresholds[0][1] = 0;
	// thresholds[1][1] = 0;
	// thresholds[0][2] = 0;
	// thresholds[1][2] = 0;
	// }

}

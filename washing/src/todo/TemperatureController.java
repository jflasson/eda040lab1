package todo;


import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;


public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine mach;
	private TemperatureEvent te;
	private int heaterMode;
	private double waterTemperature;
	private double targetTemperature;
	private double[][] thresholds;
	
	// FIXA TEMPERATUR!
	public TemperatureController(AbstractWashingMachine mach, double speed) {
		
		super((long) (1000/speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
	//	System.out.println("Entering temperature controller");
	//	initThresholds();
		// TODO: implement this method
		te = (TemperatureEvent) this.mailbox.tryFetch();
		if(te != null){
			heaterMode = te.getMode();
			targetTemperature = te.getTemperature();
			((RTThread)te.getSource()).putEvent(new RTEvent(this));
			
			switch(heaterMode){
				case 0:
					mach.setHeating(false);
					break;
				case 1:
					mach.setHeating(true);
					break;
				default:
			}
		//	System.out.println("Exiting temperature controller");
		}
	}
	
	private void initThresholds(){
		thresholds[0][0] = 0;
		thresholds[1][0] = 0;
		thresholds[0][1] = 0;
		thresholds[1][1] = 0;
		thresholds[0][2] = 0;
		thresholds[1][2] = 0;
	}
	
}

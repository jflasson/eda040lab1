package todo;

import done.*;

public class WashingController implements ButtonListener {
	// TODO: add suitable attributes
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private TemperatureController tempController;
	private WaterController waterController;
	private SpinController spinController;
	private WashingProgram wp;

	public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		tempController = new TemperatureController(theMachine, theSpeed);
		waterController = new WaterController(theMachine, theSpeed);
		spinController = new SpinController(theMachine, theSpeed);
		this.theMachine = theMachine;
		this.theSpeed = theSpeed;
		tempController.start();
		waterController.start();
		spinController.start();
		

	}

	public void processButton(int theButton) {
		// TODO: implement this method
		switch (theButton) {
		case 0:
			System.out.println("Button 0 pressed");
			if (wp != null && wp.isAlive()) {
					wp.interrupt();
			}
			break;
		case 1:
			System.out.println("Button 1 pressed");
			wp = new WashingProgram1(theMachine, theSpeed, tempController, waterController, spinController);
			break;
		case 2:
			System.out.println("Button 2 pressed");
			wp = new WashingProgram2(theMachine, theSpeed, tempController, waterController, spinController);
			break;
		case 3:
			System.out.println("Button 3 pressed");
			wp =  new WashingProgram3(theMachine, theSpeed, tempController, waterController, spinController);
			break;
		}
		wp.start();
	}
}

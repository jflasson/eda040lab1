package todo;

import done.*;

public class WashingController implements ButtonListener {	
	// TODO: add suitable attributes
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private WashingProgram wp;
	private TemperatureController tempController;
	private WaterController waterController;
	private SpinController spinController;
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		tempController = new TemperatureController(theMachine, theSpeed);
    	waterController = new WaterController(theMachine, theSpeed);
		spinController = new SpinController(theMachine, theSpeed);
		this.theMachine = theMachine;
		this.theSpeed = theSpeed;
		
    }

    public void processButton(int theButton) {
		// TODO: implement this method
    	switch(theButton){
    		case 0:
    			System.out.println("Button 0 pressed");
    			wp = new WashingProgram3(theMachine, theSpeed, tempController, waterController, spinController);
    		break;
    		case 1:
    			System.out.println("Button 1 pressed");
    			wp = new WashingProgram1(theMachine, theSpeed, tempController, waterController, spinController);
    		break;
    		case 2:
    			System.out.println("Button 2 pressed");
    			wp = new WashingProgram3(theMachine, theSpeed, tempController, waterController, spinController);
    		break;
    		case 3:
    			System.out.println("Button 3 pressed");
    			wp = new WashingProgram3(theMachine, theSpeed, tempController, waterController, spinController);
    		break;
    	}
    	wp.start();
    }
}

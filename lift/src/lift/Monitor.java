package lift;

import java.util.ArrayList;

public class Monitor {
	
	private Lift lift;
	private int [] waitEntry = new int[6];
	private int [] waitExit = new int[6];
	
	public Monitor(Lift lift){
		this.lift = lift;
	}
	
	private ArrayList<Person> persons = new ArrayList<Person>();	
	
	public void addPerson() {
		// TODO Auto-generated method stub
		
	}
	
	public void moveLift(){
		int currentFloor = lift.currentFloor;
		int nextFloor = lift.nextFloor;
		currentFloor = nextFloor;
		if(currentFloor == 6){
			nextFloor = 5;
		}else if(currentFloor == 0){
			nextFloor = 1;
		}
	}
	
	int here(){
		return lift.currentFloor;
	};
	
	int next(){
		return lift.nextFloor;
	}

}

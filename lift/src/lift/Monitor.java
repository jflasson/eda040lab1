package lift;

public class Monitor {
	
	public int load = 0;
	public int currentFloor = 0;
	public int nextFloor = 1;
	public int [] waitEntry = new int[6];
	public int [] waitExit = new int[6];
	public LiftView lview = new LiftView();
	
	public synchronized void moveLift(){
		if(currentFloor < nextFloor){
			if(currentFloor == 5){
				currentFloor = nextFloor;
				nextFloor = 5;
			}
			else{
				currentFloor = nextFloor;
				nextFloor++;
			}
		}
		else if(currentFloor > nextFloor){
			if(currentFloor == 1){
				currentFloor = nextFloor;
				nextFloor = 1;
			}
			else{
				currentFloor = nextFloor;
				nextFloor--;
			}
		}
//		System.out.println("Current floor is: " + currentFloor);
		System.out.println("Load is: " + load);
		lview.drawLift(currentFloor, load);
	}


	public synchronized boolean attemptToBoard(int startFloor, int endFloor) {
		if(startFloor == currentFloor && load < 4){
			waitEntry[startFloor]--;
			lview.drawLevel(startFloor, waitEntry[startFloor]);
			waitExit[endFloor]++;
			load++;
			lview.drawLift(currentFloor, load);
			return true;
		}
		return false;
		
	}

	public synchronized boolean attemptToExit(int endFloor) {
		if(endFloor == currentFloor){
			waitExit[endFloor]--;
			
			lview.drawLift(currentFloor, load);
			return true;
		}
		return false;
		
	}
}

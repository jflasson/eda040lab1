package lift;

public class Main {

	public static void main(String[] args) {
		int numOfPersons = 0;
		int numOfPotatoes = 25;
		
		Lift lift = new Lift();
		Monitor monitor = new Monitor(lift);
		monitor.addPerson();
		while(true){
			if (numOfPersons < 20){
				
				int timeToWait = 1000 * (int)(Math.random()*46.0);
				try {
					Thread.sleep(timeToWait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				monitor.addPerson();
				
			}
		}

	}

}

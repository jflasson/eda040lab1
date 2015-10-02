package lift;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Person> people = new ArrayList<Person>();
		Monitor monitor = new Monitor();
		Lift lift = new Lift(monitor);
		for(int i = 0; i < 20; i++){
			Person temp = new Person(monitor);
			people.add(temp);
		}
		lift.start();
		for(int j = 0; j < 20; j++){
			int timeToWait = 1000 * (int)(Math.random()*/*46.0*/ 1);
			try {
				Thread.sleep(timeToWait);
			}
			catch (InterruptedException e) {
				e.printStackTrace(); 
			} 
			people.get(j).start();
		}
		

	}
}

package lift;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Person> people = new ArrayList<Person>();
		LiftView lview = new LiftView();
		Monitor monitor = new Monitor(lview);
		Lift lift = new Lift(monitor, lview);
		for(int i = 0; i < 20; i++){
			Person temp = new Person(monitor);
			people.add(temp);
		}
		lift.start();
		for(int j = 0; j < 20; j++){
			
			people.get(j).start();
		}
		

	}
}

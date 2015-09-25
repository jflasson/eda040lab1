package lift;

public class Person extends Thread {

	private int startFloor;
	private int endFloor;
	private int id;

	public Person(int id) {
		this.id = id;
		int randStart = 0;
		int randEnd = 0;
		while (randStart == randEnd) {
			randStart = (int) (Math.random() * 6.0);
			randEnd = (int) (Math.random() * 6.0);
		}

		this.startFloor = randStart;
		this.endFloor = randEnd;
	}
}

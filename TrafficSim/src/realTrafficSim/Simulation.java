package realTrafficSim;

import java.util.ArrayList;
import processing.core.*;


public class Simulation extends PApplet {

	//	#NO
	//	ALL THE #NO FOR ALL OF THE PROJECT

	private static final long serialVersionUID = 1L;

	int c = color(0);
	float y = 100;
	float carWidth = 60;
	float carHeight = 30;
	float speed = 1;
	int x1 = 0;
	int offset = 0;
	int medianSpeed = 3;
	Car car1 = new Car();
	Car car2 = new Car();
	ArrayList<Car> cars = new ArrayList<Car>();

	public void setup() {
		size(displayWidth, displayHeight);
		background(0);
		cars.add(car1);
		cars.add(car2);
	}

	public void draw() {
		background(0, 255, 0);
		highwayLanes();
		move(cars);
		display();
		changeLane();
	}

	void move(ArrayList<Car> carsList) {
		for (Car car : carsList) {
			car.setxCoord(car.getxCoord() + speed);
		}
		
//		car1.setxCoord(car1.getxCoord()+speed);

//	controls looping; treadmill effect
//		if (userCar.getxCoord() > width) {
//			userCar.setxCoord(0);
//		}
	}

	void changeLane() {
		car1.setyCoord(car1.getyCoord() + speed);
		
		if (car1.getyCoord() == 230) {
			car1.setyCoord(0);
		}
		if (x1 > width) {
			x1 = 0;
		}
	}

	void highwayLanes() {
		// road
		fill(51);
		stroke(0);
		rect(0, 100, displayWidth, 200);

		// top outer road line
		fill(255);
		stroke(0);
		rect(0, 110, displayWidth, 10);
		//bottom outer road line
		fill(255);
		stroke(0);
		rect(0, 280, displayWidth, 10);

		// median
		fill(255);
		stroke(0); 
		offset = offset - medianSpeed;
		
		for(int i = 0; i < displayWidth; i++) {
			rect(i * 100 + offset, 195, 40, 10);
		}
	}

	void display() {
		fill(255, 0, 0);
		stroke(0);
		rect(car1.getxCoord(), car1.getyCoord()+140, carWidth, carHeight);
		rect(car2.getxCoord(), car2.getyCoord()+230, carWidth, carHeight);

//		fill(c);
//		rect(x2, y+100, carWidth, carHeight);

//		fill(c+200);
//		rect(x1, y+200, carWidth, carHeight);
	}
}




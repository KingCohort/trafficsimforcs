package realTrafficSim;

import processing.core.*;


public class Simulation extends PApplet {

	//	#NO

	int c = color(0);
	float x, x2, x3 = 0;
	float y = 100;
	float carWidth = 60;
	float carHeight = 30;
	float speed = 2;
	float speed2 = 3;
	float speed3 = 1;
	int laneLength = 60;
	Car userCar = new Car();

	public void setup() {
		size(displayWidth, displayHeight);
		background(0);
	}

	public void draw() {
		background(255);
		move();
		display();
		changeLane();
	}

	void move() {
		userCar.setxCoord(userCar.getxCoord()+speed);
		x2 = x2 + speed2;

		if (userCar.getxCoord() > width) {
			userCar.setxCoord(0);
		}
		if (x2 > width){
			x2 = 0;
		}
	}

	void changeLane(){
		userCar.setyCoord(userCar.getyCoord()+speed);
		if (userCar.getyCoord() == displayHeight){
			userCar.setyCoord(0);
		}

			x3 = x3 + speed3;

			if (x > width) {
				x = 0;

			}
			if (x2 > width) {
				x2 = 0;
			}
			if (x3 > width) {
				x3 = 0;
			}
		
	}

	void display() {
		fill(c);
		rect(userCar.getxCoord(), userCar.getyCoord(), carWidth, carHeight);

		//fill(c);
		//rect(x2, y+100, carWidth, carHeight);

		fill(c+200);
		rect(x3, y+200, carWidth, carHeight);
	}
}

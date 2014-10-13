

package realTrafficSim;

import processing.core.*;

public class Simulation
{
	
	void Sim(PApplet par)
	{
		int c = par.color(0);
		int carWidth = 60;
		int carHeight = 30;
		int car1X = 90;
		int car1Y = 150;
		int car2X = 0;
		int car2Y = 150;
		
		Car car1 = new Car(car1X, car1Y, carWidth, carHeight);
		Car car2 = new Car(car2X, car2Y, carWidth, carHeight);
		
		par.fill(255,0,0);
		par.stroke(0);
		par.rect(car1X, car1Y, carWidth, carHeight);
		
		move(car1, 1);
		move(car2, 1);
	}
	
	void move(Car car, int speed)
	{
		car.setxCoord(car.getxCoord()+speed);

		//	controls looping; treadmill effect
		if (car.getxCoord() > car.width) {
			car.setxCoord(0);
		}
	}
}
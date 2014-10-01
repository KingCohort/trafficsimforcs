

package realTrafficSim;

import processing.core.*;

public class Main extends PApplet
{
	// needed for processing
	private static final long serialVersionUID = 1L;
	
	private static Main mainClass;
	
	int c = color(0);
	float y = 100;
	float carWidth = 60;
	float carHeight = 30;
	float speed = 1;
	int x1 = 0;
	float laneChangeVar = 40;

	Car car1 = new Car(0,140);
	Car car2 = new Car(0 + carWidth + 50, 140);
	Environment env = new Environment();
	
	public static void main(String[] args)
	{
		PApplet.main("realTrafficSim.Main");
	}
	
	public void setup()
	{
		mainClass = this;
		size(displayWidth, displayHeight);
		background(0);
		//		cars.add(car1);
		//		cars.add(car2);
	}

	public void draw()
	{
		background(0, 255, 0);
		env.createHighway();
		move(car1, 1);
		move(car2, 1);
		changeLane(car1, car2, 1, 1); //1 = merging down 0 = merging up
		display();
	}
	
	public static Main getMainObject()
    {
            return mainClass;
    }
	
	void move(Car car, float speed)
	{
		car.setxCoord(car.getxCoord()+speed);

		//	controls looping; treadmill effect
		if (car.getxCoord() > width) {
			car.setxCoord(0);
		}
	}

	void changeLane(Car car, Car obsCar, float speed1, int direction)
	{
		car.setyCoord(car.getyCoord() + laneChangeVar);
	}

	void display()
	{
		fill(255, 0, 0);
		stroke(0);
		rect(car1.getxCoord(), car1.getyCoord(), carWidth, carHeight);
		rect(car2.getxCoord(), car2.getyCoord(), carWidth, carHeight);
	}
}

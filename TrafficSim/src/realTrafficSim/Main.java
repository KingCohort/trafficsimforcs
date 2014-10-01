

package realTrafficSim;

import processing.core.*;

public class Main extends PApplet
{
	// needed for processing
	private static final long serialVersionUID = 1L;
	
	private static Main mainClass;
	Environment env = new Environment();
	int c = color(0);
	float y = 100;
	float carWidth = 60;
	float carHeight = 30;
	float speed = 1;
	int x1 = 0;
	float laneChangeVar = 40;

	
	Car car1 = new Car(0,140);
	Car car2 = new Car(0 + carWidth + 50, 140);
	
	
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
		env.createHighway(this);
		//display();
	}
	
	public static Main getMainObject()
    {
            return mainClass;
    }
}







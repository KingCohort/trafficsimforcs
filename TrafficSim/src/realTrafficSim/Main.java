

package realTrafficSim;

import processing.core.*;

public class Main extends PApplet
{
	// needed for processing
	private static final long serialVersionUID = 1L;
	
	private static Main mainClass;
	
	public static Car car1 = new Car(0,150,60,40);
    public static Car car2 = new Car(0 + 150 + 50, 140,60,40);
	
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
	}

	public void draw()
	{
		background(0, 255, 0);
		env.createHighway(this);
		env.move(car1, 1);
		env.move(car2, 1);
		env.changeLane(car1, 1);
		env.display(this);
	}
	
	public static Main getMainObject()
    {
		return mainClass;
    }
}







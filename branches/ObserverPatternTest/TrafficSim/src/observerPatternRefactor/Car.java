

package observerPatternRefactor;

import java.awt.Point;


public class Car
{
	int xCoord;
	int yCoord;
	int width;
	int height;
	int carWidth = 60;
	int carHeight = 30;
	int car1X = 90;
	int car1Y = 150;
	int car2X = 0;
	int car2Y = 150;
	boolean carInOtherLane = false;
	
	public Car(int xCoord, int yCoord, int width, int height)
	{
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.width = width;
		this.height = height;
	}
	
	public static void main(Car car){
		
		car.makeDecision(car);

	}

	public Point getLocation()
	{
		Point loc = new Point();
		
		loc.setLocation(this.xCoord , this.yCoord);
		
		return loc;
	}
	
	public int getxCoord()
	{
		return xCoord;
	}

	public void setxCoord(int f)
	{
		this.xCoord = f;
	}

	public int getyCoord()
	{
		return yCoord;
	}

	public void setyCoord(int yCoord)
	{
		this.yCoord = yCoord;
	}
	
	void move(Car car, int speed)
	{
		car.setxCoord(car.getxCoord() + speed);

	}
	
	void makeDecision(Car car){
		
		car.move(car, 1);
		
		
	}
	
	void changeLane(Car car, int speed)
	{
		if  (car.getyCoord() < 250 && carInOtherLane == false)// '/highwayYcoor-(lanesize*0.5)')
		{
		car.setyCoord(car.getyCoord() + speed);
		}
		else if (car.getyCoord() == 250)
		{
			carInOtherLane = true;
			car.setxCoord(car.getxCoord() + speed +1);
		}
		if (car.getxCoord() > 960 && car.getyCoord() >= 150)
		{
			car.setyCoord(car.getyCoord() - speed);
		}
	}
}















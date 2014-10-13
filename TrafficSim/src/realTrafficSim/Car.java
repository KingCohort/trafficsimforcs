

package realTrafficSim;

import java.awt.Point;

public class Car
{
	int xCoord;
	int yCoord;
	int width;
	int height;
	
	public Car(int xCoord, int yCoord, int width, int height)
	{
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.width = width;
		this.height = height;
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
}















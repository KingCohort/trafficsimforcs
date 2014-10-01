
package realTrafficSim;

import java.awt.Point;

public class Car {
	float xCoord;
	float yCoord;
	
	public Car(float xCoord, float yCoord) {
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	public Point getLocation() {
		Point loc = new Point();
		
		loc.setLocation(this.xCoord , this.yCoord);
		
		return loc;
	}
	
	public float getxCoord() {
		return xCoord;
	}

	public void setxCoord(float xCoord) {
		this.xCoord = xCoord;
	}

	public float getyCoord() {
		return yCoord;
	}

	public void setyCoord(float yCoord) {
		this.yCoord = yCoord;
	}

}
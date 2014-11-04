

package moreRefactor;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.geometry.*;

// creates a car
// the program will create many cars to simulate real traffic
public class Car
{
	float xCoord;
	float yCoord;
	double width = TrafficConstants.CARWIDTH;
	double height = TrafficConstants.CARHEIGHT;
	boolean carInOtherLane = false;
	float speed = 1;
	boolean changingLanes = false;
	float nextLaneMiddle = 0;
	boolean changeLaneTest = false;
	ArrayList<BoundingBox> carloc;

	public Car(float xCoord, float yCoord)
	{
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		

	}

	public float getxCoord()
	{
		return xCoord;
	}

	public void setxCoord(float f)
	{
		this.xCoord = f;
	}

	public float getyCoord()
	{
		return yCoord;
	}

	public void setyCoord(float yCoord)
	{
		this.yCoord = yCoord;
	}

	double computeCarLength()
	{
		double carLength = (getxCoord() + width);
		return carLength;
	}

	double computeCarHeight()
	{
		double carHeight = (getyCoord() + height);
		return carHeight;
	}

	BoundingBox getBoundingBox()
	{		
		BoundingBox carBox = new BoundingBox(getxCoord(), getyCoord(), width, height);
		return carBox;
	}
	
	void makeDecision(ArrayList<BoundingBox> carloc)
	{		
		this.carloc = carloc;
		move(speed);
		changeLane(speed, carloc);
	}
	
	void move(float speed)
	{
		setxCoord(getxCoord() + speed);
		//setxCoord(50);
	}
	
	void slowDown(){
		speed = (float) (speed - 0.5);
	}
	void speedUp(){
		speed = (float) (speed + 0.5);
	}
	
	void moveOneLaneDown(){
		setyCoord(getyCoord() + speed);
		
	}
	
	void changeLane(float speed, ArrayList<BoundingBox> carloc)
	{
		boolean[] surroundingCarLocations = checkOtherCars(carloc);
	
		if(surroundingCarLocations[0] == true && changingLanes == false){
			
			System.out.println("changing lanes commenced");
			changingLanes = true;
			nextLaneMiddle = getyCoord() + 100;
			speedUp();
			}
		if (changingLanes == true && getyCoord() < nextLaneMiddle) {
			moveOneLaneDown();
			System.out.println(getyCoord());
			System.out.println(nextLaneMiddle);
		}
		else if (changingLanes == true && getyCoord() >= nextLaneMiddle) {
			changingLanes = false;
			System.out.println("I am done changing lanes");

		}
	}


	boolean[] checkOtherCars(ArrayList<BoundingBox> carLoc)
	{

		boolean[] surroundingCarLocations = new boolean[4]; // 0 = right 1 = up 2 = left 3 = down
		//method that informs the car of its surroundings. Returns an array of booleans for each cardinal direction
		//the car could attempt to move. This is called before any decision is made
		for(int i = 0; i < carLoc.size(); i++)
		{

			if((getBoundingBox().getMaxX() < carLoc.get(i).getMinX()))
			{

				//right direction, assignment of index 0
				surroundingCarLocations[0] = true;
			} 
//			if((getBoundingBox().getMaxX() + computeCarLength()) < carLoc.get(i).getMinX())
//			{
//				//right direction, assignment of index 0
//				surroundingCarLocations[0] = false;
//			}
			if((getBoundingBox().getMinY() > carLoc.get(i).getMaxY()))
			{
				//up direction, assignment of index 1
				surroundingCarLocations[1] = true;
			}
//			if((getBoundingBox().getMinY() + computeCarHeight()*2) > carLoc.get(i).getMaxY())
//			{
//				//up direction, assignment of index 1
//				surroundingCarLocations[1] = false;
//			}
			if((getBoundingBox().getMinX()) > carLoc.get(i).getMaxX())
			{
				//left direction, assignment of index 2
				surroundingCarLocations[2] = true;			
			}
//			if((getBoundingBox().getMinX() - computeCarLength()) > carLoc.get(i).getMaxX())
//			{
//				//left direction, assignment of index 2
//				surroundingCarLocations[2] = false;	
//			}
			if((getBoundingBox().getMaxY() < carLoc.get(i).getMinY()))
			{
				//down direction, assignment of index 3
				surroundingCarLocations[3] = true;
			}
//			if((getBoundingBox().getMaxY() - computeCarHeight()*2 > carLoc.get(i).getMinY()))
//			{
//				//down direction, assignment of index 3
//				surroundingCarLocations[3] = false;
//			}			
		}	
		
		return surroundingCarLocations;
	}
	
	public boolean contains(int x, int y)
	{
		return (getBoundingBox().contains(x, y));
	}
	
	public void debug()
	{
		
		boolean[] check = checkOtherCars(carloc);
		
		String out = "";
		
		out+="right =" + check[0] + "\n";
		out+="up=" + check[1] +"\n";
		out+="left="+check[2]+"\n";
		out+="down="+check[3]+"\n";
		out+="changelane="+changingLanes+"\n";
		JOptionPane.showMessageDialog(TrafficView.view, out);
	}
}















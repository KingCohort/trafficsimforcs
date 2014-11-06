

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
	double width = TrafficConstants.getInstance().CARWIDTH;
	double height = TrafficConstants.getInstance().CARHEIGHT;
	boolean carInOtherLane = false;
	float speed = 1;
	boolean changingLanes = false;
	float nextLaneMiddle = 0;
	boolean changeLaneTest = false;
	boolean[] surroundingCarLocations = new boolean[4]; // 0 = right 1 = up 2 = left 3 = down

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
	
	void makeDecision(ArrayList<BoundingBox> carLoc)
	{		
		checkOtherCars(carLoc);
		move();
		changeLane();
	
	}
	
	void move()
	{
		setxCoord(getxCoord() + speed);
		//setxCoord(50);
	}
	
	void slowDown(){
		if(speed != 0){
		speed = (float) (speed - 0.5);
		}
	}
	void speedUp(){
		speed = (float) (speed + 0.5);
	}
	
	void moveOneLaneDown(){
		setyCoord(getyCoord() + speed);
		
	}
	
	void changeLane()
	{	
		if(surroundingCarLocations[0] == true && surroundingCarLocations[3] == false && changingLanes == false){
			
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
		//int yBuffer = 0;
		//method that informs the car of its surroundings. Returns an array of booleans for each cardinal direction
		//the car could attempt to move. This is called before any decision is made
		surroundingCarLocations[0] = false;
		surroundingCarLocations[1] = false;
		surroundingCarLocations[2] = false;
		surroundingCarLocations[3] = false;
		for(int i = 0; i < carLoc.size(); i++)
		{
			if((getBoundingBox().getMaxX() < carLoc.get(i).getMinX() && getBoundingBox().getMaxX()+width > carLoc.get(i).getMinX()
					&& getBoundingBox().getMaxY()>=carLoc.get(i).getMinY() && getBoundingBox().getMinY() <= carLoc.get(i).getMaxY()))
			{
				//right direction, assignment of index 0
				surroundingCarLocations[0] = true;
			} 

			if((getBoundingBox().getMinY() > carLoc.get(i).getMaxY()))
			{
				//up direction, assignment of index 1
				surroundingCarLocations[1] = true;
			}

			if((getBoundingBox().getMinX()) > carLoc.get(i).getMaxX())
			{
				//left direction, assignment of index 2
				surroundingCarLocations[2] = true;			
			}

			if((getBoundingBox().getMaxY() < carLoc.get(i).getMinY() && getBoundingBox().getMaxY()+300 > carLoc.get(i).getMinY()
					&& getBoundingBox().getMaxX()>=carLoc.get(i).getMinX() || getBoundingBox().getMinX() <= carLoc.get(i).getMaxX()))
			{
				//down direction, assignment of index 3
				surroundingCarLocations[3] = true;
			}
			
		}	
		
		return surroundingCarLocations;
	}
	
	public boolean contains(int x, int y)
	{
		return (getBoundingBox().contains(x, y));
	}
	
	public void debug()
	{
		String out = "";
		
		out+="right =" + surroundingCarLocations[0] + "\n";
		out+="up=" + surroundingCarLocations[1] +"\n";
		out+="left="+surroundingCarLocations[2]+"\n";
		out+="down="+surroundingCarLocations[3]+"\n";
		out+="changelane="+changingLanes+"\n";
		JOptionPane.showMessageDialog(TrafficView.view, out);
	}
}















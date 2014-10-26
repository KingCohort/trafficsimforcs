

package moreRefactor;

import java.util.ArrayList;

import javafx.geometry.*;


// creates a car
// the program will create many cars to simulate real traffic
public class Car
{
	double xCoord;
	double yCoord;
	double width;
	double height;
	int carWidth; 
	int carHeight;
	boolean carInOtherLane = false;
	
	float speed = 1;
	
	
	public Car(double xCoord, double yCoord,double width, double height)
	{
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.width = width;
		this.height = height;
	
	}
	

	
	public double getxCoord()
	{
		return xCoord;
	}

	public void setxCoord(double f)
	{
		this.xCoord = f;
	}

	public double getyCoord()
	{
		return yCoord;
	}

	public void setyCoord(double yCoord)
	{
		this.yCoord = yCoord;
	}
	
	void move(double speed)
	{
		setxCoord(getxCoord() + speed);
		//setxCoord(50);
	}
	
	double computeCarLength(){
		
		double carLength = (getxCoord() + width);
		return carLength;
		
	}
	
	double computeCarHeight(){
		double carHeight = (getyCoord() + height);
		return carHeight;
	}
	
	void makeDecision(ArrayList<BoundingBox> carloc){
		
		move(1);
		System.out.println("I made a decision");
		//car.changeLane(car, 1);
	
	}
	
	//void move(ArrayList<BoundingBox> carloc, int speed){
		
	//}
	
	BoundingBox getBoundingBox(){
		
		BoundingBox carBox = new BoundingBox(getxCoord(), getyCoord(), width, height);
		return carBox;
	}
	
	
	void changeLane(ArrayList<BoundingBox> carLoc, int speed)
	{
		
		/* if  (changer.getyCoord() < 250 && carInOtherLane == false)// '/highwayYcoor-(lanesize*0.5)')
		{
		changer.setyCoord(changer.getyCoord() + speed);
		}
		else if (changer.getyCoord() == 250)
		{
			carInOtherLane = true;
			changer.setxCoord(changer.getxCoord() + speed +1);
		}
		if (changer.getxCoord() > 960 && changer.getyCoord() >= 150)
		{
			changer.setyCoord(changer.getyCoord() - speed);
		} */
	}
	
	
	boolean[] checkOtherCars(ArrayList<BoundingBox> carLoc){
		
		boolean[] surroundingCarLocations = new boolean[4]; // 0 = right 1 = up 2 = left 3 = down
		//method that informs the car of its surroundings. Returns an array of booleans for each cardinal direction
		//the car could attempt to move. This is called before any decision is made
		for(int i = 0; i < carLoc.size(); i++){
				
			if((getBoundingBox().getMaxX() + computeCarLength()) > carLoc.get(i).getMinX()){
				
				//right direction, assignment of index 0
				surroundingCarLocations[0] = true;
				
			} else if((getBoundingBox().getMaxX() + computeCarLength()) < carLoc.get(i).getMinX()){
				
				//right direction, assignment of index 0
				surroundingCarLocations[0] = false;
				
			} else if((getBoundingBox().getMinY() - computeCarHeight()) < carLoc.get(i).getMaxY()){
				
				//up direction, assignment of index 1
				surroundingCarLocations[1] = true;
				
			} else if((getBoundingBox().getMinY() + computeCarHeight()) > carLoc.get(i).getMaxY()){
			
				//up direction, assignment of index 1
				surroundingCarLocations[1] = false;
				
			} else if((getBoundingBox().getMinX() - computeCarLength()) < carLoc.get(i).getMaxX()){
				
				//left direction, assignment of index 2
				surroundingCarLocations[2] = true;			
				
			} else if((getBoundingBox().getMinX() - computeCarLength()) > carLoc.get(i).getMaxX()){
				
				//left direction, assignment of index 2
				surroundingCarLocations[2] = false;	
			} else if((getBoundingBox().getMaxY() - computeCarLength() < carLoc.get(i).getMinY())){
				
				//down direction, assignment of index 3
				surroundingCarLocations[3] = true;
				
			} else if((getBoundingBox().getMaxY() - computeCarLength() > carLoc.get(i).getMinY()))
				
				//down direction, assignment of index 3
				surroundingCarLocations[3] = false;
		}	
			
		 return surroundingCarLocations;
	}
}















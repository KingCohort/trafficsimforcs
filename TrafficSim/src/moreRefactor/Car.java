

package moreRefactor;

import java.util.ArrayList;

import javafx.geometry.*;


// creates a car
// the program will create many cars to simulate real traffic
public class Car
{
	float xCoord;
	float yCoord;
	double width;
	double height;
	int carWidth; 
	int carHeight;
	boolean carInOtherLane = false;
	boolean carActive = false; //each car has this paramter, determines if it can collide/is on view  
	int RIGHT = 0;
	int UP = 1;
	int LEFT = 2;
	int DOWN = 3;
	float speed = 1;


	public Car(float xCoord, float yCoord,double width, double height)
	{
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.width = width;
		this.height = height;

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

	void speedUp(float speed)
	{
		setxCoord(getxCoord() + speed);
		//setxCoord(50);
	}

	void slowDown(float speed){

		setxCoord(getxCoord() - speed);

	}

	void stop(float speed){		
		speed = 0;
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

		speedUp(1);
		System.out.println("I made a decision");
		if (getxCoord() > 400) {
			changeLane(1);
		}

	}

	BoundingBox getBoundingBox(){

		BoundingBox carBox = new BoundingBox(getxCoord(), getyCoord(), width, height);
		//instance variable
		return carBox;
	}


	void changeLane(float speed)
	{

		/*if  (getyCoord() < 250 && carInOtherLane == false)// '/highwayYcoor-(lanesize*0.5)')
		{
			setyCoord(getyCoord() + speed);
		}
		else if (getyCoord() == 250)
		{
			carInOtherLane = true;
			setxCoord(getxCoord() + speed +1);
		}
		if (getxCoord() > 960 && getyCoord() >= 150)
		{
			setyCoord(getyCoord() - speed);
		} */
	}



	boolean[] checkOtherCars(ArrayList<BoundingBox> carLoc){

		boolean[] surroundingCarLocations = new boolean[4]; // 0 = right 1 = up 2 = left 3 = down
		//method that informs the car of its surroundings. Returns an array of booleans for each cardinal direction
		//the car could attempt to move. This is called before any decision is made
		//simple names (constants) rather than [0] etc
		for(int i = 0; i < carLoc.size(); i++){

			if((getBoundingBox().getMaxX() + computeCarLength()) > carLoc.get(i).getMinX()){

				//right direction, assignment of index 0
				surroundingCarLocations[RIGHT] = true;	
			} 
			if((getBoundingBox().getMaxX() + computeCarLength()) < carLoc.get(i).getMinX()){

				//right direction, assignment of index 0
				surroundingCarLocations[RIGHT] = false;
			} 
			if((getBoundingBox().getMinY() - computeCarHeight()) < carLoc.get(i).getMaxY()){

				//up direction, assignment of index 1
				surroundingCarLocations[UP] = true;
			} 
			if((getBoundingBox().getMinY() + computeCarHeight()) > carLoc.get(i).getMaxY()){

				//up direction, assignment of index 1
				surroundingCarLocations[UP] = false;
			} 
			if((getBoundingBox().getMinX() - computeCarLength()) < carLoc.get(i).getMaxX()){

				//left direction, assignment of index 2
				surroundingCarLocations[LEFT] = true;			
			} 
			if((getBoundingBox().getMinX() - computeCarLength()) > carLoc.get(i).getMaxX()){

				//left direction, assignment of index 2
				surroundingCarLocations[LEFT] = false;	
			} 
			if((getBoundingBox().getMaxY() - computeCarLength() < carLoc.get(i).getMinY())){

				//down direction, assignment of index 3
				surroundingCarLocations[DOWN] = true;
			} 
			if((getBoundingBox().getMaxY() - computeCarLength() > carLoc.get(i).getMinY()))
				
				//down direction, assignment of index 3
				surroundingCarLocations[DOWN] = false;
		}	

		return surroundingCarLocations;
	}
}















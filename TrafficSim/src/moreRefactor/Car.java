

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
	int UP = 1, DOWN = 3, LEFT = 2, RIGHT = 0;
	double width = TrafficConstants.getInstance().CARWIDTH;
	double height = TrafficConstants.getInstance().CARHEIGHT;
	boolean carInOtherLane = false;
	boolean isChangingLanes = false; // true during the act of lane change
	boolean wantToChangeLanes = false; //true when car is going to attempt a lane change
	float nextLaneMiddle = 0;
	boolean changeLaneTest = false;
	boolean[] surroundingCarLocations = new boolean[4]; // 0 = right 1 = up 2 = left 3 = down

	boolean checkFrontTesterBool;

	//The beginnings of personality paramaterization.
	// I was trying to make a car stop when coming within a set distance of the car infront of it. So I just made the section now - Noel 

	float followingDistance = 15;
	float speed = 1;


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

	 void setxCoord(float f)
	{
		this.xCoord = f;
	}

	public float getyCoord()
	{
		return yCoord;
	}

	 void setyCoord(float yCoord)
	{
		this.yCoord = yCoord;
	}

	void setSpeed(float speed){
		
		this.speed = speed;
		
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
		move(carLoc);
		if(wantToChangeLanes){
		changeLane();
		}

	}

	void move(ArrayList<BoundingBox> carLoc)
	{
		if(isChangingLanes == false && checkFront(carLoc) == false){
			xCoord = xCoord + speed;
		} else {
			wantToChangeLanes = true;
			slowDown();
		}
		
		if(isChangingLanes == true){
			changeLane();
		}
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
		if(surroundingCarLocations[RIGHT] == true && surroundingCarLocations[LEFT] == false && isChangingLanes == false){

			System.out.println("changing lanes commenced");
			isChangingLanes = true;
			nextLaneMiddle = getyCoord() + 100;
			speedUp();
		}
		if (isChangingLanes == true && getyCoord() < nextLaneMiddle) {
			moveOneLaneDown();
			System.out.println(getyCoord());
			System.out.println(nextLaneMiddle);
		}
		else if (isChangingLanes == true && getyCoord() >= nextLaneMiddle) {
			isChangingLanes = false;
			System.out.println("I am done changing lanes");

		}
	}

	public boolean checkFront(ArrayList<BoundingBox> carLoc){

		BoundingBox myBB = getBoundingBox();
		double plus = myBB.getMaxX() + followingDistance;
		for(int i = 0; i < carLoc.size(); i++){

			if(carLoc.get(i).contains(plus, getyCoord())){
				checkFrontTesterBool = true;
				return true;

			}

		}


		return false;
	}

	boolean[] checkOtherCars(ArrayList<BoundingBox> carLoc)
	{
		//int yBuffer = 0;
		//method that informs the car of its surroundings. Returns an array of booleans for each cardinal direction
		//the car could attempt to move. This is called before any decision is made
		surroundingCarLocations[RIGHT] = false;
		surroundingCarLocations[UP] = false;
		surroundingCarLocations[LEFT] = false;
		surroundingCarLocations[DOWN] = false;
		for(int i = 0; i < carLoc.size(); i++)
		{
			if((getBoundingBox().getMaxX() < carLoc.get(i).getMinX() && getBoundingBox().getMaxX()+width > carLoc.get(i).getMinX()
					&& getBoundingBox().getMaxY()>=carLoc.get(i).getMinY() && getBoundingBox().getMinY() <= carLoc.get(i).getMaxY()))
			{
				//right direction, assignment of index 0
				surroundingCarLocations[RIGHT] = true;
			} 

			if((getBoundingBox().getMinY() > carLoc.get(i).getMaxY()))
			{
				//up direction, assignment of index 1
				surroundingCarLocations[UP] = true;
			}

			if((getBoundingBox().getMinX()) > carLoc.get(i).getMaxX())
			{
				//left direction, assignment of index 2
				surroundingCarLocations[LEFT] = true;			
			}

			if((getBoundingBox().getMaxY() < carLoc.get(i).getMinY() && getBoundingBox().getMaxY()+300 > carLoc.get(i).getMinY()
					&& getBoundingBox().getMaxX()>=carLoc.get(i).getMinX() || getBoundingBox().getMinX() <= carLoc.get(i).getMaxX()))
			{
				//down direction, assignment of index 3
				surroundingCarLocations[DOWN] = true;
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

		out+="right =" + surroundingCarLocations[RIGHT] + "\n";
		out+="up=" + surroundingCarLocations[UP] +"\n";
		out+="left="+surroundingCarLocations[LEFT]+"\n";
		out+="down="+surroundingCarLocations[DOWN]+"\n";
		out+="changelane="+isChangingLanes+"\n";
		out+= "XY Coord=" + getxCoord() + "," + getyCoord()+"\n";
		out+="The checkFront method is outputting: " + checkFrontTesterBool;
		JOptionPane.showMessageDialog(TrafficView.view, out);
	}
}

















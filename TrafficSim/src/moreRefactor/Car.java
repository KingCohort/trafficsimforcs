

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
	BoundingBox myBoundingBox = getBoundingBox();

	int arrayValue;
	ArrayList<BoundingBox> carSurroundingBB = new ArrayList<BoundingBox>();

	boolean checkFrontTesterBool;

	//The beginnings of personality paramaterization.
	// I was trying to make a car stop when coming within a set distance of the car infront of it. So I just made the section now - Noel 

	float followingDistance = 30;
	float speed = 1;


	public Car(float xCoord, float yCoord, int arrayValue)
	{

		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.arrayValue = arrayValue;


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

	// this is weird
//	double computeCarLength()
//	{
//		double carLength = (getxCoord() + width);
//		return carLength;
//	}
//
//	double computeCarHeight()
//	{
//		double carHeight = (getyCoord() + height);
//		return carHeight;
//	}

	BoundingBox getBoundingBox()
	{		
		BoundingBox carBox = new BoundingBox(getxCoord(), getyCoord(), width, height);
		return carBox;
	}

	void makeDecision(ArrayList<BoundingBox> carLoc)
	{	
		surroundingCarLocations = checkOtherCars(carLoc);
		checkFront(carLoc);
		
		if(wantToChangeLanes){
			
			changeLanes();
			move();
			
		} else if(checkFrontTesterBool == true){
			
			slowDown();
		}
			
		move();
	}


	void move()
	{
		xCoord = xCoord + speed;
	}

	void slowDown(){
		if(speed != 0){
			speed = (float) (speed - 0.5);
		}
	}

	void speedUp(){
		speed = (float) (speed + 0.5);
	}


	void changeLanes(){

		if(surroundingCarLocations[UP] == false){
			
			moveUpOneLane();

			
		} else if (surroundingCarLocations[DOWN] == false){
			
			moveDownOneLane();

			
		}
	}


	void moveDownOneLane(){
		if(getyCoord() < TrafficConstants.getInstance().BOTLANESTARTY){
			
			
			yCoord = yCoord + 2;
			xCoord = xCoord + 1;
			

		} else{
			wantToChangeLanes = false;
			return;
		}

	}

	void moveUpOneLane(){
		if(getyCoord() > TrafficConstants.getInstance().TOPLANESTARTY){

			yCoord = yCoord - 2;
			xCoord = xCoord + 1;
				
		} else{
			wantToChangeLanes = false;
			return;
		}			

	}

	public void checkFront(ArrayList<BoundingBox> carLoc){

		BoundingBox myBB = getBoundingBox();
		BoundingBox frontCheckBB = new BoundingBox(myBB.getMaxX() + followingDistance, getyCoord(), 1, height);
		for(int i = 0; i < carLoc.size(); i++){
			//if(carLoc.get(i).contains(plus, getyCoord())){
			if(carLoc.get(i).intersects(frontCheckBB)){
				checkFrontTesterBool = true;
	
			}
		}
		checkFrontTesterBool = false;
	}

	ArrayList<BoundingBox> getSurroundingBoundingBoxs(){

		carSurroundingBB.clear();
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMaxX(), getBoundingBox().getMinY(), width, height)); // right
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX(), (getBoundingBox().getMinY() - height*2), width, height*2)); //up
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX() - height, getBoundingBox().getMinY(), width, height)); // left
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX(), getBoundingBox().getMaxY(), width, height*2)); // down
		//	System.out.println(" Min X: " + carSurroundingBB.get(0).getMinX() + " Min y: " + carSurroundingBB.get(0).getMinY());
		return carSurroundingBB;

	}


	boolean[] checkOtherCars(ArrayList<BoundingBox> carLoc){

		surroundingCarLocations[RIGHT] = false;
		surroundingCarLocations[UP] = false;
		surroundingCarLocations[LEFT] = false;
		surroundingCarLocations[DOWN] = false;
		carSurroundingBB = getSurroundingBoundingBoxs();
		for(int i = 0; i < carLoc.size(); i++){

			if(i == arrayValue){

				//I can't check myself here! 
				System.out.print("Not checking myself so I am not wrecking myself");

			} else{
				if(carSurroundingBB.get(RIGHT).intersects(carLoc.get(i))){

					surroundingCarLocations[RIGHT] = true;

				}
				if(carSurroundingBB.get(UP).intersects(carLoc.get(i))){

					surroundingCarLocations[UP] = true;

				}
				if(carSurroundingBB.get(LEFT).intersects(carLoc.get(i))){

					surroundingCarLocations[LEFT] = true;
				}

				if(carSurroundingBB.get(DOWN).intersects(carLoc.get(i))){

					surroundingCarLocations[DOWN] = true;

				}
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
		out+= "Cars number in the cars Array: " +  arrayValue + "\n";
		out+="The checkFront method is outputting: " + checkFrontTesterBool + "\n";

		JOptionPane.showMessageDialog(TrafficView.view, out);
	}
}



















package moreRefactor;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.geometry.*;

// creates a car
// the program will create many cars to simulate real traffic
public class Car
{
	float xCoord;
	float yCoord;
	int UP = 1, DOWN = 3, LEFT = 2, RIGHT = 0;
	int AGGRESSION = 0, COMFORTABLESPEED = 1, DECISIONMAKING = 2, REACTIONTIME = 3;
	double width = TrafficConstants.getInstance().CARWIDTH;
	double height = TrafficConstants.getInstance().CARHEIGHT;
	boolean carInOtherLane = false;
	boolean isChangingLanes = false; // true during the act of lane change
	boolean wantToChangeLanes = false; //true when car is going to attempt a lane change
	float nextLaneMiddle = 0;
	boolean changeLaneTest = false;
	boolean[] surroundingCarLocations = new boolean[5]; // 0 = right 1 = up 2 = left 3 = down
	BoundingBox[] myPersonalBubble = new BoundingBox[4];
	boolean[] personalBubbleCheckerBools = new boolean[4];
	Random statBehaviorCheck = new Random(); // statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) to get the next random number.
	BoundingBox myBoundingBox = getBoundingBox();

	int arrayValue;
	ArrayList<BoundingBox> carSurroundingBB = new ArrayList<BoundingBox>();

	boolean checkFrontTesterBool;

	//The beginnings of personality paramaterization.
	// I was trying to make a car stop when coming within a set distance of the car infront of it. So I just made the section now - Noel 
	//NOTES:::: THE CAR CLASS DOES NOT SUPPORT THE USE OF WHILE LOOPS. MAY GOD HAVE MERCY ON YOUR SOUL. Just use an if statement 
	//				take a look at the moving lanes methods for an example.

	int aggression;
	int attention = 40;
	int decisionMaking = 50;

	float followingDistanceMin = 30;
	float comfortBubble = 15;
	float reactionTime = 2;
	float comfortableSpeed;
	float currentSpeed = comfortableSpeed;



	public Car(float xCoord, float yCoord, int arrayValue, Object[] personalityValues)
	{

		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.arrayValue = arrayValue;
		aggression = (Integer)personalityValues[AGGRESSION];
		comfortableSpeed = (Float)personalityValues[COMFORTABLESPEED];

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

	void setComfortableSpeed(float speed){

		this.comfortableSpeed = speed;

	}



	void makeDecision(ArrayList<BoundingBox> carLoc)
	{	
		surroundingCarLocations = checkOtherCars(carLoc);
		personalBubbleMaker();
		personalBubbleViolation(carLoc);
		if(!isCrashed(carLoc)){

			move();

			if(personalBubbleCheckerBools[RIGHT] == true){
				slowDown();
				move();
			} else{

				normalizeSpeed();
			}
			
			if(personalBubbleCheckerBools[UP]){

				yCoord = yCoord - 1;
			}

			if(personalBubbleCheckerBools[DOWN]){

				yCoord = yCoord + 1;
			} 

			if(personalBubbleCheckerBools[LEFT]){				
				speedUp();
				move();
			} else{
				normalizeSpeed();
			}

			if(wantToChangeLanes){	
				changeLanes();
				move();
			} 

		}else{

			currentSpeed = 0;
			comfortableSpeed = 0;
		}
	}


	BoundingBox getBoundingBox()
	{		
		BoundingBox carBox = new BoundingBox(getxCoord(), getyCoord(), width, height);
		return carBox;
	}

	//CAR BEHAVIOR METHODS START HERE


	void move()
	{
		xCoord = xCoord + currentSpeed;
	}

	void slowDown(){
		if(currentSpeed > 0){
			currentSpeed = (float) (currentSpeed - 0.1);
			xCoord = xCoord + currentSpeed;
		} else{
			currentSpeed = 1;
		}
		
		if(aggression < statBehaviorCheck.nextInt()){
			
			wantToChangeLanes = true;
			
		}
		
	}

	void speedUp(){
		currentSpeed = (float) (currentSpeed + 0.1);
		xCoord = xCoord + currentSpeed;
	}


	void changeLanes(){

		if(surroundingCarLocations[UP] == false){

			moveUpOneLane();

		} else if (surroundingCarLocations[DOWN] == false){

			moveDownOneLane();

		}
	}

	void normalizeSpeed(){ //makes the car prefer its comfort speed

		if(currentSpeed < comfortableSpeed){			
			speedUp();
		} else if (currentSpeed > comfortableSpeed){			
			slowDown();			
		} else{		
			move();
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


	ArrayList<BoundingBox> getSurroundingBoundingBoxs(){

		carSurroundingBB.clear();
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMaxX(), getBoundingBox().getMinY(), width, height)); // right
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX(), (getBoundingBox().getMinY() - height*2), width, height*2)); //up
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX() - height, getBoundingBox().getMinY(), width, height)); // left
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX(), getBoundingBox().getMaxY(), width, height*2)); // down
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMaxX() + followingDistanceMin, getyCoord(), 1, height)); // directly in front of car
		//carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX() - comfortBubble, getBoundingBox().getMinY() - comfortBubble, width + (2*comfortBubble), height + (2*comfortBubble)));
		//	System.out.println(" Min X: " + carSurroundingBB.get(0).getMinX() + " Min y: " + carSurroundingBB.get(0).getMinY());


		return carSurroundingBB;

	}



	void personalBubbleMaker(){

		myPersonalBubble[UP] = new BoundingBox(getBoundingBox().getMinX() - comfortBubble, getBoundingBox().getMinY() - comfortBubble, width + (2*comfortBubble), 1); 
		myPersonalBubble[LEFT] = new BoundingBox(getBoundingBox().getMinX() - comfortBubble, getBoundingBox().getMinY() - comfortBubble, 1, height + (2*comfortBubble));
		myPersonalBubble[RIGHT] = new BoundingBox(getBoundingBox().getMaxX() + comfortBubble, getBoundingBox().getMinY() - comfortBubble, 1, height + (2*comfortBubble));
		myPersonalBubble[DOWN] = new BoundingBox(getBoundingBox().getMinX() - comfortBubble, getBoundingBox().getMaxY() + comfortBubble, width + (2*comfortBubble), 1 );

		//Making the BBs drawn on the view so I can see them and ensure they're right. LEaving in for debugging purposes 
		/*TrafficModel.model.carBB.add(myPersonalBubble[UP]);
		TrafficModel.model.carBB.add(myPersonalBubble[DOWN]);
		TrafficModel.model.carBB.add(myPersonalBubble[LEFT]);
		TrafficModel.model.carBB.add(myPersonalBubble[RIGHT]);*/
	}

	void personalBubbleViolation(ArrayList<BoundingBox> carLoc){

		personalBubbleCheckerBools[RIGHT] = false;
		personalBubbleCheckerBools[LEFT] = false;
		personalBubbleCheckerBools[UP] = false;
		personalBubbleCheckerBools[DOWN] = false;


		for(int i = 0; i < carLoc.size(); i++){

			for(int j = 0; j < personalBubbleCheckerBools.length; j++){

				if(myPersonalBubble[j].intersects(carLoc.get(i))){
					if(i != arrayValue)
						personalBubbleCheckerBools[j] = true; 

				}
			}	
		}	
	} 


	boolean[] checkOtherCars(ArrayList<BoundingBox> carLoc){

		surroundingCarLocations[RIGHT] = false;
		surroundingCarLocations[UP] = false;
		surroundingCarLocations[LEFT] = false;
		surroundingCarLocations[DOWN] = false;
		carSurroundingBB = getSurroundingBoundingBoxs();
		for(int i = 0; i < carLoc.size(); i++){

			if(i != arrayValue){ //So there is no useless if statement
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

	boolean isCrashed(ArrayList<BoundingBox> carLocs){

		for(int i = 0; i < carLocs.size(); i++){

			if(i == arrayValue){
				//Not checking myself dog

			}else if(getBoundingBox().intersects(carLocs.get(i))){

				return true;
			}
		}
		return false;
	}


	//DEBUG METHODS


	public boolean contains(int x, int y)
	{
		return (getBoundingBox().contains(x, y));
	}

	public void debug()
	{
		String out = "";
		out+="Car Number " + arrayValue + "\n";
		out+="Vision Check Right =" + surroundingCarLocations[RIGHT] + "\n";
		out+="Vision Check Up =" + surroundingCarLocations[UP] +"\n";
		out+="Vision Check Left ="+surroundingCarLocations[LEFT]+"\n";
		out+="Vision Check Down ="+surroundingCarLocations[DOWN]+"\n";
		out+="/////////////////////////////////\n";
		out+="Personal Bubble Top is " + personalBubbleCheckerBools[UP]+"\n" ;
		out+="Personal Bubble Right is " + personalBubbleCheckerBools[RIGHT]+"\n";
		out+="Personal Bubble bottom is " + personalBubbleCheckerBools[DOWN]+"\n";
		out+="Personal Bubble left is " + personalBubbleCheckerBools[LEFT]+"\n";
		out+="///////////////////////////////////\n";
		out+="changelane="+isChangingLanes+"\n";
		out+= "XY Coord=" + getxCoord() + "," + getyCoord()+"\n";
		out+="Have I crashed? " + isCrashed(TrafficModel.model.carBB) + "\n";
		out+="///////////////////Personality Values//////////////////////////////\n";
		out+="Aggression value is " + aggression + "\n";
		out+="Comfortable Speed is " + comfortableSpeed + "\n";
		out+="Current Speed is " + currentSpeed + "\n";

		JOptionPane.showMessageDialog(TrafficView.view, out);
	}

}

















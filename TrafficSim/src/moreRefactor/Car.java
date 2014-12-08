

package moreRefactor;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.geometry.*;

// The car class that creates the car object 
//NOTES:::: THE CAR CLASS DOES NOT SUPPORT THE USE OF WHILE LOOPS. MAY GOD HAVE MERCY ON YOUR SOUL. Just use an if statement 
//				take a look at the moving lanes methods for an example.

public class Car
{
	float xCoord;
	float yCoord;
	int UP = 1, DOWN = 3, LEFT = 2, RIGHT = 0;
	int AGGRESSION = 0, COMFORTABLESPEED = 1, DECISIONMAKING = 3, ATTENTION = 2, BUBBLESIZE = 4, STARTINGLANE = 5;
	double width = TrafficConstants.getInstance().CARWIDTH;
	double height = TrafficConstants.getInstance().CARHEIGHT;
	boolean isChangingLanes = false; // true during the act of lane change
	boolean wantToChangeLanes = false; //true when car is going to attempt a lane change
	boolean changeLaneTest = false;
	boolean[] surroundingCarLocations = new boolean[5]; // 0 = right 1 = up 2 = left 3 = down
	BoundingBox[] myPersonalBubble = new BoundingBox[4];
	boolean[] personalBubbleCheckerBools = new boolean[4];
	boolean testerAttention = false;
	int whatLane; 
	int laneGoal;
	String carDescription;
	String methodRunning;
	Random statBehaviorCheck = new Random(); // statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) to get the next random number.
	BoundingBox myBoundingBox = getBoundingBox();

	int arrayValue;
	ArrayList<BoundingBox> carSurroundingBB = new ArrayList<BoundingBox>();

	boolean checkFrontTesterBool;

	//The beginnings of personality paramaterization.

	double aggression;
	int attention;
	int decisionMaking = 50;

	double comfortBubble;
	float reactionTime = 2;
	float comfortableSpeed;
	float currentSpeed = 1;
	//SpeedAdjust is the number used to adjust all cars' base speeds when the view is fixed over one car.
	//Since all cars move relative to the first car, all car comfortable speeds are reduced by the first car's comfortable speed
	//in the constructors below.
	float speedAdjust = 0;


	//Fixed-location constructor.
	public Car(float xCoord, float yCoord, int arrayValue, Object[] personalityValues, float speedAdjust)
	{

		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.arrayValue = arrayValue;
		this.speedAdjust = speedAdjust;
		aggression = (Double)personalityValues[AGGRESSION];
		comfortableSpeed = (Float)personalityValues[COMFORTABLESPEED];
		attention = (Integer)personalityValues[ATTENTION];
		comfortBubble = (Double)personalityValues[BUBBLESIZE];
		whatLane = (Integer)personalityValues[STARTINGLANE];
		yCoord = 150 + (Integer)personalityValues[STARTINGLANE]* 100;
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			//Fixed-location cars start towards the middle of the highway, to better see cars around them.
			this.xCoord += 960;
			comfortableSpeed = comfortableSpeed - speedAdjust;
		}
	}
	
	//Random-location constructor.
	public Car(int arrayValue, Object[] personalityValues, float speedAdjust)
	{
		//Creates the car just offscreen, then sets its starting Y to the middle of a random lane based on how many are available.
		//Y coordinate must be hardcoded since the lane height and highway start Y are only accessible from within TrafficView at the moment.
		xCoord = TrafficConstants.getInstance().STARTX-TrafficConstants.getInstance().CARWIDTH;
		whatLane = (Integer)personalityValues[STARTINGLANE];
		yCoord = 150 + (Integer)personalityValues[STARTINGLANE]* 100;
		this.arrayValue = arrayValue;
		this.speedAdjust = speedAdjust;
		aggression = (Double)personalityValues[AGGRESSION];
		comfortableSpeed = (Float)personalityValues[COMFORTABLESPEED];
		attention = (Integer)personalityValues[ATTENTION];
		comfortBubble = (Double)personalityValues[BUBBLESIZE];
		
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			if (this.arrayValue == 0) {
				//If this is the first car, have it start at the middle of the highway, to better see cars around it.
				this.xCoord += 960;
			}
			comfortableSpeed = comfortableSpeed - speedAdjust;
		}
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
	



	void makeDecision(ArrayList<BoundingBox> carLoc){	
		surroundingCarLocations = checkOtherCars(carLoc);
		if(attention > statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND)){
			testerAttention = true;
			personalBubbleMaker();
			personalBubbleViolation(carLoc);
		} else{
			testerAttention = false;
		}
		if(!isCrashed(carLoc)){

			move();

			if(personalBubbleCheckerBools[RIGHT]){
				slowDown();
				if(aggression < statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND)){
					wantToChangeLanes = true;
				}

			} else{

				normalizeSpeed();
			}

			if(personalBubbleCheckerBools[UP]){
				moveDownOneLane();
			}

			if(personalBubbleCheckerBools[DOWN]){
				moveUpOneLane();
			} 

			if(personalBubbleCheckerBools[LEFT]){				

				
				if(currentSpeed + 1 < comfortableSpeed){
					
					wantToChangeLanes = true;
					
				}
				
				if(aggression > statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND)){
					
					speedUp();
					
	
				} else{
					
					normalizeSpeed();
				}

			} 

			if(wantToChangeLanes){	
				
				if(aggression > statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND)){
					
					speedUp();
					
				} else{
					
					slowDown();
				}
				
				changeLanes();
			} 

		}else{
			currentSpeed = 0-TrafficConstants.getInstance().MEDIANSPEED;
			comfortableSpeed = 0-TrafficConstants.getInstance().MEDIANSPEED;

			if (arrayValue == 0) {
				TrafficConstants.getInstance().setMEDIANSPEED(0);
			}
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
		
		methodRunning += "- slowing Down";

	}

	void speedUp(){
		currentSpeed = (float) (currentSpeed + 0.1);
		xCoord = xCoord + currentSpeed;
		methodRunning += "- speeding Up";
	}


	void changeLanes(){
		methodRunning = "Has decided to change lanes";
		if(surroundingCarLocations[UP] == false && whatLane != 0){

			whatLane = whatLane + 1;
			
			moveUpOneLane();

		} else if (surroundingCarLocations[DOWN] == false && whatLane != 4){

			moveDownOneLane();

		}
	}

	void normalizeSpeed(){ //makes the car prefer its comfort speed
		methodRunning = "Normalizing its speed";
		if(currentSpeed < comfortableSpeed){			
			speedUp();
		} else if (currentSpeed > comfortableSpeed){			
			slowDown();			
		} 
	}

	void moveDownOneLane(){
		methodRunning += ": Moving down one lane";
		if(getyCoord() < 150 + whatLane * 100){		

			yCoord = yCoord + 2;
			xCoord = xCoord + 1;		

		} else{
			wantToChangeLanes = false;
			return;
		}
	}

	void moveUpOneLane(){
		methodRunning += ": Moving up one lane";
		if(getyCoord() > 150 + whatLane * 100){

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
		/* TrafficModel.model.carBB.add(myPersonalBubble[UP]);
		TrafficModel.model.carBB.add(myPersonalBubble[DOWN]);
		TrafficModel.model.carBB.add(myPersonalBubble[LEFT]);
		TrafficModel.model.carBB.add(myPersonalBubble[RIGHT]); */
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
		methodRunning = "This Car has crashed";

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
		out+="//////////////ABOUT THIS CAR/////////////////////////\n";
		out+="Car Number " + arrayValue + "\n";
		out+= "Method being run: " + methodRunning + "\n";
		out+= "XY Coord=" + getxCoord() + "," + getyCoord()+"\n";	
		out+="Have I crashed? " + isCrashed(TrafficModel.model.carBB) + "\n";
		out+="Am I paying attention?" + testerAttention + "\n";
		out+="Am I changing Lanes? ="+isChangingLanes+"\n";
		out+="Aggression value is " + aggression + "\n";
		out+="Comfortable Speed is " + comfortableSpeed + "\n";
		out+="Current Speed is " + currentSpeed + "\n";
		out+="Personal Bubble Values is " + comfortBubble + "\n";
		out+="//////////////VISION CHECK////////////////////////////\n";
		out+="Vision Check Right =" + surroundingCarLocations[RIGHT] + "\n";
		out+="Vision Check Up =" + surroundingCarLocations[UP] +"\n";
		out+="Vision Check Left ="+surroundingCarLocations[LEFT]+"\n";
		out+="Vision Check Down ="+surroundingCarLocations[DOWN]+"\n";
		out+="/////////////PERSONAL BUBBLE CHECK////////////////////\n";
		out+="Personal Bubble Top is " + personalBubbleCheckerBools[UP]+"\n" ;
		out+="Personal Bubble Right is " + personalBubbleCheckerBools[RIGHT]+"\n";
		out+="Personal Bubble bottom is " + personalBubbleCheckerBools[DOWN]+"\n";
		out+="Personal Bubble left is " + personalBubbleCheckerBools[LEFT]+"\n";

		JOptionPane.showMessageDialog(TrafficView.view, out);
	}

}

















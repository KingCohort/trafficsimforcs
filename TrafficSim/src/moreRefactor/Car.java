

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
	int UP = 1, DOWN = 3, LEFT = 2, RIGHT = 0, TOPDIAG = 4, BOTDIAG = 5;
	int AGGRESSION = 0, COMFORTABLESPEED = 1, WAITCOUNT = 3, ATTENTION = 2, BUBBLESIZE = 4, STARTINGLANE = 5;
	double width = TrafficConstants.getInstance().CARWIDTH;
	double height = TrafficConstants.getInstance().CARHEIGHT;
	boolean wantsToChangeLanes;
	boolean isChangingLanes = false; //true when car is going to attempt a lane change
	boolean wantToMoveUpOneLane = false;
	boolean wantToMoveDownOneLane = false;
	boolean[] surroundingCarLocations = new boolean[6]; // 0 = right 1 = up 2 = left 3 = down 4 = right top daig 5 = right bot diag
	BoundingBox[] myPersonalBubble = new BoundingBox[4];
	boolean[] personalBubbleCheckerBools = new boolean[4];
	boolean testerAttention = false;
	boolean isCrashed;
	int startingLane;
	int laneNumber;
	BoundingBox aheadView;
	String carDescription;
	String methodRunning;
	Random statBehaviorCheck = new Random(); // statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) to get the next random number.
	BoundingBox myBoundingBox = getBoundingBox();
	double leftTrueCount = 0;
	double rightTrueCount = 0;
	double closeTrueCountLeft = 0;
	double carWaitCount;

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
	//inQueue determines whether or not the car is in the creation queue, used if the car is created on top of another.
	boolean inQueue = false;
	boolean leavingQueue = false;
	int queueTimer = 25;


	//Fixed-location constructor.
	public Car(float xCoord, float yCoord, int arrayValue, Object[] personalityValues, float speedAdjust, ArrayList<BoundingBox> carBB)
	{

		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.arrayValue = arrayValue;
		this.speedAdjust = speedAdjust;
		aggression = (Double)personalityValues[AGGRESSION];
		comfortableSpeed = (Float)personalityValues[COMFORTABLESPEED];
		attention = (Integer)personalityValues[ATTENTION];
		comfortBubble = (Double)personalityValues[BUBBLESIZE];
		laneNumber = (Integer)personalityValues[STARTINGLANE];
		yCoord = 160 + (Integer)personalityValues[STARTINGLANE]* 110;
		carWaitCount = (Integer)personalityValues[WAITCOUNT];
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {

			//Fixed-location cars start towards the middle of the highway, to better see cars around them.
			this.xCoord += 960;
			comfortableSpeed = comfortableSpeed - speedAdjust;
		}
		for(int i = 0; i < carBB.size(); i++){

			if(i == arrayValue){

			}else if(getBoundingBox().intersects(carBB.get(i))){
				inQueue = true;
			}
		}
	}

	//Random-location constructor.
	public Car(int arrayValue, Object[] personalityValues, float speedAdjust, ArrayList<BoundingBox> carBB)
	{
		//Creates the car just offscreen, then sets its starting Y to the middle of a random lane based on how many are available.
		//Y coordinate must be hardcoded since the lane height and highway start Y are only accessible from within TrafficView at the moment.
		xCoord = TrafficConstants.getInstance().STARTX-TrafficConstants.getInstance().CARWIDTH -80;
		laneNumber = (Integer)personalityValues[STARTINGLANE];
		startingLane = (Integer)personalityValues[STARTINGLANE];
		yCoord = (Integer)personalityValues[STARTINGLANE]* 110 + 160;
		this.arrayValue = arrayValue;
		this.speedAdjust = speedAdjust;
		aggression = (Double)personalityValues[AGGRESSION];
		comfortableSpeed = (Float)personalityValues[COMFORTABLESPEED];
		attention = (Integer)personalityValues[ATTENTION];
		comfortBubble = (Double)personalityValues[BUBBLESIZE];
		carWaitCount = (Double)personalityValues[WAITCOUNT];
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			if (this.arrayValue == 0) {
				//If this is the first car, have it start at the middle of the highway, to better see cars around it.
				this.xCoord += 960;
			}
			comfortableSpeed = comfortableSpeed - speedAdjust;
		}
		for(int i = 0; i < carBB.size(); i++){

			if(i == arrayValue){
				//Check yourself before you shrek yourself

			}else if(getBoundingBox().intersects(carBB.get(i))){
				inQueue = true;
			}
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


	String printCarStats(){

		return "Car: " + arrayValue + "\n \t \t Aggression value is " + aggression + "\n \t \t" + "Comfortable Speed is " + comfortableSpeed + "\n \t \t";

	}

	void makeDecision(ArrayList<BoundingBox> carLoc, Car[] cars){
		methodRunning = "";
		if (!leavingQueue) {
			surroundingCarLocations = checkOtherCars(carLoc);
			personalBubbleMaker();
			personalBubbleViolation(carLoc);
			if(!isIntersectingOtherCar(cars)){
				stayInMyLane();
				if(!isChangingLanes){
					checkLaneNum();
				}

				if(isThisLaneStopped(laneNumber, cars)){

					wantsToChangeLanes = true;
				} 

				if(wantsToChangeLanes){
					if(isChangingLanes){
						if(wantToMoveDownOneLane){
							moveDownOneLane();
						} else if(wantToMoveUpOneLane){
							moveUpOneLane();
						}else{
							changeLaneCalculation(cars);
						}
					} else{
						changeLaneCalculation(cars);
					}
				}

				if(personalBubbleCheckerBools[UP] == true){
					speedUp();
					changeLaneCalculation(cars);
					isChangingLanes = true;

				}

				if(surroundingCarLocations[UP] == true){

					rightTrueCount = 0;
					leftTrueCount = 0;
					closeTrueCountLeft =0;
					wantsToChangeLanes = false;

				}

				if(surroundingCarLocations[DOWN] == true){

					rightTrueCount = 0;
					leftTrueCount = 0;
					closeTrueCountLeft =0;
					wantsToChangeLanes = false;

				}

				if(surroundingCarLocations[TOPDIAG] == true){
					if(isChangingLanes){
						if(wantToMoveUpOneLane){
							wantToMoveUpOneLane = false;
							if( laneNumber != TrafficConstants.getInstance().getLANENUM() - 1 ){
								wantToMoveDownOneLane = true;
							}
						}
					}

				}

				if(surroundingCarLocations[BOTDIAG] == true){
					if(isChangingLanes){
						if(wantToMoveDownOneLane){
							wantToMoveDownOneLane = false;
							if(laneNumber != 0){
								wantToMoveUpOneLane = true;
							}
						}
					}
				}

				if(personalBubbleCheckerBools[DOWN]==true){

					speedUp();
					changeLaneCalculation(cars);
					isChangingLanes = true;

				}
				if(surroundingCarLocations[RIGHT] == true){				
					slowDown();
					if(currentSpeed < 1){

						wantsToChangeLanes = true;

					}
					if((statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) < aggression)){
						rightTrueCount++;
						if(surroundingCarLocations[TOPDIAG]){
							if(rightTrueCount > carWaitCount){						
								wantsToChangeLanes = true;
								rightTrueCount = 0;
							}
						}

					} else{							
						slowDown();						
					}
				} else{
					rightTrueCount = 0;
					normalizeSpeed();
				}

				if(surroundingCarLocations[LEFT] == true){


					if(statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) > aggression){
						leftTrueCount++;						
						if((leftTrueCount > carWaitCount)){						
							wantsToChangeLanes = true;
							leftTrueCount = 0;
						} else{							
							speedUp();						
						}			
					}
				}else{
					leftTrueCount = 0;
				}
				if(personalBubbleCheckerBools[RIGHT] == true){
					slowDown();
					slowDown();

				}
				if(personalBubbleCheckerBools[LEFT] == true){
					speedUp();		
					if((statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) < aggression)){
						closeTrueCountLeft++;
						if(currentSpeed > comfortableSpeed){
							if(closeTrueCountLeft > carWaitCount){						
								wantsToChangeLanes = true;
								closeTrueCountLeft = 0;
							}
						} else{
							speedUp();
						}
					}
				} else{
					closeTrueCountLeft = 0;
				}


				move();
			} else{
				currentSpeed = 0-TrafficConstants.getInstance().MEDIANSPEED;
				comfortableSpeed = 0-TrafficConstants.getInstance().MEDIANSPEED;
				isChangingLanes = false;
				wantToMoveUpOneLane = false;
				wantToMoveDownOneLane = false;

			}
		} else {
			//Let car go after 1 second of waiting.
			queueTimer -= 1;
			if (queueTimer==0) {
				queueTimer = 25;
				leavingQueue = false;
				if(!surroundingCarLocations[RIGHT]){
					normalizeSpeed();
				} else{
					currentSpeed = 1;
				}
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
		//If looping mode is on, return car to start of highway once it disappears off right edge.
		if (TrafficConstants.getInstance().getLOOPING() == true && getxCoord() > 1920) {
			//-20 because reasons
			xCoord = TrafficConstants.getInstance().STARTX-TrafficConstants.getInstance().CARWIDTH-100;
			inQueue = true;
		}
		else {
			xCoord = xCoord + currentSpeed;
		}

	}

	void slowDown(){
		if(currentSpeed > 0){
			currentSpeed = (float)(currentSpeed - .5);
		} else{
			currentSpeed = 1;
		}
		methodRunning += "slowing down";

	}

	void speedUp(){
		currentSpeed = (float) (currentSpeed + .5);
		methodRunning += "speeding up";

	}

	void stayInMyLane(){

		if(!isChangingLanes){

			if(yCoord > ((laneNumber) * 110) + 160){

				yCoord = yCoord - 1;

			} else if(yCoord < ((laneNumber) * 110) + 160){

				yCoord = yCoord + 1;

			}

		}

	}

	void changeLaneCalculation(Car[] cars){
		methodRunning = "Changing lanes by- ";
		if(laneNumber != 0){
			if(surroundingCarLocations[UP] == false){
				if(surroundingCarLocations[TOPDIAG] == false){
					isChangingLanes = true;
					wantToMoveUpOneLane = true;
				}
			}
		} 
		if(laneNumber != TrafficConstants.getInstance().getLANENUM() - 1){
			if(surroundingCarLocations[DOWN] == false){
				if(surroundingCarLocations[BOTDIAG]){
					isChangingLanes = true;
					wantToMoveDownOneLane = true;
				}

			}
		}
	}

	void normalizeSpeed(){ //makes the car prefer its comfort speed
		methodRunning = "Normalizing its speed by- ";
		if(currentSpeed  < comfortableSpeed){			
			speedUp();
		} else if (currentSpeed > comfortableSpeed){			
			slowDown();			
		} 
	}

	void moveDownOneLane(){
		methodRunning += " Moving from " + laneNumber + " to " + (laneNumber + 1);
		if(getyCoord() <= ((laneNumber+1) * 110) + 160){	
			yCoord = yCoord + 2;
			xCoord = xCoord +1;
			rightTrueCount = 0;
			leftTrueCount = 0;
			closeTrueCountLeft =0;


		} else{
			isChangingLanes = false;
			wantToMoveDownOneLane = false;
			return;
		}
	}

	void moveUpOneLane(){
		methodRunning += " Moving from " + laneNumber + " to " + (laneNumber - 1);
		if(getyCoord() >= ((laneNumber-1) * 110) + 160){
			yCoord = yCoord - 2;
			xCoord = xCoord + 1;
			rightTrueCount = 0;
			leftTrueCount = 0;
			closeTrueCountLeft =0;


		} else{	 
			isChangingLanes = false;
			wantToMoveUpOneLane = false;
			return;
		}			 
	}

	void checkLaneNum(){

		if(getyCoord() >= 130 && getyCoord() <= 230){

			laneNumber = 0;

		} else if (getyCoord() >= 240 && getyCoord() <= 340){

			laneNumber = 1;

		} else if(getyCoord() >=350 && getyCoord() <= 450){

			laneNumber = 2;
		} else if(getyCoord() >= 460 && getyCoord() <= 560){

			laneNumber = 3;
		} else if(getyCoord() >=570 && getyCoord() <= 670){

			laneNumber = 4;
		}

	}

	boolean isThisLaneStopped(int laneNumber, Car[] cars){
		for(Car car:cars){
			if(car.arrayValue != arrayValue){
				if(car.isCrashed){
					if(car.laneNumber == laneNumber){
						return true;
					}
				}
			}

		}

		return false;
	}

	ArrayList<BoundingBox> getSurroundingBoundingBoxs(){

		carSurroundingBB.clear();
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMaxX(), getBoundingBox().getMinY(), width, height)); // right
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX(), (getBoundingBox().getMinY() - height*2), width, height*2)); //up
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX() - height, getBoundingBox().getMinY(), width, height)); // left
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX(), getBoundingBox().getMaxY(), width, height*2)); // down
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX() + width, (getBoundingBox().getMinY() - height), width, height)); // upper right diag
		carSurroundingBB.add(new BoundingBox(getBoundingBox().getMinX() + width, getBoundingBox().getMaxY(), width, height)); // down right diag
	



		return carSurroundingBB;

	}

	void personalBubbleMaker(){

		myPersonalBubble[UP] = new BoundingBox(getBoundingBox().getMinX() - comfortBubble, getBoundingBox().getMinY() - comfortBubble, width + (2*comfortBubble), 1); 
		myPersonalBubble[LEFT] = new BoundingBox(getBoundingBox().getMinX() - comfortBubble, getBoundingBox().getMinY() - comfortBubble, 1, height + (2*comfortBubble));
		myPersonalBubble[RIGHT] = new BoundingBox(getBoundingBox().getMaxX() + comfortBubble, getBoundingBox().getMinY() - comfortBubble, 1, height + (2*comfortBubble));
		myPersonalBubble[DOWN] = new BoundingBox(getBoundingBox().getMinX() - comfortBubble, getBoundingBox().getMaxY() + comfortBubble, width + (2*comfortBubble), 1 );

		//Making the BBs drawn on the view so I can see them and ensure they're right. Leaving in for debugging purposes 
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
		surroundingCarLocations[TOPDIAG] = false;
		surroundingCarLocations[BOTDIAG] = false;
		carSurroundingBB = getSurroundingBoundingBoxs();
		for(int i = 0; i < carLoc.size(); i++){

			if(i != arrayValue){
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
				if(carSurroundingBB.get(TOPDIAG).intersects(carLoc.get(i))){

					surroundingCarLocations[TOPDIAG] = true;
				}

				if(carSurroundingBB.get(BOTDIAG).intersects(carLoc.get(i))){

					surroundingCarLocations[BOTDIAG] = true;
				}
			}
		}

		return surroundingCarLocations;

	}

	public String toString(){

		return "Car Number: " + arrayValue + "- Current Speed: " + currentSpeed + " and " + methodRunning;

	}

	boolean isIntersectingOtherCar(Car[] cars){
		//Checks if there are cars that aren't in the queue intersecting this car.
		//Used for crash detection and queues.
		for(int i = 0; i < cars.length; i++){

			if(i == arrayValue){
				//Not checking myself dog

			}else if(!cars[i].inQueue){
				if(getBoundingBox().intersects(cars[i].getBoundingBox())){
					isCrashed = true;
					return true;
				}
			}
		}
		isCrashed = false;
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
		out+="Car Number: " + arrayValue + "\n";
		out+= "XY Coord= " + getxCoord() + "," + getyCoord() + "\n";	
		out+="Starting Lane = " + startingLane + "\n";
		out+="Lane Number = " + laneNumber + "\n";
		out+="Has Crashed = " + isIntersectingOtherCar(TrafficModel.model.cars) + "\n";
		out+= "Crash In Lane = " + isThisLaneStopped(laneNumber, TrafficModel.model.cars) + "\n";
		out+= "Method being run: " + methodRunning + "\n";
		out+="Aggression value = " + aggression + "\n";
		out+="Comfortable Speed = " + comfortableSpeed + "\n";
		out+="Current Speed = " + currentSpeed + "\n";
		out+="Personal Bubble distance from car = " + comfortBubble + "\n";
		out+= "Attempting Lane Change = " + wantsToChangeLanes + "\n";
		out+="Currently Changing Lanes =" + isChangingLanes+ "\n";
		out+="//////////////VISION CHECK////////////////////////////\n";
		out+="Vision Check Right = " + surroundingCarLocations[RIGHT] + "\n";
		out+="Vision Check Up = " + surroundingCarLocations[UP] +"\n";
		out+="Vision Check Left = " + surroundingCarLocations[LEFT]+"\n";
		out+="Vision Check Down = " + surroundingCarLocations[DOWN]+"\n";
		out+="Vision Check Top Diagonal = " + surroundingCarLocations[TOPDIAG]+"\n";
		out+="Vision Check Bottom Diagonal = " + surroundingCarLocations[BOTDIAG]+"\n";
		out+="/////////////PERSONAL BUBBLE CHECK////////////////////\n";
		out+="Personal Bubble Top = " + personalBubbleCheckerBools[UP]+"\n" ;
		out+="Personal Bubble Right = " + personalBubbleCheckerBools[RIGHT]+"\n";
		out+="Personal Bubble bottom = " + personalBubbleCheckerBools[DOWN]+"\n";
		out+="Personal Bubble left = " + personalBubbleCheckerBools[LEFT]+"\n";

		JOptionPane.showMessageDialog(TrafficView.view, out);
	}



}

















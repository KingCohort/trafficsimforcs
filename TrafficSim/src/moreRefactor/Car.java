

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
	boolean isChangingLanes = false; //true when car is going to attempt a lane change
	boolean wantToMoveUpOneLane = false;
	boolean wantToMoveDownOneLane = false;
	boolean[] surroundingCarLocations = new boolean[5]; // 0 = right 1 = up 2 = left 3 = down
	BoundingBox[] myPersonalBubble = new BoundingBox[4];
	boolean[] personalBubbleCheckerBools = new boolean[4];
	boolean testerAttention = false;
	int startingLane;
	int laneNumber;
	BoundingBox aheadView;
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
		xCoord = TrafficConstants.getInstance().STARTX-TrafficConstants.getInstance().CARWIDTH;
		laneNumber = (Integer)personalityValues[STARTINGLANE];
		startingLane = (Integer)personalityValues[STARTINGLANE];
		yCoord = (Integer)personalityValues[STARTINGLANE]* 110 + 160;
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

	/*	void makeDecision(ArrayList<BoundingBox> carLoc, Car[] cars){

		methodRunning = "";
		if (!leavingQueue) {
			surroundingCarLocations = checkOtherCars(carLoc);
			personalBubbleMaker();
			personalBubbleViolation(carLoc);
			if(!isChangingLanes){
				checkLaneNum();
			}
			move();
			if(!isIntersectingOtherCar(cars)){

				if(isThisLaneStopped(laneNumber, cars)){
					currentSpeed = 1;
					if(wantToMoveDownOneLane){
						moveDownOneLane();
					} 

					if(wantToMoveUpOneLane){	
						moveUpOneLane();
					} else{

						wantChangeLanes(cars);	
					}


				} else{
					if(isChangingLanes){

						if(wantToMoveDownOneLane){
							moveDownOneLane();
						} 

						if(wantToMoveUpOneLane){	
							moveUpOneLane();
						} else{

							wantChangeLanes(cars);	
						}
					}
					if(surroundingCarLocations[RIGHT]){				
						slowDown();	
						for(Car car: cars){
							if(carSurroundingBB.get(RIGHT).intersects(car.getBoundingBox())){
								slowDown();
								if(car.currentSpeed > this.currentSpeed){	
									if(aggression >= statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND)){						
										isChangingLanes = true;						
									}									

								} 
							}
						}

					} else{

						normalizeSpeed();
					}

					if(personalBubbleCheckerBools[RIGHT]){
						slowDown();
						if(aggression >= statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND)){						
							isChangingLanes = true;						
						}	
					}

					if(surroundingCarLocations[LEFT]){
						if(!surroundingCarLocations[RIGHT]){
							speedUp();
						} else{

							isChangingLanes = true;

						}
						for(Car car: cars){
							if(carSurroundingBB.get(LEFT).intersects(car.getBoundingBox())){							
								if(car.currentSpeed > this.currentSpeed){								
									isChangingLanes = true;
								} 
							}


						}

					}

					if(personalBubbleCheckerBools[LEFT]){						
						isChangingLanes = true;
					} else{

						normalizeSpeed();
					}


				}
			}else{

				currentSpeed = 0;
			}

		}else {
			//Let car go after 1 second of waiting.
			queueTimer -= 1;
			if (queueTimer==0) {
				queueTimer = 25;
				leavingQueue = false;
				currentSpeed = 1;
			}
		}

	} */

	void makeDecision(ArrayList<BoundingBox> carLoc, Car[] cars){
		methodRunning = "";
		if (!leavingQueue) {
			checkLaneNum();
			surroundingCarLocations = checkOtherCars(carLoc);
			personalBubbleMaker();
			personalBubbleViolation(carLoc);
			if(!isIntersectingOtherCar(cars)){
				if(!isChangingLanes){
					checkLaneNum();
				}			
				if(isThisLaneStopped(laneNumber, cars)){	
					isChangingLanes = true;
					changeLaneCalculation(cars);
					if(wantToMoveDownOneLane){
						moveDownOneLane();
					} else if(wantToMoveUpOneLane){
						moveUpOneLane();
					} else{
						slowDown();
					}
					move();	
				} else{
					normalizeSpeed();
					if(surroundingCarLocations[LEFT]){
						if(!surroundingCarLocations[RIGHT]){
							if(statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) < aggression){
								speedUp();
							} else{			
								for(Car car: cars){							
									if(car.getBoundingBox().intersects(carSurroundingBB.get(LEFT))){								
										if(car.currentSpeed > currentSpeed){
											isChangingLanes = true;
										}								
									}					
								}
							} 
						}else{			
							for(Car car: cars){							
								if(car.getBoundingBox().intersects(carSurroundingBB.get(LEFT))){								
									if(car.currentSpeed > currentSpeed){
										isChangingLanes = true;
									}								
								}					
							}
						}	
						
						
						
						
					}				
					if(surroundingCarLocations[RIGHT]){					
						if(personalBubbleCheckerBools[RIGHT]){						
							if((statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) < aggression - (comfortableSpeed - currentSpeed))){							
								isChangingLanes = true;						
							} else{
								slowDown();
							}
						}
					} else{
						if((statBehaviorCheck.nextInt(TrafficConstants.getInstance().UPPERBOUND) < aggression)){
							speedUp();
						} 				
					}

					if(isChangingLanes){	
						if(wantToMoveDownOneLane){
							moveDownOneLane();
						} else if(wantToMoveUpOneLane){
							moveUpOneLane();
						}else{
							changeLaneCalculation(cars);
						}
						move();	
					}
				}

				move();
			} else{
				currentSpeed = 0-TrafficConstants.getInstance().MEDIANSPEED;
                comfortableSpeed = 0-TrafficConstants.getInstance().MEDIANSPEED;

			}
		} else {
			//Let car go after 1 second of waiting.
			queueTimer -= 1;
			if (queueTimer==0) {
				queueTimer = 25;
				leavingQueue = false;
				currentSpeed = 1;
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
			xCoord = TrafficConstants.getInstance().STARTX-TrafficConstants.getInstance().CARWIDTH-40;
			inQueue = true;
		}
		else {
			xCoord = xCoord + currentSpeed;
		}

	}

	void slowDown(){
		if(currentSpeed > 0){
			currentSpeed = (float)(currentSpeed - .5);
			xCoord = xCoord + currentSpeed;
		} else{
			currentSpeed = 1;
		}

		methodRunning += "- slowing Down";

	}

	void speedUp(){
		currentSpeed = (float) (currentSpeed + .5);
		xCoord = xCoord + currentSpeed;
		methodRunning += "- speeding Up";

	}


	void changeLaneCalculation(Car[] cars){

		if(laneNumber != 0){
			if(surroundingCarLocations[UP] == false){
				if(!isThisLaneStopped(laneNumber-1, cars)){
					wantToMoveUpOneLane = true;
				}
			}

		} 
		if(laneNumber != 4){
			if(surroundingCarLocations[DOWN] == false){
				if(!isThisLaneStopped(laneNumber+1, cars)){
					wantToMoveDownOneLane = true;
				}
			}
		}
	}

	void normalizeSpeed(){ //makes the car prefer its comfort speed
		methodRunning = "Normalizing its speed";
		if(currentSpeed+1 < comfortableSpeed){			
			speedUp();
		} else if (currentSpeed-1 > comfortableSpeed){			
			slowDown();			
		} 
	}

	void moveDownOneLane(){
		methodRunning += ": Moving from " + laneNumber + " to " + (laneNumber + 1);
		if(getyCoord() >= ((laneNumber+1) * 110) + 160){		

			yCoord = yCoord + 2;
			xCoord = xCoord + 1;		

		} else{
			isChangingLanes = false;
			wantToMoveDownOneLane = false;
			return;
		}
	}

	void moveUpOneLane(){
		methodRunning += ": Moving from " + laneNumber + " to" + (laneNumber - 1);
		if(getyCoord() <= ((laneNumber-1) * 110) + 160){

			yCoord = yCoord - 2;
			xCoord = xCoord + 1;

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
     boolean isLaneStopped = false;
		for(Car car : cars){
			if(car.isIntersectingOtherCar(cars)){
				if(car.laneNumber == laneNumber){
					isLaneStopped = true;

				}
			} 
		}
		
		return isLaneStopped;
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




	public String toString(){

		return "Car Number: " + arrayValue + "- Current Speed: " + currentSpeed + " and " + methodRunning;

	}

	boolean isIntersectingOtherCar(Car[] cars){
		//Checks if there are cars that aren't in the queue intersecting this car.
		//Used for crash detection and queues.
		for(int i = 0; i < cars.length; i++){

			if(i == arrayValue){
				//Not checking myself dog

			}else if(getBoundingBox().intersects(cars[i].getBoundingBox()) && !cars[i].inQueue){
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
		out+="Have I crashed? " + isIntersectingOtherCar(TrafficModel.model.cars) + "\n";
		out+= "Is lane stopped? " + isThisLaneStopped(laneNumber, TrafficModel.model.cars) + "\n";
		out+="Am I paying attention?" + testerAttention + "\n";
		out+="Am I changing Lanes? ="+isChangingLanes+"\n";
		out+="Starting Lane: " + startingLane + "\n";
		out+="What lane am I in? " + laneNumber + "\n";
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

















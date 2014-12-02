

package moreRefactor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.BoundingBox;

public class TrafficModel
{

	Car[] cars = new Car[5];
	//Car[] cars = new Car[TrafficConstants.getInstance().getCARNUM()];
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	int AGGRESSION = 0, COMFORTABLESPEED = 1, DECISIONMAKING = 3, ATTENTION = 2, BUBBLESIZE = 4;
	Boolean simulation = true;
	public static TrafficModel model = new TrafficModel();
	public Car debuggedCar;
	public Object[] personalityValues = new Object[5];


	Random r = new Random();


	public TrafficModel()
	{
		model = this;
	}

	public void createCars()
	{

		//WHEN TESTING MAKE SURE THE CARS ARE NOT ON TOP OF EACHOTHER, WE HAVENT FIXED THAT YET

		/*	for(int i = 0; i < TrafficConstants.getInstance().getCARNUM(); i++)
		{
			if(i < (TrafficConstants.getInstance().getCARNUM() / 2))
			{
				System.out.println("----- CAR NUMBER: " + TrafficConstants.getInstance().getCARNUM() + " i: " + i);
				cars[i] = new Car(TrafficConstants.getInstance().STARTX, TrafficConstants.getInstance().TOPLANESTARTY, i);
			}
			else
			{
				System.out.println("----- CAR NUMBER: " + TrafficConstants.getInstance().getCARNUM() + " i: " + i);
				cars[i] = new Car(TrafficConstants.getInstance().STARTX, TrafficConstants.getInstance().BOTLANESTARTY, i);
			}
		}  

		 */

		Object[] firstCarPersonality = PersonalityGenerator();
		float speedAdjust = 0;
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			speedAdjust = (float) firstCarPersonality[COMFORTABLESPEED];
		}
		cars[0] = new Car(TrafficConstants.getInstance().STARTX+50, TrafficConstants.getInstance().BOTLANESTARTY, 0, firstCarPersonality, speedAdjust);
		cars[1] = new Car(TrafficConstants.getInstance().STARTX+50, TrafficConstants.getInstance().TOPLANESTARTY, 1, PersonalityGenerator(), speedAdjust);
		cars[2] = new Car(TrafficConstants.getInstance().STARTX + 150, TrafficConstants.getInstance().BOTLANESTARTY, 2, PersonalityGenerator(), speedAdjust);
		cars[3] = new Car(TrafficConstants.getInstance().STARTX + 150, TrafficConstants.getInstance().TOPLANESTARTY, 3, PersonalityGenerator(), speedAdjust);
		//The fifth car below demonstrates the new random-location car constructor.
		cars[4] = new Car(4, PersonalityGenerator(), speedAdjust);
		
		TrafficConstants.getInstance().isModelReady = true;
	}


	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> runSimulation()
	{	
		carBB.clear();
		for (Car car : cars)
		{
			carBB.add(new BoundingBox(car.xCoord,car.yCoord,car.width,car.height));
		}
		//These lines involving firstCarPosition and firstCarDifference are intended to
		//make the other cars move in relation to the first car moving horizontally if the view is fixed on it.
		//If the first car moves forward, the other cars will move backwards an equal amount, and vice versa.
		int firstCarPosition = 0;
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			firstCarPosition = (int) carBB.get(0).getMinX();
		}
		for(Car car: cars){
			car.makeDecision(carBB); 
		}
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			int firstCarDifference = firstCarPosition - (int) carBB.get(0).getMinX();
			for(Car car: cars) {
				car.setxCoord(car.getxCoord() + firstCarDifference);
			}
		}
		return carBB;
	}

	public Object[] PersonalityGenerator(){ // We're using THIS method to get the values http://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml


		double aggression = 101;
			do {
				aggression = Math.round(r.nextGaussian() * (100 - TrafficConstants.getInstance().getAGGRESSION()) + TrafficConstants.getInstance().getAGGRESSION());
			} while(aggression < 0 || aggression > 100);
		
		personalityValues[AGGRESSION] = aggression;
		float comfortableSpeed = (float) (TrafficConstants.getInstance().BASESPEED + (aggression / 10));

		personalityValues[COMFORTABLESPEED] = comfortableSpeed;
		
		int attention = r.nextInt(101-85) + 85;
		
		personalityValues[ATTENTION] = attention;

		double bubbleSize = TrafficConstants.getInstance().defaultBubbleSize;
		if(aggression < 50){
			
			bubbleSize = bubbleSize + (comfortableSpeed);
			
		} else{
			
			bubbleSize = bubbleSize - comfortableSpeed;
			
		}
		
		personalityValues[BUBBLESIZE] = bubbleSize;
		
		return personalityValues;
	}

	public void start()
	{
		createCars();
		do{

		}while(TrafficConstants.getInstance().isModelReady == false);
		carBB = runSimulation();	
	
	}
	
	public boolean personalityValueChecker(int personalityTrait, int value){
		
		if(personalityTrait == AGGRESSION){
			
			if(value > 100 || value < 0){
				
				return false;
			} 
			
		} else if (personalityTrait == ATTENTION){
			
			if(value > 100 || value < 85){
				
				return false;
				
			}
		} else if(personalityTrait == COMFORTABLESPEED){
			
			
		} else if(personalityTrait == BUBBLESIZE){
			
			
		}
		
		return true;
	}

	public void debugCarAt(int x, int y)
	{
		for (Car car : cars)
		{
			if (car.contains(x, y))
			{
				debuggedCar = car;
				car.debug();
			}
		}
	}

}


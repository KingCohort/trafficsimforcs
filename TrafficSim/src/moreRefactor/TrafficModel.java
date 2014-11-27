

package moreRefactor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.BoundingBox;

public class TrafficModel
{
	
	Car[] cars = new Car[4];
	//Car[] cars = new Car[TrafficConstants.getInstance().getCARNUM()];
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	int AGGRESSION = 0, COMFORTABLESPEED = 1, DECISIONMAKING = 2, REACTIONTIME = 3;
	Boolean simulation = true;
	public static TrafficModel model = new TrafficModel();
	public Car debuggedCar;
	public Object[] personalityValues = new Object[2];
 

	Random guassian = new Random();


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
		
		cars[0] = new Car(TrafficConstants.getInstance().STARTX, TrafficConstants.getInstance().BOTLANESTARTY, 0, PersonalityGenerator());
		cars[1] = new Car(TrafficConstants.getInstance().STARTX, TrafficConstants.getInstance().TOPLANESTARTY, 1, PersonalityGenerator());
		cars[2] = new Car(TrafficConstants.getInstance().STARTX + 100, TrafficConstants.getInstance().BOTLANESTARTY, 2, PersonalityGenerator());
		cars[3] = new Car(TrafficConstants.getInstance().STARTX + 100, TrafficConstants.getInstance().TOPLANESTARTY, 3, PersonalityGenerator());
		
		
	}
	
	
	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> runSimulation()
	{	
		carBB.clear();
		for (Car car : cars)
		{
		
			carBB.add(new BoundingBox(car.xCoord,car.yCoord,car.width,car.height));
		}
		
		for(Car car: cars){
			
			car.makeDecision(carBB); 
			
		}

		return carBB;
	}
	
	public Object[] PersonalityGenerator(){ // We're using THIS method to get the values http://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml
		
		/* personalityValues.add(AGGRESSION,((float)guassian.nextGaussian() + TrafficConstants.getInstance().getAGGRESSION()));
		float comfortableSpeed = (personalityValues.indexOf(AGGRESSION) / 10);
		personalityValues.add(COMFORTABLESPEED, comfortableSpeed); */
		
		int aggression = (int) (guassian.nextGaussian() + TrafficConstants.getInstance().getAGGRESSION());
		personalityValues[AGGRESSION] = aggression;
		float comfortableSpeed = (aggression / 10);
		personalityValues[COMFORTABLESPEED] = comfortableSpeed;
		
		return personalityValues;
	}
	
	public void start()
	{
		createCars();
		carBB = runSimulation();	
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


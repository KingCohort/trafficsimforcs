

package moreRefactor;

import java.util.ArrayList;
import javafx.geometry.BoundingBox;

public class TrafficModel
{
	
	Car[] cars = new Car[2];
	//Car[] cars = new Car[TrafficConstants.getInstance().getCARNUM()];
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	Boolean simulation = true;
	public static TrafficModel model = new TrafficModel();


	public TrafficModel()
	{
		model = this;
	}

	public void createCars()
	{
		System.out.println();
		System.out.println("----- CAR NUMBER: " + TrafficConstants.getInstance().getCARNUM());
		System.out.println("----- LANE NUMBER: " + TrafficConstants.getInstance().getLANENUM());
		System.out.println("----- AGGRESSION NUMBER: " + TrafficConstants.getInstance().getAGGRESSION());
		System.out.println();
		
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
		
		cars[0] = new Car(TrafficConstants.getInstance().STARTX, TrafficConstants.getInstance().BOTLANESTARTY, 0, 1);
		cars[1] = new Car(TrafficConstants.getInstance().STARTX, TrafficConstants.getInstance().TOPLANESTARTY, 1, 1);
	
		
		
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
	
//	boolean[] checkLaneCarStart(){
		
//		for(BoundingBox bb : ArrayList<BoundingBox> carBB){
			
			
//		}
		
		
//	}
	
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
				car.debug();
			}
		}
	}

}


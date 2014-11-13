

package moreRefactor;

import java.util.ArrayList;
import javafx.geometry.BoundingBox;

public class TrafficModel
{
	
	//Car[] cars = new Car[2];
	Car[] cars = new Car[TrafficConstants.getInstance().getCARNUM()];
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

		for(int i = 0; i < TrafficConstants.getInstance().getCARNUM(); i++)
		{
			if(i < (TrafficConstants.getInstance().getCARNUM() / 2))
			{
				System.out.println("----- CAR NUMBER: " + TrafficConstants.getInstance().getCARNUM() + " i: " + i);
				cars[i] = new Car(TrafficConstants.getInstance().BOTLANESTARTX, TrafficConstants.getInstance().STARTY);
			}
			else
			{
				System.out.println("----- CAR NUMBER: " + TrafficConstants.getInstance().getCARNUM() + " i: " + i);
				cars[i] = new Car(TrafficConstants.getInstance().TOPLANESTARTX, TrafficConstants.getInstance().STARTY);
			}
		}  
		
//		cars[0] = new Car(500,TrafficConstants.getInstance().STARTY);
//		cars[1] = new Car(0, TrafficConstants.getInstance().STARTY);
//		cars[1].setSpeed(2);
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


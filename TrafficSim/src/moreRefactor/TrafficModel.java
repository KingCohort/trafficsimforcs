

package moreRefactor;

import java.util.ArrayList;
import javafx.geometry.BoundingBox;

public class TrafficModel
{
	
	Car[] cars = new Car[2];
	
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
		System.out.println("-----CAR NUMBER: " + TrafficConstants.getInstance().getCARNUM());
		System.out.println("-----LANE NUMBER: " + TrafficConstants.getInstance().getLANENUM());
		System.out.println();

	/*	for(int i = 0; i < TrafficConstants.getInstance().CARNUM; i++){
			
			if(i < (TrafficConstants.getInstance().CARNUM/2)){
				
				cars[i] = new Car(TrafficConstants.getInstance().BOTLANESTARTX, TrafficConstants.getInstance().STARTY);
				
			}else {
				
				cars[i] = new Car(TrafficConstants.getInstance().TOPLANESTARTX, TrafficConstants.getInstance().STARTY);
			}
			
		} */ 
		
		cars[0] = new Car(500,TrafficConstants.getInstance().STARTY);
		cars[1] = new Car(0, TrafficConstants.getInstance().STARTY);
		cars[1].setSpeed(2);
	}
	
	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> runSimulation()
	{	
		carBB.clear();
		for (Car car : cars)
		{
			// car.makeDecision(carBB);  //THIS IS COMMENTED OUT SO I CAN ONLY HAVE ONE CAR MOVE FOR TESTING PURPOSES - Noel 
			cars[0].move(carBB);
			cars[1].makeDecision(carBB);
			carBB.add(new BoundingBox(car.xCoord,car.yCoord,car.width,car.height));
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


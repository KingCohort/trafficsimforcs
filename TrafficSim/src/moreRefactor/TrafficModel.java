

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
		
//		TrafficConstants constants = new TrafficConstants();
//		constants.getCarNumConstants();

		cars[0] = new Car(0,150);
		cars[1] = new Car(200, 150);
	}


	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> getBB()
	{
		carBB = new ArrayList<BoundingBox>();
		
		for (Car car : cars)
		{
			carBB.add(new BoundingBox(car.xCoord,car.yCoord,car.width,car.height));
		}

		return carBB;
	}

	public void start()
	{
		createCars();
		carBB = getBB();	
	}

}


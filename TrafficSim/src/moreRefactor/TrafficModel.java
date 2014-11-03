

package moreRefactor;

import java.util.ArrayList;
import javafx.geometry.BoundingBox;

public class TrafficModel
{

	Car[] cars = new Car[2];
	
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	Boolean simulation = true;
	public static TrafficModel model;

	public TrafficModel()
	{
		model = this;
	}

	public void createCars()
	{
		System.out.println("creating cars");
		
		TrafficConstants constants = new TrafficConstants();
		constants.getCarNumConstants();

		cars[0] = new Car(0,150, 60, 30);
		cars[1] = new Car(200, 150, 60, 30);
	}


	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> getBB()
	{
		carBB = new ArrayList<BoundingBox>();
		
		for (Car car : cars)
		{
			car.makeDecision(carBB);
			System.out.println("Cars are deciding");
			carBB.add(new BoundingBox((float)car.xCoord,(float)car.yCoord,(float)car.width,(float)car.height));
		}

		return carBB;
	}

	public void start()
	{
		createCars();
		
		carBB = getBB();
		
		System.out.println("starting model");
		TrafficView view = TrafficView.view;
		view.draw();
	}

}




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
		
		for(int i = 0; i < TrafficConstants.CARNUM/2; i++){
			
			if(i <= TrafficConstants.CARNUM/2){
				cars[i] = new Car(TrafficConstants.BOTLANESTARTX, TrafficConstants.STARTY);
			} else{
				cars[i] = new Car(TrafficConstants.TOPLANESTARTX, TrafficConstants.STARTY);
			}
			
		}

	//	cars[0] = new Car(0,150, 60, 30);
	//	cars[1] = new Car(200, 150, 60, 30);
	}


	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> getBB()
	{
		
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
		
		System.out.println("starting model");
//		TrafficView view = TrafficView.view;
//		view.draw();
		carBB = getBB();
	}

}


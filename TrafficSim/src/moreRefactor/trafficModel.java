

package moreRefactor;

import java.util.ArrayList;
//import java.util.Observable;

import javafx.geometry.BoundingBox;


public class trafficModel {

	Car[] cars = new Car[2]; // this should be a variable 
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	Boolean simulation = true;
	public static trafficModel model;
	
	
	public static GUI theGUI;
	public static int numberOfCars;

	public trafficModel() {
		model = this;
	}

	public void createCars() {
		//numberOfCars = theGUI.getCarSpinnerValue();

		System.out.println("creating cars");
		
		System.out.println("-----THIS IS THE NUMBER OF CARS: " + numberOfCars);
		
		// create cars dynamically

		//for(int i = 0; i < cars.length; i++){
		cars[0] = new Car(0,150, 60, 30);
		cars[1] = new Car(200, 150, 60, 30);
		//}
	}

	//	public void notifyObservers() {	
	//		setChanged();
	//		update(this, carBB);
	//	}


	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> getBB()
	{
		carBB = new ArrayList<BoundingBox>();
		int[] personality = new int[5];
		for (Car car : cars) {
			car.makeDecision(carBB);
			System.out.println("Cars are deciding");
		}
		for (Car car : cars) {
			carBB.add(new BoundingBox(car.xCoord,car.yCoord,car.width,car.height));
		}

		return carBB;
	}

	public void start(){
		createCars();
		carBB = getBB();
		System.out.println("starting model");
		trafficView view = trafficView.view;
		view.draw();
	}

	//	public void run() {
	//			for (Car car : cars) {
	//				car.makeDecision(carBB);
	//				System.out.println("Cars are deciding");
	//			}
	//		}
}

package moreRefactor;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

import javafx.geometry.BoundingBox;


public class trafficModel extends Observable {
	
	Car[] cars = new Car[2];
	ArrayList<Point> carPoints;		// is this still used?? cause bounding boxes
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	Boolean simulation = true;


	
	public void createCars() {
		
		System.out.println("creating cars");

		//for(int i = 0; i < cars.length; i++){
		cars[0] = new Car(0,150, 60, 30);
		cars[1] = new Car(0, 250, 60, 30);
		//}
	}

	//	public void notifyObservers() {	
	//		setChanged();
	//		update(this, carBB);
	//	}


	// 	java has bounding boxes now yay
	private ArrayList<BoundingBox> getBB(Car[] cars){

		for (Car car : cars) {
			carBB.add(new BoundingBox((float)car.xCoord,(float)car.yCoord,(float)car.width,(float)car.height));
		}

		return carBB;
	}

	public void start(){
		createCars();
		carBB = getBB(cars);
		
		System.out.println("starting model");
		
		run();
	}

	public void run() {
		for (Car car : cars) {
			car.makeDecision(carBB);
			System.out.println("Cars are deciding");
		}
		
		
		setChanged();
		notifyObservers(carBB);
	}
}



package moreRefactor;

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
		run();
		}
	
	public void run() {
		while (simulation = true) {
			for (Car car : cars) {
				car.makeDecision(carBB);
			}
			setChanged();
			notifyObservers(carBB);
		}
	}

}

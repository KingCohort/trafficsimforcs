package observerPatternRefactor;

import java.awt.Point;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

import javafx.geometry.BoundingBox;

public class trafficModel extends java.util.Observable {

	//This is where the actual simulation takes place. All things related to what should happen in the simulation goes here
	//There is no environment. This class will create an array of car objects that is passed to the controller and into the view
	//The only class who knows about anything besides itself is the controller class. This is not that class


	Car[] cars = new Car[2];
	ArrayList<Point> carPoints;
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	Boolean simulation = true;

	public void createCars(){

		//for(int i = 0; i < cars.length; i++){
		cars[0] = new Car(0,150, 60, 30);
		cars[1] = new Car(0, 250, 60, 30);
		//}
	}

//	public void notifyObservers() {	
//		setChanged();
//		update(this, carBB);
//	}



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
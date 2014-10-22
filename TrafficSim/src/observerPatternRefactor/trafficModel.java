package observerPatternRefactor;

import java.awt.Point;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

import javafx.geometry.BoundingBox;
import javafx.geometry.BoundingBoxBuilder;

public class trafficModel extends java.util.Observable {

	//This is where the actual simulation takes place. All things related to what should happen in the simulation goes here
	//There is no environment. This class will create an array of car objects that is passed to the controller and into the view
	//The only class who knows about anything besides itself is the controller class. This is not that class


	Car[] cars = new Car[2];
	Observer o;
	ArrayList<Point> carPoints;
	ArrayList<BoundingBox> carBB;

	public Car[] createCars(){

		//for(int i = 0; i < cars.length; i++){
		cars[0] = new Car(0,150, 60, 30);
		cars[1] = new Car(0, 250, 60, 30);
		//}
		return  cars;
	}

	public void notifyObservers() {	
		setChanged();
		o.update(this, carBB);
	}



	private ArrayList<BoundingBox> getBB(Car[] cars){

		for (Car car : cars) {
			carBB.add(new BoundingBox(car.xCoord,car.yCoord,car.width,car.height));
		}

		return carBB;
	}

	public void main(Car[] cars){
		carBB = getBB(cars);
		for(int i = 0; i < cars.length; i++){
			cars[i].makeDecision(carBB);
		}
	}
}
package observerPatternRefactor;

import java.awt.Point;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

public class trafficModel extends java.util.Observable {

	//This is where the actual simulation takes place. All things related to what should happen in the simulation goes here
	//There is no environment. This class will create an array of car objects that is passed to the controller and into the view
	//The only class who knows about anything besides itself is the controller class. This is not that class

	
	Car[] cars = new Car[1];
	Observer o;
	ArrayList<Point> carLocs;

	public Car[] createCars(){
	
		//for(int i = 0; i < cars.length; i++){
			
			cars[0] = new Car(0,150);
			
		//}
		return  cars;
	}

	public void setObserver(Observer o) {
		this.o = o;
	}

	public void notifyObservers() {	
		setChanged();
		carLocs = new ArrayList<Point>();
		for (Car car : cars) {
			carLocs.add(car.getLocation());
		}
		o.update(this, carLocs);
	}

}
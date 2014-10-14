package observerPatternRefactor;

import java.util.Arrays;

public class trafficModel extends java.util.Observable {

	//This is where the actual simulation takes place. All things related to what should happen in the simulation goes here
	//There is no environment. This class will create an array of car objects that is passed to the controller and into the view
	//The only class who knows about anything besides itself is the controller class. This is not that class
	
	Car[] cars = new Car[100];
	
	public static void main(String[] args) {
		
		
		
		//notifyObservers(cars);

	}
	
	
public Car[] createCars(){
	
	return  cars;
}
	
	
	

}

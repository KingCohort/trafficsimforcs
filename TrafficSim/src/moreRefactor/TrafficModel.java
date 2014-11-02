

package moreRefactor;

import java.util.ArrayList;
//import java.util.Observable;


import javafx.geometry.BoundingBox;


public class TrafficModel {

	Car[] cars = new Car[2];
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	Boolean simulation = true;
	TrafficConstants constants = new TrafficConstants();


	public void createCars (int carNum) {

		System.out.println("creating cars");
//		for(int i = 0; i < carNum; i++){
				
			
			
//		}
		
		cars[0] = new Car();
		cars[1] = new Car();

	}

	//	public void notifyObservers() {	
	//		setChanged();
	//		update(this, carBB);
	//	}


	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> getBB(){
		carBB = new ArrayList<BoundingBox>();
		for (Car car : cars) {
			car.makeDecision(carBB);
			System.out.println("Cars are deciding");
		}
		for (Car car : cars) {
			carBB.add(new BoundingBox(car.xCoord,car.yCoord,car.carWidth,car.carHeight));
		}

		return carBB;
	}

	public void start(){
		TrafficView view = new TrafficView(this);
		createCars(constants.CARNUM);
		carBB = getBB();
		System.out.println("starting model");
		view.draw();
	}

//	public void run() {
//			for (Car car : cars) {
//				car.makeDecision(carBB);
//				System.out.println("Cars are deciding");
//			}
//		}
	}


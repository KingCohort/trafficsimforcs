

package moreRefactor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.BoundingBox;

public class TrafficModel
{


	Car[] cars = new Car[TrafficConstants.getInstance().getCARNUM()];
	ArrayList<BoundingBox> carBB = new ArrayList<BoundingBox>();
	int AGGRESSION = 0, COMFORTABLESPEED = 1, DECISIONMAKING = 3, ATTENTION = 2, BUBBLESIZE = 4, STARTINGLANE = 5;
	Boolean simulation = true;
	public static TrafficModel model = new TrafficModel();
	public Car debuggedCar;
	public Object[] personalityValues = new Object[6];
	public BoundingBox[] startingLocs = new BoundingBox[TrafficConstants.getInstance().getLANENUM()];
	public boolean[] isOpen = new boolean[TrafficConstants.getInstance().getLANENUM()];
	public float speedAdjust = 0;
	public boolean isSafe = true;


	Random r = new Random();


	public TrafficModel()
	{
		model = this;
	}

	public void createCars()
	{

		//WHEN TESTING MAKE SURE THE CARS ARE NOT ON TOP OF EACHOTHER, WE HAVENT FIXED THAT YET

		/*	for(int i = 0; i < TrafficConstants.getInstance().getCARNUM(); i++)
		{
			if(i < (TrafficConstants.getInstance().getCARNUM() / 2))
			{
				System.out.println("----- CAR NUMBER: " + TrafficConstants.getInstance().getCARNUM() + " i: " + i);
				cars[i] = new Car(TrafficConstants.getInstance().STARTX, TrafficConstants.getInstance().TOPLANESTARTY, i);
			}
			else
			{
				System.out.println("----- CAR NUMBER: " + TrafficConstants.getInstance().getCARNUM() + " i: " + i);
				cars[i] = new Car(TrafficConstants.getInstance().STARTX, TrafficConstants.getInstance().BOTLANESTARTY, i);
			}
		}  

		 */
	

		Object[] firstCarPersonality = PersonalityGenerator();
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			speedAdjust = (float) firstCarPersonality[COMFORTABLESPEED];
		}
		cars[0] = new Car(0, firstCarPersonality, speedAdjust, carBB);
		carBB.add(new BoundingBox(cars[0].xCoord,cars[0].yCoord,cars[0].width,cars[0].height));
		for(int i = 1; i < TrafficConstants.getInstance().getCARNUM(); i++){
			cars[i] = new Car(i, PersonalityGenerator(), speedAdjust, carBB);
			try {
				writeToFile(cars[i].printCarStats());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			carBB.add(new BoundingBox(cars[i].xCoord,cars[i].yCoord,cars[i].width,cars[i].height));
		}


		//		Object[] firstCarPersonality = PersonalityGenerator();
		//		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
		//			speedAdjust = (float) firstCarPersonality[COMFORTABLESPEED];
		//		}
		//		
		//		cars[1] = new Car(1, PersonalityGenerator(), speedAdjust);
		//		cars[2] = new Car(2, PersonalityGenerator(), speedAdjust);
		//		cars[3] = new Car(3, PersonalityGenerator(), speedAdjust);
		//		cars[3].wantToChangeLanes = true;
		//		//The fifth car below demonstrates the new random-location car constructor.
		//		//cars[4] = new Car(4, PersonalityGenerator(), speedAdjust);

		TrafficConstants.getInstance().isModelReady = true;
	}


	
	public void writeToFile(String TextLine) throws IOException
	{
		FileWriter write = new FileWriter(TrafficConstants.getInstance().getFILENAME() + ".txt", true);
		PrintWriter print = new PrintWriter(write);

		print.printf("%s"+"%n",TextLine);
		print.close();
	}
	


	// 	java has bounding boxes now yay
	public ArrayList<BoundingBox> runSimulation(){	
		carBB.clear();
		for (Car car : cars)
		{
			carBB.add(new BoundingBox(car.xCoord,car.yCoord,car.width,car.height));
		}
		//These lines involving firstCarPosition and firstCarDifference are intended to
		//make the other cars move in relation to the first car moving horizontally if the view is fixed on it.
		//If the first car moves forward, the other cars will move backwards an equal amount, and vice versa.
		float firstCarPosition = 0;
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			firstCarPosition = cars[0].getxCoord();
		}
		for(Car car: cars) {
			//If not in the queue, act normally.
			if (!car.inQueue) {
				car.makeDecision(carBB, cars); 
				try {
					writeToFile(car.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//If the car's in the queue, check to see if any unqueued cars are in its way. If so, don't move.
			//If only other queued cars are there, then it's free to start moving.
			else if (!car.isIntersectingOtherCar(cars)) {
				car.inQueue = false;
				car.leavingQueue = true;
			}
		}
		if (TrafficConstants.getInstance().GLOBALSIMVIEW==false) {
			float firstCarDifference = firstCarPosition - cars[0].getxCoord();
			for(Car car: cars) {
				car.setxCoord(car.getxCoord() + firstCarDifference);
			}
			if (cars[0].isIntersectingOtherCar(cars) == true) {
				TrafficConstants.getInstance().setMEDIANSPEED(0);
				TrafficConstants.getInstance().setGLOBALSIMVIEW(true);
				for(Car car: cars) {
					car.comfortableSpeed += speedAdjust;
					car.currentSpeed += speedAdjust;
				}
			}
		}
		return carBB;
	}

	private int chooseStartLane() {
		int startLane = r.nextInt(TrafficConstants.getInstance().getLANENUM());
		return startLane;

	}



	public Object[] PersonalityGenerator(){ // We're using THIS method to get the values http://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml


		double aggression = 101;
		do {
			aggression = Math.round(r.nextGaussian() * (100 - TrafficConstants.getInstance().getAGGRESSION()) + TrafficConstants.getInstance().getAGGRESSION());
		} while(aggression < 0 || aggression > 100);

		personalityValues[AGGRESSION] = aggression;
		float comfortableSpeed = (float)(aggression / 10);
		
		if(comfortableSpeed <= 0){	
			comfortableSpeed = 1;
		} 
		
		personalityValues[COMFORTABLESPEED] = comfortableSpeed;

		int attention = r.nextInt(101-90) + 90;

		personalityValues[ATTENTION] = attention;

		double bubbleSize = TrafficConstants.getInstance().defaultBubbleSize;
		if(aggression < 50){

			bubbleSize = bubbleSize + (comfortableSpeed);

		} else{

			bubbleSize = bubbleSize - comfortableSpeed;

		}
		
		if(bubbleSize < 10){
			
			bubbleSize = 10;
		}

		personalityValues[BUBBLESIZE] = bubbleSize;

		personalityValues[STARTINGLANE] = chooseStartLane();

		return personalityValues;
	}

	public void start()
	{
		
		createCars();
		do{

		}while(TrafficConstants.getInstance().isModelReady == false);
		carBB = runSimulation();	

	}


	public void debugCarAt(int x, int y)
	{
		for (Car car : cars)
		{
			if (car.contains(x, y))
			{
				debuggedCar = car;
				car.debug();
			}
		}
	}


	/* public void clearFile()throws IOException{
		FileWriter write = new FileWriter("log.txt", false);
		PrintWriter print = new PrintWriter(write);
		print.printf("","");
		print.close();

	} */

}
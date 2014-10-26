package moreRefactor;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import javafx.geometry.BoundingBox;

public class trafficView extends PApplet implements Observer {

	/*--INFORMATION ABOUT VIEW--
	//This is where the highway and cars are drawn. All things related to what the simulation should LOOK LIKE goes here
	//There are no manipulation of object behavior. This class will generate the simulation graphics
	//The only class who knows about anything besides itself is the controller class. This is not that class
	 * 
	 * SIMULATION BEING DONE HERE
	 * take array of cars
	 * telling things where to draw stuff
	 * 
	 * --- INFORMATION ABOUT VARS ---
	 * 
	 * __Size : is the size of the box in the Y direction
	 * 		remember the Y axis increases as you go down vertically on the screen
	 * 
	 * __Hor : is the length of the box in the X direction
	 * 		remember the X axis increases horizontally across the right of the screen -->
	 * 		displayWidth
	 * 
	 * REMEMBER THE COORDINATE OF A BOX IS IN THE UPPER LEFT CORNER OF THE BOX
	 * 		__Xcoor : is the X coordinate
	 * 		__Ycoor : is the Y coordinate
	 * 
	 */

	private static final long serialVersionUID = 1L;

	int carWidth = 60; // what if we have a parameter class where all of the random dimensional info is so it
	int carHeight = 30;
	ArrayList<BoundingBox> carLocs = new ArrayList<BoundingBox>();
	public static trafficView view;
	
	
	public trafficView() {
	}

	public void trafficViewObservers(Observable o){
		
		o.addObserver(this);
		System.out.println("observer added");
	}

	// processing setup method
	public void setup()
	{
		size(displayWidth, displayHeight);
		//background(0, 255, 0);
		view = this;
	}

	// processing draw method
	public void draw()
	{
		System.out.println("Now drawing");
		background(0, 255, 0);
		createHighway();
		carLocs = trafficModel.model.getBB();
		displayCars(carLocs);
		//makeDecision(carLocs);
	}


	void displayCars(ArrayList<BoundingBox> carLocs)
	{
		System.out.println("Displaying cars");
		fill(255, 0, 0);
		stroke(0);
		for (BoundingBox bb : carLocs) {
			rect((float)bb.getMinX(), (float)bb.getMinY(), (float)bb.getWidth(), (float)bb.getHeight());
		}
	}

	void createHighway()
	{
		System.out.println("Displaying highway");

		int buffer = 10;

		// highway coords
		int highwayXcoor = 0;
		int highwayYcoor = 100;

		// lane bound coordinates
		int topBoundXcoor = 0;
		int topBoundYcoor = highwayYcoor + buffer;
		int bottomBoundXcoor = 0;

		// extra white line outer bounds
		int topBoundSize = 10;
		int bottomBoundSize = topBoundSize; // the boundary lines should be the same size
		int boundHor = displayWidth;

		// median
		int medianSize = 10;
		int medianHor = 40;
		int medianXcoor;
		int offset = 0;
		int medianSpeed = 0;

		// lanes
		int laneSize = 100;
		int laneHor = displayWidth;
		int numLanes = 2;

		// base highway
		int highwaySize = (buffer * 2) + ( topBoundSize + bottomBoundSize ) + ( medianSize ) + ( ( laneSize ) * (numLanes) ); 
		// the horizontal size of the road
		int highwayHor = displayWidth; // width of the road

		// bottom lane coord declared here for other vars info
		int bottomBoundYcoor = ( buffer ) + ( topBoundYcoor ) + ( ( laneSize ) * (numLanes) ) + ( medianSize );
		int medianYcoor = ( highwayYcoor ) + ( buffer ) + ( topBoundSize ) + ( laneSize );

		// rect(X, Y, WIDTH, HEIGHT), how to use rect

		// base road
		fill(51);
		stroke(0);
		rect(highwayXcoor, highwayYcoor, highwayHor, highwaySize);

		// top outer road line
		fill(255);
		stroke(0);
		rect(topBoundXcoor, topBoundYcoor, boundHor, topBoundSize);
		//bottom outer road line
		fill(255);
		stroke(0);
		rect(bottomBoundXcoor, bottomBoundYcoor, boundHor, topBoundSize);

		// median
		fill(255);
		stroke(0); 
		offset = offset - medianSpeed;

		for (int i = 0; i < displayWidth; i+=100)
		{
			rect(i + offset, medianYcoor, medianHor, medianSize);
		}		
	}

	@Override
	public void update(Observable o, Object arg) {
		ArrayList<BoundingBox> carBBs = new ArrayList<BoundingBox>();
		System.out.println("i'm updating");
		if(arg instanceof ArrayList){
			
			System.out.println("Lucy! Its an ArrayList!");
			carBBs = (ArrayList<BoundingBox>)arg;

		} else{
			
			System.out.println("Not an array list, its" + arg.getClass().getSimpleName() + " and o is " + o.getClass().getSimpleName());
		}
		
		System.out.println("Its drawing probably");
		draw();
		
	}


}







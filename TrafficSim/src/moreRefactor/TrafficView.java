

package moreRefactor;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import javafx.geometry.BoundingBox;
import java.util.Random;

public class TrafficView extends PApplet
{

	/*--INFORMATION ABOUT VIEW--
	//This is where the highway and cars are drawn. All things related to what the simulation should LOOK LIKE goes here
	//There are no manipulation of object behavior. This class will generate the simulation graphics
	//The only class who knows about anything besides itself is the controller class. This is not that class
	 * 
	 * SIMULATION BEING DONE HERE
	 * take array of cars
	 * telling things where to draw stuff
	 */

	private static final long serialVersionUID = 1L;
	ArrayList<BoundingBox> carLocs = new ArrayList<BoundingBox>();
	public static TrafficView view;
	PImage loadGif;
	PImage blueCarImage = loadImage("blueCar.png");
	PImage redCarImage = loadImage("redCar.png");
	PImage greenCarImage = loadImage("greenCar.png");
	PImage carColor;
	ArrayList<PImage> images = new ArrayList<PImage>();
	public Car imageCar;
	int offset = 0;


	public TrafficView()
	{
		TrafficModel.model.start();
		//If the view is fixed over one car, start the median moving.
		if (TrafficConstants.getInstance().getGLOBALSIMVIEW()==false){
			TrafficConstants.getInstance().setMEDIANSPEED(10);
		}
	}


	// processing setup method
	public void setup()
	{
		size(1920, 980);
		frameRate(25);
		view = this;
	}

	// processing draw method
	public void draw()
	{
		loadGif = loadImage("ajax-loader.gif");
		
		if(TrafficConstants.getInstance().isModelReady == false)
		{
			background(0, 0, 0);
			image(loadGif, 123, 123);
		}
		else
		{
			background(0, 255, 0);
			createHighway();
			
			carLocs = TrafficModel.model.runSimulation();
			displayCars(carLocs);
		}
	}


	void displayCars(ArrayList<BoundingBox> carLocs)
	{		
		for (BoundingBox bb : carLocs)
		{
			noFill();
			noStroke();
			rect((float)bb.getMinX(), (float)bb.getMinY(), (float)bb.getWidth(), (float)bb.getHeight());
			image(carColor(), (float)bb.getMinX(), (float)bb.getMinY());
		}
	}
	
	// should generate a random car image and passes it into the image method in displayCars()
	public PImage carColor()
	{
		int index;
		Random randomGenerator;
		
		images.add(blueCarImage);
//		images.add(redCarImage);
//		images.add(greenCarImage);
		
		randomGenerator = new Random();
		index = randomGenerator.nextInt(images.size());
        carColor = images.get(index);
        
		return carColor;
	}

	void createHighway()
	{
		// the highway is drawn in layers 
		// the bottom is drawn first then other layers added on top of that
		// the background (technically the first layer) is drawn in the draw method

		// marker vars
		int gutter = 20; // far edges of the highway, where you would pull over
		int markerHeight = 10; // height (y-axis) of white lines, lane markers, and median
		int gutterMarkerWidth = displayWidth; // width (x-axis) of the gutter markers
		int medianMarkerWidth = 30; // width (x-axis) of the median stripes
		//int theHighwayOffsetVariable = 50;		// the amount of pixels the highway is drawn down the y-axis from the (0, 0) point

		// lane vars
		int laneNumber = TrafficConstants.getInstance().getLANENUM(); // get lane number from gui
		int laneHeight = 100; // height (y-axis) of the lane
		//int laneWidth = displayWidth; // width (x-axis) of the lane

		// first layer of the highway
		int highwayBaseHeight = ( (gutter * 2) + (markerHeight * 2) + (laneNumber * laneHeight) + (markerHeight * (laneNumber - 1)) ); // height (y-axis) of the first layer of the highway (road part, grey part)
		// ( (two gutters) + (two edge markers) + (number of lanes) + (medians) )
		int highwayBaseWidth = displayWidth; // width (x-axis) of the first layer of the highway (road part, grey part)
		int highwayBaseXcoord = 0; // where the highway should be placed
		int highwayBaseYcoord = 100; // where the highway should be placed
		//System.out.println("HEIGHT OF THE HIGHWAY: " + highwayBaseHeight);

		// marker placement
		int upperGutterMarkerXcoord = 0;
		int upperGutterMarkerYcoord = highwayBaseYcoord + gutter;
		int lowerGutterMarkerXcoord = 0;
		int lowerGutterMarkerYcoord = ( (highwayBaseYcoord + highwayBaseHeight) - gutter - markerHeight);
		// median
		int medianHeight = markerHeight;
		int medianWidth = medianMarkerWidth;
		// no x coord
		// y coord(s) below

		// ----- actually draw the highway -----

		// base
		fill(51);
		noStroke();
		rect(highwayBaseXcoord, highwayBaseYcoord, highwayBaseWidth, highwayBaseHeight);

		// edge markers
		fill(255);
		noStroke();
		// upper
		rect(upperGutterMarkerXcoord, upperGutterMarkerYcoord, gutterMarkerWidth, markerHeight);
		// lower
		rect(lowerGutterMarkerXcoord, lowerGutterMarkerYcoord, gutterMarkerWidth, markerHeight);

		// medians (lane dividers)
		fill(255);
		noStroke();
		//Offset for moving median when fixed view is on. Resets to 0 if below -100 to make sure we don't run out of rectangles.
		offset = offset - TrafficConstants.getInstance().medianSpeed;
		if (offset < -100) {
			offset = 0;
		}
		int medianYcoord_1;
		int medianYcoord_2;
		int medianYcoord_3;
		int medianYcoord_4;

		if (laneNumber == 5) // five lanes
		{
			medianYcoord_1 = ( (highwayBaseYcoord) + (gutter) + (laneHeight) + markerHeight);
			medianYcoord_2 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 2) + markerHeight*2);
			medianYcoord_3 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 3) + markerHeight*3);
			medianYcoord_4 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 4) + markerHeight*4 );
			
			// draw medians
			for (int i = 0; i < displayWidth+100; i += 100)
			{
				rect(i + offset, medianYcoord_1, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_2, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_3, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_4, medianWidth, medianHeight);
			}
		}
		else if (laneNumber == 4) // four lanes
		{
			medianYcoord_1 = ( (highwayBaseYcoord) + (gutter) + (laneHeight) + markerHeight );
			medianYcoord_2 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 2) + markerHeight*2 );
			medianYcoord_3 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 3) + markerHeight*3 );

			// draw medians
			for (int i = 0; i < displayWidth+100; i += 100)
			{
				rect(i + offset, medianYcoord_1, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_2, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_3, medianWidth, medianHeight);
			}
		}
		else if (laneNumber == 3) // three lanes
		{
			medianYcoord_1 = ( (highwayBaseYcoord) + (gutter) + (laneHeight) + markerHeight );
			medianYcoord_2 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 2) + markerHeight*2 );

			// draw medians
			for (int i = 0; i < displayWidth+100; i += 100)
			{
				rect(i + offset, medianYcoord_1, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_2, medianWidth, medianHeight);
			}
		}
		else // two lanes
		{
			medianYcoord_1 = ( (highwayBaseYcoord) + (gutter) + (laneHeight) + markerHeight );

			// draw medians
			for (int i = 0; i < displayWidth+100; i += 100)
			{
				rect(i + offset, medianYcoord_1, medianWidth, medianHeight);
			}
		}
	} // end createHighway()

	@Override
	public void mousePressed()
	{
		TrafficModel.model.debugCarAt(mouseX, mouseY);
	}
}







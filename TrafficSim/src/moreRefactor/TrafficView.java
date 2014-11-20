

package moreRefactor;

import java.util.ArrayList;

import processing.core.PApplet;
import javafx.geometry.BoundingBox;

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

	ArrayList<BoundingBox> carLocs = new ArrayList<BoundingBox>();
	public static TrafficView view;
	
	
	public TrafficView()
	{
		TrafficModel.model.start();
	}


	// processing setup method
	public void setup()
	{
		size(1920, 1080);
		frameRate(25);
		view = this;
	}

	// processing draw method
	public void draw()
	{
		//System.out.println("Now drawing");
		background(0, 255, 0);
		createHighway();
		// this line is being weird with processing and VMs
		carLocs = TrafficModel.model.runSimulation();
		displayCars(carLocs);
	}


	void displayCars(ArrayList<BoundingBox> carLocs)
	{
		//System.out.println("Displaying cars");
		fill(255, 0, 0);
		stroke(0);
		for (BoundingBox bb : carLocs)
		{
			rect((float)bb.getMinX(), (float)bb.getMinY(), (float)bb.getWidth(), (float)bb.getHeight()); //
		}
	}

	void createHighway()
	{
		System.out.println("----- LANE NUMBER IN VIEW: " + TrafficConstants.getInstance().getLANENUM());
		
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
		int lowerGutterMarkerYcoord = ( (highwayBaseYcoord + highwayBaseHeight) - gutter - ( markerHeight * (laneNumber - 1) ) );
		
		// median
		int offset = 0;
		int medianSpeed = 0;
		int medianHeight = markerHeight;
		int medianWidth = medianMarkerWidth;
		// no x coord
		// y coord(s) below
		
		// ----- actually draw the highway -----
		
		// base
		fill(51);
		stroke(0);
		rect(highwayBaseXcoord, highwayBaseYcoord, highwayBaseWidth, highwayBaseHeight);
		
		// edge markers
		fill(255);
		stroke(0);
		// upper
		rect(upperGutterMarkerXcoord, upperGutterMarkerYcoord, gutterMarkerWidth, markerHeight);
		// lower
		rect(lowerGutterMarkerXcoord, lowerGutterMarkerYcoord, gutterMarkerWidth, markerHeight);
		
		// medians (lane dividers)
		fill(255);
		stroke(0); 
		offset = offset - medianSpeed;
		int medianYcoord_1;
		int medianYcoord_2;
		int medianYcoord_3;
		int medianYcoord_4;
		
		if (laneNumber == 5) // five lanes
		{
			medianYcoord_1 = ( (highwayBaseYcoord) + (gutter) + (laneHeight) );
			medianYcoord_2 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 2) );
			medianYcoord_3 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 3) );
			medianYcoord_4 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 4) );
			
			for (int i = 0; i < displayWidth; i += 100)
			{
				rect(i + offset, medianYcoord_1, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_2, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_3, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_4, medianWidth, medianHeight);
			}
		}
		else if (laneNumber == 4) // four lanes
		{
			medianYcoord_1 = ( (highwayBaseYcoord) + (gutter) + (laneHeight) );
			medianYcoord_2 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 2) );
			medianYcoord_3 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 3) );
			
			for (int i = 0; i < displayWidth; i += 100)
			{
				rect(i + offset, medianYcoord_1, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_2, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_3, medianWidth, medianHeight);
			}
		}
		else if (laneNumber == 3) // three lanes
		{
			medianYcoord_1 = ( (highwayBaseYcoord) + (gutter) + (laneHeight) );
			medianYcoord_2 = ( (highwayBaseYcoord) + (gutter) + (laneHeight * 2) );
			
			for (int i = 0; i < displayWidth; i += 100)
			{
				rect(i + offset, medianYcoord_1, medianWidth, medianHeight);
				rect(i + offset, medianYcoord_2, medianWidth, medianHeight);
			}
		}
		else // two lanes
		{
			medianYcoord_1 = ( (highwayBaseYcoord) + (gutter) + (laneHeight) );
			
			for (int i = 0; i < displayWidth; i += 100)
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







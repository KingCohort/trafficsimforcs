

package realTrafficSim;

import processing.core.*;

public class Environment {
	
	/*
	 * --- INFORMATION ABOUT VARS ---
	 * 
	 * __Size : is the size of the box in the Y direction
	 * 		remember the Y axis increases as you go down vertically on the screen
	 * 
	 * __Hor : is the length of the box in the X direction
	 * 		remember the X axis increases horizontally across the right of the screen -->
	 * 
	 * REMEMBER THE COORDINATE OF A BOX IS IN THE UPPER LEFT CORNER OF THE BOX
	 * 		__Xcoor : is the X coordinate
	 * 		__Ycoor : is the Y coordinate
	 * 
	 */
	PApplet par;
	
	/* public Environment(PApplet parent)
	{
		par = parent;
	} */ 
	



	void createHighway(PApplet par)
	{
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
		int boundHor = par.displayWidth;
		
		// median
		int medianSize = 10;
		int medianHor = 40;
		int medianXcoor;
		int medianYcoor = 0;
		int offset = 0;
		int medianSpeed = 3;
		
		// lanes
		int laneSize = 100;
		int laneHor = par.displayWidth;
		int numLanes = 2;
		
		// base highway
		int highwaySize = par.displayWidth;
		int highwayHor = (buffer * 2) + ( topBoundSize + bottomBoundSize ) + ( medianSize ) + ( ( laneSize ) * (numLanes) );
		
		// bottom lane coord declared here for other vars info
		int bottomBoundYcoor = ( buffer ) + ( topBoundYcoor ) + ( ( laneSize ) * (numLanes) ) + ( medianSize );
		// rect(X, Y, WIDTH, HEIGHT), how to use rect
		
		// base road
		par.fill(51);
		par.stroke(0);
		par.rect(highwayXcoor, highwayYcoor, highwayHor, highwaySize);

		// top outer road line
		par.fill(255);
		par.stroke(0);
		par.rect(topBoundXcoor, topBoundYcoor, boundHor, topBoundSize);
		//bottom outer road line
		par.fill(255);
		par.stroke(0);
		par.rect(bottomBoundXcoor, bottomBoundYcoor, boundHor, topBoundSize);

		// median
		par.fill(255);
		par.stroke(0); 
		offset = offset - medianSpeed;

		for(int i = 0; i < par.displayWidth; i++)
		{
			par.rect(i * 100 + offset, medianYcoor, medianHor, medianSize);
		}		
	}
}













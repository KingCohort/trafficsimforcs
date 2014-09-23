
package realTrafficSim;

/*
 * test class for processing
 */

import processing.core.*;

public class TestProcessing extends PApplet
{
	public void setup()
	{
		size(1500,900);
		background(0);
	}
	
	public void draw()
	{
		stroke(255);
		if (mousePressed)
		{
			line(mouseX,mouseY,pmouseX,pmouseY);
		}
	}
	
	// grahams comment
	// Noel's Comment!!
	// run all who enter here for i have no idea what im doing. love kyle
	// it works! - stephen
}

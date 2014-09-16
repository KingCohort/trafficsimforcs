
package realTrafficSim;

import processing.core.*;

public class TestProcessing extends PApplet {

	public void setup() {
		size(200,200);
		background(0);
	}

	// maybe this will show up in the svn

	public void draw() {
		stroke(255);
		if (mousePressed) {
			line(mouseX,mouseY,pmouseX,pmouseY);
		}
	}
}


package realTrafficSim;

import processing.core.*;

public class Simulation extends PApplet {
	
	int c = color(0);
	float x, x2 = 0;
	float y = 100;
	float carWidth = 60;
	float carHeight = 30;
	float speed = 1;
	float speed2 = 2;

	public void setup() {
		size(displayWidth, displayHeight);
		background(0);
	}

	public void draw() {
		background(255);
		move();
		display();
	}

	void move() {
		x = x + speed;
		x2 = x2 + speed2;
		if (x > width) {
			x = 0;
		}
		if (x2 > width) {
			x2 = 0;
		}
	}

	void display() {
		fill(c);
		rect(x, y, carWidth, carHeight);
		
		fill(c);
		rect(x2, y+100, carWidth, carHeight);
	}
}






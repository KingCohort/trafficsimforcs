
package realTrafficSim;

import processing.core.*;

public class Simulation extends PApplet {
	
	int c = color(0);
	float x, x2, x3 = 0;
	float y = 100;
	float carWidth = 60;
	float carHeight = 30;
	float speed = 2;
	float speed2 = 3;
	float speed3 = 1;

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
		x3 = x3 + speed3;
		
		if (x > width) {
			x = 0;
		}
		if (x2 > width) {
			x2 = 0;
		}
		if (x3 > width) {
			x3 = 0;
		}
	}

	void display() {
		fill(c);
		rect(x, y, carWidth, carHeight);
		
		fill(c);
		rect(x2, y+100, carWidth, carHeight);
		
		fill(c+200);
		rect(x3, y+200, carWidth, carHeight);
	}
}






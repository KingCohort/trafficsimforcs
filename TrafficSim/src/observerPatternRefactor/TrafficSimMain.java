package observerPatternRefactor;

import java.util.Observable;

import processing.core.PApplet;

// the main class where the program actually starts
// the gui is called and from there it follows the model view controller model

public class TrafficSimMain extends PApplet
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		GUIView gui = new GUIView();
		gui.GUIStart();
	}
}

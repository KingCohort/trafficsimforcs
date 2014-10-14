package observerPatternRefactor;

import java.util.Observable;
import java.util.Observer;

public class GUIView implements Observer {
	
	//This is where the GUI stuff is created and used. All things related to what the GUI should do/look like goes here
	//There is no environment, there are no cars. This class will generate the GUI
	//The only class who knows about anything besides itself is the controller class. This is not that class

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

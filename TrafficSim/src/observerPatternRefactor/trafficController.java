package observerPatternRefactor;

import java.util.Observable;
import java.util.Observer;

public class trafficController implements Observer {
	
	//This is where the communication between the model and the views go on. All things related to data transfer
	//between the Model and the two Views. There is no manipulation of object behavior, there is no draw method
	//There are no manipulation of object behavior. This class will generate the simulation graphics
	//The only class who knows about anything besides itself is the controller class. This is not that class
	
	private trafficModel model;
	private trafficView view;

	
	public trafficController(trafficModel model, trafficView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void update(Observable model, Object carLocs) {
		view.update(model, carLocs);
	}
}

package observerPatternRefactor;

import java.util.Observable;

public class TrafficSimMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		trafficModel model = new trafficModel(); 
		trafficView view = new trafficView();
		trafficController controller = new trafficController();
//		model.addObserver(view);
		model.addObserver(controller);
		controller.addObserver(view);
	}

}

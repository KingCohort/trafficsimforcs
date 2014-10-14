package observerPatternRefactor;

public class TrafficSimMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		trafficModel model = new trafficModel(); 
		trafficView view = new trafficView();
		trafficController controller = new trafficController(model, view);
		model.setObserver(controller);
//		controller.start();
	}

}

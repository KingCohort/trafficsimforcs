package observerPatternRefactor;

public class TrafficSimMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		trafficModel model = new trafficModel(); 
		trafficView view = new trafficView();
		model.addObserver(view);
		
//		trafficController controller = new trafficController(model, view);
//		model.addObserver(controller);
		//http://www.austintek.com/mvc/
	}

}

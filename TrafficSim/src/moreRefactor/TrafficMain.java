package moreRefactor;

public class TrafficMain {
	
	public static void main(String[] args)
	{
		TrafficModel model = new TrafficModel();
		TrafficView view = new TrafficView();
//		view.trafficViewObservers(model);
		GUI gui = new GUI();
		gui.GUIStart();		
	}
}

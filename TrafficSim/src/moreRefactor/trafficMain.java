package moreRefactor;

public class trafficMain {
	
	public static void main(String[] args)
	{
		trafficModel model = new trafficModel();
		trafficView view = new trafficView();
		view.trafficViewObservers(model);
		GUI gui = new GUI();
		gui.GUIStart();
		
	}
	
	

}

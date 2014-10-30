package moreRefactor;


public class TrafficConstants {
	
	int CARNUM;

	//called by GUI on start, input the relevent data from the GUI into the constructor
	void setConstantsFromGUI(int carNum){
		
		CARNUM = carNum;

	}
	
	
	int TOPLANESTARTX = 0;
	int STARTY = 150;
	int BOTLANESTARTX = 200;
	int CARHEIGHT = 60;
	int CARWIDTH = 30;
	boolean GLOBALSIMVIEW = true; //is it global view or single car data
	float BASESPEED = 1;
	 
	
	
	
	

}

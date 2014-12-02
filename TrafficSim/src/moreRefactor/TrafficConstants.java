

package moreRefactor;

class TrafficConstants
{
	private static TrafficConstants instance;

	private TrafficConstants(){

		//	AHAHAHAHAHHAHAHAHAHAHAHHA NO INSTANTIATION FOR YOU

	}

	public static synchronized TrafficConstants getInstance(){ // TrafficConstants.getInstance().*; << that is the way you get info from this class

		if(instance == null){
			instance = new TrafficConstants();
		}

		return instance;

	}
	
	boolean isModelReady = false; 

	
	int defaultAggression = 50;
	double defaultBubbleSize = 15;
	
	
	// from GUI, vars may need to be initialized to an actual number because when pulled 
	// 		from gui and not changed there is no default value
	int CARNUM = 2; // defaults to 2 cars
	int LANENUM = 2; // defaults to 2 lanes
	int AGGRESSION = 50; // defaults to an average aggression of a moderate amount, 50 is the middle value
	int CARWIDTH = 80; 
	int CARHEIGHT = 40;

	//called by GUI on start, input the relevent data from the GUI into the constructor
	// getters and setters for all values from the GUI
	void setCARNUM(int carNum)
	{
		CARNUM = carNum;
	}

	int getCARNUM()
	{
		return CARNUM;
	}
	
	void setLANENUM(int laneNum)
	{
		LANENUM = laneNum;
	}

	int getLANENUM()
	{
		return LANENUM;
	}
	
	void setAGGRESSION(int aggression)
	{
		AGGRESSION = aggression;
	}
	
	
	int getAGGRESSION()
	{
		return AGGRESSION;
	}
	
	void setMEDIANSPEED(int medianSpeed) {
		MEDIANSPEED = medianSpeed;
	}

	int MEDIANSPEED = 0;
	int TOPLANESTARTY = 150;
	int BOTLANESTARTY = 250;
	int STARTX = 0; // negative to start off screen. Will alter at later date
	int UPPERBOUND = 101;
	boolean GLOBALSIMVIEW = true; //is it global view or single car data
	float BASESPEED = 1;	
	
}










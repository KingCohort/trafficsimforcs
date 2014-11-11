

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

//	GUI gui = new GUI();
	int CARNUM;
	int LANENUM;
	int AGGRESSION;
	int CARWIDTH = 60; 
	int CARHEIGHT = 30;

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

	int TOPLANESTARTX = 0;
	int STARTY = 150;
	int BOTLANESTARTX = 200;
	boolean GLOBALSIMVIEW = true; //is it global view or single car data
	float BASESPEED = 1;	
}










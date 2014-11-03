package moreRefactor;

public class TrafficConstants
{
	int CARNUM;
	int CARWIDTH = 60; 
	int CARHEIGHT = 30;
	
	
	public void setFromGUI(int carNum){
		this.CARNUM = carNum;
	}

    //called by GUI on start, input the relevent data from the GUI into the constructor
    void setConstantsFromGUI(int carNum)
    {
    	CARNUM = carNum;
    }
    
    int TOPLANESTARTX = 0;
    int STARTY = 150;
    int BOTLANESTARTX = 200;
    boolean GLOBALSIMVIEW = true; //is it global view or single car data
    float BASESPEED = 1;	
}

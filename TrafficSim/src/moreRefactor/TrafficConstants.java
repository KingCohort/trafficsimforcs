

package moreRefactor;

public class TrafficConstants
{
	GUI gui = new GUI();
	int CARNUM;
	int CARWIDTH = 60; 
	int CARHEIGHT = 30;

    //called by GUI on start, input the relevent data from the GUI into the constructor
    public void setConstantsFromGUI(int carNum)
    {
    	System.out.println("set CARNUM IN constants: " + CARNUM);
    	System.out.println("2 set carNum IN constants: " + carNum);
    	CARNUM = carNum;
    }
    
    public int getCarNumConstants()
    {
    	System.out.println("get CARNUM IN constants: " + CARNUM);
    	CARNUM = gui.getCarSpinnerValue();
    	System.out.println("2 get CARNUM IN constants: " + CARNUM);
    	return CARNUM;
    }
    
    int TOPLANESTARTX = 0;
    int STARTY = 150;
    int BOTLANESTARTX = 200;
    boolean GLOBALSIMVIEW = true; //is it global view or single car data
    float BASESPEED = 1;	
}

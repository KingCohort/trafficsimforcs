

package moreRefactor;

public class TrafficConstants
{
	static GUI gui = new GUI();
	static int CARNUM;
	static int CARWIDTH = 60; 
	static int CARHEIGHT = 30;

    //called by GUI on start, input the relevent data from the GUI into the constructor
    public static void setConstantsFromGUI(int carNum)
    {
    	System.out.println("set CARNUM IN constants: " + CARNUM);
    	System.out.println("2 set carNum IN constants: " + carNum);
    	CARNUM = carNum;
    }
    
    public static int getCarNumConstants()
    {
    	System.out.println("get CARNUM IN constants: " + CARNUM);
    	CARNUM = gui.getCarSpinnerValue();
    	System.out.println("2 get CARNUM IN constants: " + CARNUM);
    	return CARNUM;
    }
    
    static int TOPLANESTARTX = 0;
    static int STARTY = 150;
    static int BOTLANESTARTX = 200;
    static boolean GLOBALSIMVIEW = true; //is it global view or single car data
    static float BASESPEED = 1;	
}

package observerPatternRefactor;

import java.util.Observable;
import java.util.Observer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 

import javax.swing.*;
 

import processing.core.PApplet;

public class GUIView extends JFrame implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	//This is where the GUI stuff is created and used. All things related to what the GUI should do/look like goes here
	//There is no environment, there are no cars. This class will generate the GUI
	//The only class who knows about anything besides itself is the controller class. This is not that class
	
	// if the model is the "main" class then it will have to create 
	
//	JButton btnMakeWindow;
//	ControlFrame cf;
//	
//	public GUIView()
//	{
//		this.setLayout(new BorderLayout());
//		
//		btnMakeWindow = new JButton("Run Simulation");
//	}
//	
//	public class ControlFrame extends JFrame
//	{
//		
//	}
//	
//	public class ControlApplet extends PApplet
//	{
//		
//	}
//
//	@Override
//	public void update(Observable o, Object arg)
//	{
//
//	}
}

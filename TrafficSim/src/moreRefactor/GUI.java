

package moreRefactor;

/*
 * 
 * this GUI is built "windowBuilder"
 * 		can be installed from "http://www.eclipse.org/windowbuilder/download.php"
 * 		just how you would install another software into eclipse
 * 			Help > Install New Softare > 
 * 			enter this address(this is for eclipse luna 4.4 you may need another verion): http://download.eclipse.org/windowbuilder/WB/release/R201406251200/4.4/
 * 			the windowBuilder is required and this uses Swing so get both of those
 * 
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import processing.core.PApplet;
import javax.swing.JCheckBox;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	JButton btnStart;

	// these vars are declared here because they need to be accessed from other classes
	//		others are declared in the GUI() because they do not change values
	JSpinner carSpinner;
	int carSpinnerValue;
	JSpinner laneSpinner;
	int laneSpinnerValue;
	JSpinner aggressionSpinner;
	int aggressionSpinnerValue;
	
	JCheckBox followCheckBox;
	JCheckBox loopCheckBox;
	boolean setFalse = false;
	boolean setTrue = true;

	/**
	 * Launch the application.
	 */
	public void GUIStart()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{					
					btnStart.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							// set view
							if (followCheckBox.isSelected())
							{
								TrafficConstants.getInstance().setGLOBALSIMVIEW(setFalse);
							}
							else
							{
								TrafficConstants.getInstance().setGLOBALSIMVIEW(setTrue);
							}
							
							// set loop
							if (loopCheckBox.isSelected())
							{
								TrafficConstants.getInstance().setLOOPING(setFalse);
							}
							else
							{
								TrafficConstants.getInstance().setLOOPING(setTrue);
							}

							PApplet.main("moreRefactor.TrafficView");

						}
					});

					setVisible(true);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI()
	{
		// sets the title of the window
		// creates the window
		// sets the grid type of the GUI
		//		some of the drag and drop aspects are weird (i dont fully understand them) in the design view of windowBuilder
		setTitle("Traffic Sim Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		// the spacing with the layout creates a padding of 5 pixels between each cell 
		gbl_contentPane.columnWidths = new int[]{200, 25, 75, 25, 200, 0}; // {label area, padding, spinner area, padding, explaination area, 0(weirdness with swing??)}
		gbl_contentPane.rowHeights = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0}; // all the rows are the same height, the last 0 is weirdness with swing i dont understand
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; // auto generated (unused)
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE}; // auto generated (unused)
		contentPane.setLayout(gbl_contentPane);

		// LABEL: asking for the number of lanes
		JLabel lblNumberOfLanes = new JLabel("Number of Lanes");
		lblNumberOfLanes.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumberOfLanes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNumberOfLanes = new GridBagConstraints();
		gbc_lblNumberOfLanes.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfLanes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfLanes.gridx = 0;
		gbc_lblNumberOfLanes.gridy = 1;
		contentPane.add(lblNumberOfLanes, gbc_lblNumberOfLanes);

		// spinner for number of lanes
		laneSpinner = new JSpinner();
		laneSpinner.setModel(new SpinnerNumberModel(2, 2, 5, 1));
		laneSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 1;
		contentPane.add(laneSpinner, gbc_spinner);

		// SETTING SPINNER VALUE: sets the value of the number of lanes, allows the spinner value to be called across classes
		laneSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				laneSpinnerValue = (int) ((JSpinner)e.getSource()).getValue();
				TrafficConstants.getInstance().setlaneNum(laneSpinnerValue);
			}
		});

		// EXPLANATION LABEL: explaining the limits of lane choice
		JLabel lblChooseANumber = new JLabel("Choose a number 2 - 5.");
		lblChooseANumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblChooseANumber = new GridBagConstraints();
		gbc_lblChooseANumber.anchor = GridBagConstraints.WEST;
		gbc_lblChooseANumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseANumber.gridx = 4;
		gbc_lblChooseANumber.gridy = 1;
		contentPane.add(lblChooseANumber, gbc_lblChooseANumber);

		// LABEL: asking for the number of cars
		JLabel lblNumberOfCars = new JLabel("Number of Cars");
		lblNumberOfCars.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumberOfCars.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNumberOfCars = new GridBagConstraints();
		gbc_lblNumberOfCars.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfCars.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfCars.gridx = 0;
		gbc_lblNumberOfCars.gridy = 3;
		contentPane.add(lblNumberOfCars, gbc_lblNumberOfCars);

		// spinner for number of cars
		carSpinner = new JSpinner();
		carSpinner.setModel(new SpinnerNumberModel(2, 2, 20, 1));
		carSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.anchor = GridBagConstraints.WEST;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 2;
		gbc_spinner_1.gridy = 3;
		contentPane.add(carSpinner, gbc_spinner_1);

		// SETTING SPINNER VALUE: sets the value of the number of cars, allows the spinner value to be called across classes
		carSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				carSpinnerValue = (int) ((JSpinner)e.getSource()).getValue();
				TrafficConstants.getInstance().setCARNUM(carSpinnerValue);
			}
		});

		// EXPLANATION LABEL: explanation for spinner for number of cars
		JLabel lblChooseANumber_1 = new JLabel("Choose a number 2 - 20.");
		lblChooseANumber_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblChooseANumber_1 = new GridBagConstraints();
		gbc_lblChooseANumber_1.anchor = GridBagConstraints.WEST;
		gbc_lblChooseANumber_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseANumber_1.gridx = 4;
		gbc_lblChooseANumber_1.gridy = 3;
		contentPane.add(lblChooseANumber_1, gbc_lblChooseANumber_1);

		// LABEL: asking for the aggression of the driver
		JLabel lblDriverAggression = new JLabel("Driver Aggression");
		lblDriverAggression.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDriverAggression = new GridBagConstraints();
		gbc_lblDriverAggression.anchor = GridBagConstraints.WEST;
		gbc_lblDriverAggression.insets = new Insets(0, 0, 5, 5);
		gbc_lblDriverAggression.gridx = 0;
		gbc_lblDriverAggression.gridy = 5;
		contentPane.add(lblDriverAggression, gbc_lblDriverAggression);

		// spinner to choose the aggression of the drivers
		aggressionSpinner = new JSpinner();
		aggressionSpinner.setModel(new SpinnerNumberModel(0, 0, 100, 5));
		aggressionSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner_2 = new GridBagConstraints();
		gbc_spinner_2.anchor = GridBagConstraints.WEST;
		gbc_spinner_2.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_2.gridx = 2;
		gbc_spinner_2.gridy = 5;
		contentPane.add(aggressionSpinner, gbc_spinner_2);

		// SETTING SPINNER VALUE: sets the aggression of all the drivers, allows the spinner value to be called across classes
		aggressionSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				aggressionSpinnerValue = (int) ((JSpinner)e.getSource()).getValue();
				TrafficConstants.getInstance().setAGGRESSION(aggressionSpinnerValue);
			}
		});

		// EXPLANATION LABEL: explanation for the aggression of drivers
		JLabel lblThisIsThe = new JLabel("Choose the average aggression of the drivers 1 - 100");
		lblThisIsThe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblThisIsThe = new GridBagConstraints();
		gbc_lblThisIsThe.anchor = GridBagConstraints.WEST;
		gbc_lblThisIsThe.insets = new Insets(0, 0, 5, 5);
		gbc_lblThisIsThe.gridx = 4;
		gbc_lblThisIsThe.gridy = 5;
		contentPane.add(lblThisIsThe, gbc_lblThisIsThe);
				
		JLabel lblNewLabel = new JLabel("View");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 7;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		followCheckBox = new JCheckBox("Follow"); // maybe class var
		followCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 2;
		gbc_chckbxNewCheckBox.gridy = 7;
		contentPane.add(followCheckBox, gbc_chckbxNewCheckBox);
		
		
		
		// EXPLANATION LABEL: explain fixed
		JLabel lblNewLabel_1 = new JLabel("Fixed maintains a vew of a section of the road");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 7;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		// EXPLANATION LABEL: explain follow
		JLabel lblNewLabel_2 = new JLabel("Follow will follow the first car created (checked)");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 4;
		gbc_lblNewLabel_2.gridy = 8;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		
		JLabel lblLoopSimulation = new JLabel("Loop Simulation");
		lblLoopSimulation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblLoopSimulation = new GridBagConstraints();
		gbc_lblLoopSimulation.anchor = GridBagConstraints.WEST;
		gbc_lblLoopSimulation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoopSimulation.gridx = 0;
		gbc_lblLoopSimulation.gridy = 10;
		contentPane.add(lblLoopSimulation, gbc_lblLoopSimulation);
		
		loopCheckBox = new JCheckBox("Loop"); // maybe class var
		loopCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxLoop = new GridBagConstraints();
		gbc_chckbxLoop.anchor = GridBagConstraints.WEST;
		gbc_chckbxLoop.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxLoop.gridx = 2;
		gbc_chckbxLoop.gridy = 10;
		contentPane.add(loopCheckBox, gbc_chckbxLoop);
		
		JLabel lblThisWillContinue = new JLabel("This will continue to generate cars off screen");
		lblThisWillContinue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblThisWillContinue = new GridBagConstraints();
		gbc_lblThisWillContinue.anchor = GridBagConstraints.WEST;
		gbc_lblThisWillContinue.insets = new Insets(0, 0, 5, 5);
		gbc_lblThisWillContinue.gridx = 4;
		gbc_lblThisWillContinue.gridy = 10;
		contentPane.add(lblThisWillContinue, gbc_lblThisWillContinue);
		
		// the start button, starts the simulation
		btnStart = new JButton("START SIMULATION");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 0, 5);
		gbc_btnStart.gridx = 4;
		gbc_btnStart.gridy = 13;
		contentPane.add(btnStart, gbc_btnStart);		
	}	
}





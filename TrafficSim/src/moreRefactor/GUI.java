

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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import processing.core.PApplet;

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
    JRadioButton radioButtonLow;
    String bttnLow;
    int bttnLowValue;
    JRadioButton radioButtonMedium;
    String bttnMedium;
    int bttnMediumValue;
    JRadioButton radioButtonHigh;
    String bttnHigh;
    int bttnHighValue;
    
    String radioButtonAnswer;

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
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{104, 65, 51, 56, 73, 57, 150, 95, 131, 0};
		gbl_contentPane.rowHeights = new int[]{35, 22, 35, 22, 48, 25, 258, 23, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		// LABEL: asking for the number of lanes
		JLabel lblNumberOfLanes = new JLabel("Number of Lanes");
		lblNumberOfLanes.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumberOfLanes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNumberOfLanes = new GridBagConstraints();
		gbc_lblNumberOfLanes.gridwidth = 2;
		gbc_lblNumberOfLanes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfLanes.gridx = 0;
		gbc_lblNumberOfLanes.gridy = 1;
		contentPane.add(lblNumberOfLanes, gbc_lblNumberOfLanes);

		// spinner for number of lanes
		laneSpinner = new JSpinner();
		laneSpinner.setModel(new SpinnerNumberModel(2, 2, 5, 1));
		laneSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.NORTH;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 4;
		gbc_spinner.gridy = 1;
		contentPane.add(laneSpinner, gbc_spinner);
		
		// SETTING SPINNER VALUE: sets the value of the number of lanes
		laneSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				laneSpinnerValue = (int) ((JSpinner)e.getSource()).getValue();
				TrafficConstants.getInstance().setLANENUM(laneSpinnerValue);
			}
		});
		
		// EXPLANATION LABEL: explaining the limits of lane choice
		JLabel lblChooseANumber = new JLabel("Choose a number 2 - 5.");
		lblChooseANumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblChooseANumber = new GridBagConstraints();
		gbc_lblChooseANumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseANumber.gridx = 6;
		gbc_lblChooseANumber.gridy = 1;
		contentPane.add(lblChooseANumber, gbc_lblChooseANumber);
		
		// LABEL: asking for the number of cars
		JLabel lblNumberOfCars = new JLabel("Number of Cars");
		lblNumberOfCars.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumberOfCars.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNumberOfCars = new GridBagConstraints();
		gbc_lblNumberOfCars.gridwidth = 2;
		gbc_lblNumberOfCars.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfCars.gridx = 0;
		gbc_lblNumberOfCars.gridy = 3;
		contentPane.add(lblNumberOfCars, gbc_lblNumberOfCars);

		// spinner for number of cars
		carSpinner = new JSpinner();
		carSpinner.setModel(new SpinnerNumberModel(1, 1, 20, 1));
		carSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.anchor = GridBagConstraints.NORTH;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 4;
		gbc_spinner_1.gridy = 3;
		contentPane.add(carSpinner, gbc_spinner_1);
		
		// SETTING SPINNER VALUE: allows the spinner value to be called across classes
		carSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				carSpinnerValue = (int) ((JSpinner)e.getSource()).getValue();
				TrafficConstants.getInstance().setCARNUM(carSpinnerValue);
			}
		});
		
		// EXPLANATION LABEL: explanation for spinner for number of cars
		JLabel lblChooseANumber_1 = new JLabel("Choose a number 1 - 20.");
		lblChooseANumber_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblChooseANumber_1 = new GridBagConstraints();
		gbc_lblChooseANumber_1.anchor = GridBagConstraints.WEST;
		gbc_lblChooseANumber_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseANumber_1.gridx = 6;
		gbc_lblChooseANumber_1.gridy = 3;
		contentPane.add(lblChooseANumber_1, gbc_lblChooseANumber_1);

		// LABEL: asking for the state of mind of the "user" driver
		JLabel lblStateOfMind = new JLabel("State of Mind");
		lblStateOfMind.setHorizontalAlignment(SwingConstants.LEFT);
		lblStateOfMind.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblStateOfMind = new GridBagConstraints();
		gbc_lblStateOfMind.insets = new Insets(0, 0, 5, 5);
		gbc_lblStateOfMind.gridx = 0;
		gbc_lblStateOfMind.gridy = 5;
		contentPane.add(lblStateOfMind, gbc_lblStateOfMind);

		// low radio button
		radioButtonLow = new JRadioButton("Low");
		radioButtonLow.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnLow = new GridBagConstraints();
		gbc_rdbtnLow.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnLow.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLow.gridx = 2;
		gbc_rdbtnLow.gridy = 5;
		contentPane.add(radioButtonLow, gbc_rdbtnLow);
		
//		radioButtonLow.addChangeListener(new ChangeListener()
//		{
//			public void stateChanged(ChangeEvent e)
//			{
//				carSpinnerValue =  e.getSource().getText();
//				TrafficConstants.getInstance().setCARNUM(carSpinnerValue);
//			}
//		});

		// medium radio button
		radioButtonMedium = new JRadioButton("Medium");
		radioButtonMedium.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnMedium = new GridBagConstraints();
		gbc_rdbtnMedium.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnMedium.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMedium.gridx = 4;
		gbc_rdbtnMedium.gridy = 5;
		contentPane.add(radioButtonMedium, gbc_rdbtnMedium);

		// high radio button
		radioButtonHigh = new JRadioButton("High");
		radioButtonHigh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnHigh = new GridBagConstraints();
		gbc_rdbtnHigh.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnHigh.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnHigh.gridx = 6;
		gbc_rdbtnHigh.gridy = 5;
		contentPane.add(radioButtonHigh, gbc_rdbtnHigh);
		
		////////-----TESTING-----/////////////////////////////////////////////////////////
		
		// dont fux wit dis
		// everything else should work
		
//		ActionListener actionListener = new ActionListener()
//		{
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				if (e.getSource() instanceof JRadioButton)
//				{
//					JRadioButton radioButton = (JRadioButton) e.getSource();
//					if (radioButton.isSelected())
//					{
//						radioButtonAnswer.setText(radioButton.getText());
//					}
//				}
//				
//			}
//		};
		
		////////-----END TESTING-----/////////////////////////////////////////////////////

		// the start button
		btnStart = new JButton("START SIMULATION");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnStart.gridx = 7;
		gbc_btnStart.gridy = 7;
		contentPane.add(btnStart, gbc_btnStart);

		// create new radio button group
		ButtonGroup radioBtnGroupStateOfMind = new ButtonGroup(); // this may jump around in the code when switching between the design and source view when things are changed (windowBuilder weirdness)
		radioBtnGroupStateOfMind.add(radioButtonHigh);
		radioBtnGroupStateOfMind.add(radioButtonMedium);
		radioBtnGroupStateOfMind.add(radioButtonLow);
		
		
	}	
}





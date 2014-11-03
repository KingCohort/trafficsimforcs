

package moreRefactor;

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
import processing.core.PApplet;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnStart;
	
	JSpinner carSpinner;
    int carSpinnerValue;

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
							//System.out.println("creating a model");
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

		JLabel lblNumberOfLanes = new JLabel("Number of Lanes");
		lblNumberOfLanes.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumberOfLanes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNumberOfLanes = new GridBagConstraints();
		gbc_lblNumberOfLanes.gridwidth = 2;
		gbc_lblNumberOfLanes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfLanes.gridx = 0;
		gbc_lblNumberOfLanes.gridy = 1;
		contentPane.add(lblNumberOfLanes, gbc_lblNumberOfLanes);

		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(2, 2, 5, 1));
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.NORTH;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 4;
		gbc_spinner.gridy = 1;
		contentPane.add(spinner, gbc_spinner);

		JLabel lblChooseANumber = new JLabel("Choose a number 2 - 5.");
		lblChooseANumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblChooseANumber = new GridBagConstraints();
		gbc_lblChooseANumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseANumber.gridx = 6;
		gbc_lblChooseANumber.gridy = 1;
		contentPane.add(lblChooseANumber, gbc_lblChooseANumber);

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
		// change var name
		carSpinner = new JSpinner();
		carSpinner.setModel(new SpinnerNumberModel(1, 1, 20, 1));
		carSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.anchor = GridBagConstraints.NORTH;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 4;
		gbc_spinner_1.gridy = 3;
		contentPane.add(carSpinner, gbc_spinner_1);
		
		carSpinnerValue = (int) carSpinner.getValue(); // fucked
		
		// explanation for spinner for number of cars
		JLabel lblChooseANumber_1 = new JLabel("Choose a number 1 - 20.");
		lblChooseANumber_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblChooseANumber_1 = new GridBagConstraints();
		gbc_lblChooseANumber_1.anchor = GridBagConstraints.WEST;
		gbc_lblChooseANumber_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseANumber_1.gridx = 6;
		gbc_lblChooseANumber_1.gridy = 3;
		contentPane.add(lblChooseANumber_1, gbc_lblChooseANumber_1);

		// asking for the state of mind of the "user" driver
		JLabel lblStateOfMind = new JLabel("State of Mind");
		lblStateOfMind.setHorizontalAlignment(SwingConstants.LEFT);
		lblStateOfMind.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblStateOfMind = new GridBagConstraints();
		gbc_lblStateOfMind.insets = new Insets(0, 0, 5, 5);
		gbc_lblStateOfMind.gridx = 0;
		gbc_lblStateOfMind.gridy = 5;
		contentPane.add(lblStateOfMind, gbc_lblStateOfMind);

		// low radio button
		JRadioButton rdbtnLow = new JRadioButton("Low");
		rdbtnLow.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnLow = new GridBagConstraints();
		gbc_rdbtnLow.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnLow.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLow.gridx = 2;
		gbc_rdbtnLow.gridy = 5;
		contentPane.add(rdbtnLow, gbc_rdbtnLow);

		// medium radio button
		JRadioButton rdbtnMedium = new JRadioButton("Medium");
		rdbtnMedium.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnMedium = new GridBagConstraints();
		gbc_rdbtnMedium.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnMedium.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMedium.gridx = 4;
		gbc_rdbtnMedium.gridy = 5;
		contentPane.add(rdbtnMedium, gbc_rdbtnMedium);

		// high radio button
		JRadioButton rdbtnHigh = new JRadioButton("High");
		rdbtnHigh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnHigh = new GridBagConstraints();
		gbc_rdbtnHigh.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnHigh.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnHigh.gridx = 6;
		gbc_rdbtnHigh.gridy = 5;
		contentPane.add(rdbtnHigh, gbc_rdbtnHigh);

		// the start button
		btnStart = new JButton("START SIMULATION");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnStart.gridx = 7;
		gbc_btnStart.gridy = 7;
		contentPane.add(btnStart, gbc_btnStart);

		// create new radio button group
		ButtonGroup radioBtnGroupStateOfMind = new ButtonGroup(); // this may need to be moved up above the radio buttons cause it moved the .add()'s to the radio buttons initialization 
		radioBtnGroupStateOfMind.add(rdbtnHigh);
		radioBtnGroupStateOfMind.add(rdbtnMedium);
		radioBtnGroupStateOfMind.add(rdbtnLow);
	}	

	public JSpinner getCarSpinner()
	{
		return carSpinner;
	}

	public void setCarSpinner(JSpinner carSpinner)
	{
		this.carSpinner = carSpinner;
	}

	public int getCarSpinnerValue()
	{
		carSpinnerValue = (int) carSpinner.getValue();
		System.out.println("get car spinner IN gui : " + carSpinnerValue);
		return carSpinnerValue;
	}
	
	public void setCarSpinnerValue(int carSpinnerValue)
	{
		//this.carSpinnerValue = carSpinnerValue;
		System.out.println("set car spinner IN gui : " + carSpinnerValue);
		this.carSpinnerValue = (int) carSpinner.getValue();
	}
	
	
	
	
}





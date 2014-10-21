package observerPatternRefactor;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder; // wat


public class GUIView2 extends JFrame {
	
	// this is just for the purpose of getting the gui down before makeing it the actual gui class
	// dont delete either class
	// this class may not work if you dont have windowBuilder installed 
		// if you dont (steve and noel) it shouldnt matter for what you are working on
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIView2 frame = new GUIView2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIView2() {
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
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(1, 1, 20, 1));
		spinner_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.anchor = GridBagConstraints.NORTH;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 4;
		gbc_spinner_1.gridy = 3;
		contentPane.add(spinner_1, gbc_spinner_1);
		
		JLabel lblChooseANumber_1 = new JLabel("Choose a number 1 - 20.");
		lblChooseANumber_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblChooseANumber_1 = new GridBagConstraints();
		gbc_lblChooseANumber_1.anchor = GridBagConstraints.WEST;
		gbc_lblChooseANumber_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseANumber_1.gridx = 6;
		gbc_lblChooseANumber_1.gridy = 3;
		contentPane.add(lblChooseANumber_1, gbc_lblChooseANumber_1);
		
		JLabel lblStateOfMind = new JLabel("State of Mind");
		lblStateOfMind.setHorizontalAlignment(SwingConstants.LEFT);
		lblStateOfMind.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblStateOfMind = new GridBagConstraints();
		gbc_lblStateOfMind.insets = new Insets(0, 0, 5, 5);
		gbc_lblStateOfMind.gridx = 0;
		gbc_lblStateOfMind.gridy = 5;
		contentPane.add(lblStateOfMind, gbc_lblStateOfMind);
		
		JRadioButton rdbtnLow = new JRadioButton("Low");
		rdbtnLow.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnLow = new GridBagConstraints();
		gbc_rdbtnLow.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnLow.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLow.gridx = 2;
		gbc_rdbtnLow.gridy = 5;
		contentPane.add(rdbtnLow, gbc_rdbtnLow);
		
		JRadioButton rdbtnMedium = new JRadioButton("Medium");
		rdbtnMedium.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnMedium = new GridBagConstraints();
		gbc_rdbtnMedium.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnMedium.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMedium.gridx = 4;
		gbc_rdbtnMedium.gridy = 5;
		contentPane.add(rdbtnMedium, gbc_rdbtnMedium);
		
		JRadioButton rdbtnHigh = new JRadioButton("High");
		rdbtnHigh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnHigh = new GridBagConstraints();
		gbc_rdbtnHigh.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnHigh.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnHigh.gridx = 6;
		gbc_rdbtnHigh.gridy = 5;
		contentPane.add(rdbtnHigh, gbc_rdbtnHigh);
		
		JButton btnStartSimulation = new JButton("START SIMULATION");
		GridBagConstraints gbc_btnStartSimulation = new GridBagConstraints();
		gbc_btnStartSimulation.gridwidth = 2;
		gbc_btnStartSimulation.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnStartSimulation.gridx = 7;
		gbc_btnStartSimulation.gridy = 7;
		contentPane.add(btnStartSimulation, gbc_btnStartSimulation);
		
			// create new radio button group
			ButtonGroup radioBtnGroup = new ButtonGroup(); // this may need to be moved up above the radio buttons cause it moved the .add()'s to the radio buttons initialization 
			radioBtnGroup.add(rdbtnHigh);
			radioBtnGroup.add(rdbtnMedium);
			radioBtnGroup.add(rdbtnLow);
	}
}





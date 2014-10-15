package observerPatternRefactor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JSpinner;
import java.awt.Insets;
import javax.swing.SpinnerNumberModel;

public class GUIView2 extends JFrame {
	
	// this is just for the purpose of getting the gui down before makeing it the actual gui class
	// dont delete either class
	// this class may not work if you dont have windowBuilder installed 
		// if you dont (steve and noel) it shouldnt matter for what you are working on
	

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
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// grid for all the components to be put into
		// i think i chose the grid that is more responsive (for resizing and stuff)
		// all elements will be placed in this and should be easy to move around
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		// label for the field that is being chosen
		JLabel lblNumberOfLanes = new JLabel("Number of Lanes");
		lblNumberOfLanes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNumberOfLanes = new GridBagConstraints();
		gbc_lblNumberOfLanes.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumberOfLanes.gridx = 0;
		gbc_lblNumberOfLanes.gridy = 1;
		contentPane.add(lblNumberOfLanes, gbc_lblNumberOfLanes);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.gridx = 4;
		gbc_spinner.gridy = 1;
		contentPane.add(spinner, gbc_spinner);
	}

}

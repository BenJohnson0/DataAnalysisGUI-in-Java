//
//  Ben Johnson C20316733 
//  Java assignment started Thursday ‎10 ‎March ‎2022, ‏‎15:00
//
//  Program designed to display facts and pieces
//  of data from a dataset using user input from
//  a GUI. SQL commands are used to retrieve the
//  data from a database.
//

package Data_Analysis;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyDataGUI extends JFrame implements ActionListener
{
	//attributes
	JTextField year_entry, area_entry;
	JButton randomFactButton, displayButton;
	JPanel randomFactPanel, yearPanel, areaPanel, displayPanel;
	JLabel yearLabel, areaLabel;
	JFrame frame;
	
	boolean flag = false;
	
	int year;
	int index = 0;
	
	String area;
	public static final String[] regions = new String[] {"National", "Dublin", "Cork", "Galway", "Limerick", "Other"};
		
	//methods
	public MyDataGUI(String title)
	{
		//layout
		super(title);
		setVisible(true);
		setSize(700, 500);
		setLayout(new FlowLayout());
		
		
		//	 generate random fact	//
		randomFactPanel = new JPanel();
		randomFactPanel.setLayout(new FlowLayout());
		add(randomFactPanel);
		
		randomFactButton = new JButton("Display a random fact!");
		randomFactPanel.add(randomFactButton);
		randomFactButton.setToolTipText("This button will a random fact about this dataset");
		randomFactButton.addActionListener(this);
		
		
		//	 specify year	//
		yearPanel = new JPanel();
		add(yearPanel);
		yearPanel.setLayout(new FlowLayout());
		
		yearLabel = new JLabel("Year:");
		yearPanel.add(yearLabel);
		
		year_entry = new JTextField("1976 - 2015");
		yearPanel.add(year_entry);
		
		//	 specify area	//
		areaPanel = new JPanel();
		add(areaPanel);
		areaPanel.setLayout(new FlowLayout());
		
		areaLabel = new JLabel("Area:");
		areaPanel.add(areaLabel);
		
		area_entry = new JTextField("National, Dublin, Cork, Galway, Limerick, Other");
		areaPanel.add(area_entry);
		
		//	 confirm entries	//
		displayPanel = new JPanel();
		displayPanel.setLayout(new FlowLayout());
		add(displayPanel);
		
		displayButton = new JButton("Display data");
		displayPanel.add(displayButton);
		displayButton.setToolTipText("Display data for specified terms");
		displayButton.addActionListener(this);
		}//close MyDataGUI constructor
	

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == randomFactButton)
			{
				JOptionPane.showMessageDialog(frame, "button 1 pressed");
			}//end if 
			
			//error check for invalid entries
			if(e.getSource() == displayButton)
			{
				year = Integer.parseInt(year_entry.getText()); //year_entry input converted to text string
				area = area_entry.getText(); //area_entry input converted to text string
				
				for (int i=0; i<=regions.length-1; i++) 
				{
				    if (area.contains(regions[i])) 
				    {
				        flag = true;
				        index = i;
				    }//end if
				}// end for
				
				
				if( (year > 1976 && year < 2015) && (flag == true) )
				{
					JOptionPane.showMessageDialog(frame, "Yes");
				}//end if
				
				else if (year <= 1976 || year >= 2015)
				{
					JOptionPane.showMessageDialog(frame, "Enter a year between 1976 and 2015!");
				}//end else if
				
				else if (flag == false)
				{
					JOptionPane.showMessageDialog(frame, "Enter a valid region!");
				}//end else if
				 
				
			}//end if button2
			
		}//end if action e
		
	}//end class


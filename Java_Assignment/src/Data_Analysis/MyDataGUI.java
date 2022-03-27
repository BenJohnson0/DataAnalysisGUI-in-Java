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
	JTextField year_entry, education_entry, sex_entry;
	JButton displayButton;
	JPanel MainPanel, yearPanel, areaPanel, displayPanel;
	JLabel yearLabel, educationLabel, sexLabel;
	JFrame frame;
	
	boolean flag = false;
	
	int year;
	int index = 0;
	
	String area;
	public static final String[] regions = new String[] {"National", "Dublin", "Cork", "Galway", "Limerick", "Other"};
		
	//methods
	//MyDataGUI constructor
	public MyDataGUI(String title)
	{
		//layout
		super(title);
		setVisible(true);
		setSize(700, 500);
		setLayout(new FlowLayout());
		
		MainPanel = new JPanel();
		MainPanel.setLayout(new FlowLayout());
		add(MainPanel);
		
		yearLabel = new JLabel("Year:");
		MainPanel.add(yearLabel);
		
		year_entry = new JTextField("\t");
		year_entry.setToolTipText("Select a year between 2009 and 2021");
		MainPanel.add(year_entry);
		
		sexLabel = new JLabel("Sex:");
		MainPanel.add(sexLabel);
		
		sex_entry = new JTextField("\t");
		sex_entry.setToolTipText("Select Male, Female or Both Sexes");
		MainPanel.add(sex_entry);
		
		educationLabel = new JLabel("Education Level:");
		MainPanel.add(educationLabel);
		
		education_entry = new JTextField("\t");
		education_entry.setToolTipText("Select Primary, Lower/Upper Secondary, Post leaving cert, Third level, Ordinary bachelor, Honours bachelor or Postgrad");
		MainPanel.add(education_entry);
		
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
			/*
			if(e.getSource() == randomFactButton)
			{
				JOptionPane.showMessageDialog(frame, "button 1 pressed");
			}//end if 
			*/
			
			//error check for invalid entries
			if(e.getSource() == displayButton)
			{
				year = Integer.parseInt(year_entry.getText()); //year_entry input converted to text string
				//area = area_entry.getText(); //area_entry input converted to text string
				
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


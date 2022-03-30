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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.io.*;
import java.lang.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MyDataGUI extends JFrame implements ActionListener
{
	//attributes
	JTextField year_entry, education_entry, sex_entry, query_entry, SQL_entry;
	JButton displayButton, SQLButton;
	JPanel MainPanel, displayPanel, SQLPanel;
	JLabel yearLabel, educationLabel, sexLabel, queryLabel, SQLLabel;
	JFrame frame;
	JTable table;
	
	boolean education_flag = false;
	boolean sex_flag = false;
	
	int year;
	int index = 0;
	
	String sex, ed, quoted_sex_entry, quoted_education_entry;
	public static final String[] levels = new String[] {"Primary", "Lower Secondary", "Upper Secondary", "Leaving certificate", "Third level", "Higher certificate", "Ordinary bachelor", "Honours bachelor", "Postgradute"};
	public static final String[] sexes = new String[] {"Male", "Female", "Both"};

	
	//MyDataGUI constructor
	public MyDataGUI(String title)
	{
		//layout
		super(title);
		setVisible(true);
		setSize(600, 300);
		setLayout(new FlowLayout());
		
		MainPanel = new JPanel();
		MainPanel.setLayout(new FlowLayout());
		add(MainPanel);
		
		yearLabel = new JLabel("Year:");
		MainPanel.add(yearLabel);
		
		year_entry = new JTextField("Enter year");
		year_entry.setToolTipText("Select a year between 2009 and 2021");
		MainPanel.add(year_entry);
		
		sexLabel = new JLabel("Sex:");
		MainPanel.add(sexLabel);
		
		sex_entry = new JTextField("Enter sex");
		sex_entry.setToolTipText("Select Male, Female or Both");
		MainPanel.add(sex_entry);
		
		educationLabel = new JLabel("Education Level:");
		MainPanel.add(educationLabel);
		
		education_entry = new JTextField("Enter education");
		education_entry.setToolTipText("Select Primary, Lower/Upper Secondary, Leaving certificate, Third level, Higher certificate, Ordinary bachelor, Honours bachelor or Postgradute");
		MainPanel.add(education_entry);
		
		//	 confirm entries	//	
		displayButton = new JButton("Display data");
		MainPanel.add(displayButton);
		displayButton.setToolTipText("Display data for specified terms");
		displayButton.addActionListener(this);
		
		
		// user input SQL code //
		SQLPanel = new JPanel();
		SQLPanel.setLayout(new FlowLayout());
		add(SQLPanel);
		SQLPanel.isVisible();
		
		SQLLabel = new JLabel("SQL:");
		SQLPanel.add(SQLLabel);
		
		SQL_entry = new JTextField("Enter SQL Query");
		SQL_entry.setToolTipText("Create your own SQL query here");
		SQLPanel.add(SQL_entry);
		
		SQLButton = new JButton("Submit");
		SQLPanel.add(SQLButton);
		SQLButton.setToolTipText("Submit your SQL query");
		SQLButton.addActionListener(this);
		}//close MyDataGUI constructor
	

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// button for user input SQL
			if(e.getSource() == SQLButton) {
				
				try
				{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // load the driver
				}
				   
				//Catch driver loading errors
				catch (ClassNotFoundException f)
				{
					System.out.println ("Could not load the driver.");
				}

				try {
					
				//URL for connection to the database
				String url = "jdbc:sqlserver://localhost;Database=JavaAssignment;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true";

				//Register the driver
				DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());   

				// Connecting to the database
				Connection conn = DriverManager.getConnection(url);
				System.out.println ("\n\n- - - - - - - - - - - -");
				System.out.println ("Connection successful");
				System.out.println ("- - - - - - - - - - - -\n\n");
				System.out.println("Your Query: " +SQL_entry.getText()+ "\n");

				//Create statement object for running SQL clauses
				Statement stmt = conn.createStatement ();  

				//Query query_entry.getText(), education_entry.getText(), year_entry.getText(), sex_entry.getText() 
				ResultSet rset = stmt.executeQuery (SQL_entry.getText());
				
				//create table for displaying results in the GUI
				String columns[] = { "Year", "Sex", "Education Level", "Unemployment Rate" };
			    String data[][] = new String[360][4];
			    
			    //loop through the results and populate the JTable
			    int i = 0;
			    while (rset.next()) {
			        String t_year = rset.getString("d_Year");
			        String t_sex = rset.getString("d_Sex");
			        String t_ed = rset.getString("d_EducationLevel");
			        String t_val = rset.getString("d_Value");
			        data[i][0] = t_year + "";
			        data[i][1] = t_sex;
			        data[i][2] = t_ed;
			        data[i][3] = t_val;
			        i++;
			    }
			    
			    DefaultTableModel model = new DefaultTableModel(data, columns);
			    JTable table = new JTable(model);
			    table.setShowGrid(true);
			    table.setShowVerticalLines(true);
			    
			    JScrollPane pane = new JScrollPane(table);
			    JFrame f = new JFrame("Results from SQL Query");
			    JPanel panel = new JPanel();
			    
			    panel.add(pane);
			    f.add(panel);
			    f.setSize(500, 250);
			    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    f.setVisible(true);
				  
				stmt.close();  // Close the statement object
				conn.close();   // Close the connection
				}//close try
				   
				catch(SQLException g)
				{
					System.err.print("SQLException: ");
					System.err.println(g.getMessage());
				}//close catch
				
				}	
			
			// button for set queries
			if(e.getSource() == displayButton) {
				
			quoted_sex_entry = "\'" + sex_entry.getText() + "\'";
			quoted_education_entry = "\'" + education_entry.getText() + "\'";
			
			//Load Driver
			try
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // load the driver
			}
			   
			//Catch driver loading errors
			catch (ClassNotFoundException f)
			{
				System.out.println ("Could not load the driver.");
			}

			try {
				
			//URL for connection to the database
			String url = "jdbc:sqlserver://localhost;Database=JavaAssignment;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true";

			//Register the driver
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());   

			// Connecting to the database
			Connection conn = DriverManager.getConnection(url);
			System.out.println ("\n\n- - - - - - - - - - - -");
			System.out.println ("Connection successful");
			System.out.println ("- - - - - - - - - - - -");
			System.out.println ("Results:\n");

			//Create statement object for running SQL clauses
			Statement stmt = conn.createStatement ();  

			//Query query_entry.getText(), education_entry.getText(), year_entry.getText(), sex_entry.getText() 
			ResultSet rset = stmt.executeQuery ("select * from EducationData where d_Year = " +year_entry.getText()+ 
					"and d_Sex = " +quoted_sex_entry+ "and d_EducationLevel = " +quoted_education_entry);
			
			   
			while (rset.next()) 
			{
				System.out.println(rset.getString(4)+ "% Unemployment rate.");
			}
			   
			stmt.close();  // Close the statement object
			conn.close();   // Close the connection
			}//close try
			   
			catch(SQLException g)
			{
				System.err.print("SQLException: ");
				System.err.println(g.getMessage());
			}//close catch
			
			}
			
			/*error check for invalid entries
			if(e.getSource() == displayButton)
			{
				//convert input into text strings
				year = Integer.parseInt(year_entry.getText()); 
				sex = sex_entry.getText();
				ed = education_entry.getText();
				
				for (int i=0; i <= levels.length-1; i++) 
				{
				    if (ed.contains(levels[i])) 
				    {
				    	education_flag = true;
				        index = i;
				    }//end if
				}// end for
				
				for (int i=0; i <= sexes.length-1; i++) 
				{
				    if (sex.contains(sexes[i])) 
				    {
				    	sex_flag = true;
				        index = i;
				    }//end if
				}// end for
				
				if ( year >= 2009 && year <= 2021 && sex_flag == true && education_flag == true )
				{
					JOptionPane.showMessageDialog(frame, "Yes");
				}
					
				
				/////////// ADD ERROR CHECKING //////////////
				/*
				else if (year < 2009 || year > 2021)
				{
					JOptionPane.showMessageDialog(frame, "Enter a year between 2009 and 2021!");
				}//end else if
				
				else if (sex_flag = false)
				{
					JOptionPane.showMessageDialog(frame, "Enter a valid sex!");
				}//end else if
				
				else if (education_flag = false)
				{
					JOptionPane.showMessageDialog(frame, "Enter a valid education level!");
				}//end else if
				*/ 
			
	
			}
	}//end class


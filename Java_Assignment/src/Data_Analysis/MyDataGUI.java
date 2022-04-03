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
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.general.DefaultPieDataset;

public class MyDataGUI extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	//attributes
	JTextField year_entry, education_entry, sex_entry, query_entry, SQL_entry;
	JButton displayButton, SQLButton, RandButton;
	JPanel MainPanel, displayPanel, SQLPanel, RandPanel;
	JLabel yearLabel, educationLabel, sexLabel, queryLabel, SQLLabel;
	JFrame frame;
	JTable table;
	
	boolean education_flag = false;
	boolean sex_flag = false;
	
	int year;
	int index = 0;
	
	String sex, ed, quoted_sex_entry, quoted_education_entry;
	//for JButton
	public static final String[] years = new String[] {"2009", "2010",  "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021"};
	public static final String[] levels = new String[] {"Primary", "Lower Secondary", "Upper Secondary", "Leaving certificate", "Third level", "Higher certificate", "Ordinary bachelor", "Honours bachelor", "Postgradute"};
	public static final String[] sexes = new String[] {"Male", "Female", "Both"};

	
	//	MyDataGUI constructor
	public MyDataGUI(String title)
	{
		//	layout
		super(title);
		setVisible(true);
		setSize(800, 300);
		setLayout(new FlowLayout());
		
		//	create panel
		MainPanel = new JPanel();
		MainPanel.setLayout(new FlowLayout());
		add(MainPanel);
		
		//	year label
		yearLabel = new JLabel("Year:");
		MainPanel.add(yearLabel);
		
		//	year textField
		year_entry = new JTextField("Enter year");
		year_entry.setToolTipText("Select a year between 2009 and 2021");
		MainPanel.add(year_entry);

		/*	drop down menu for yearEntry
		JComboBox yearList = new JComboBox(years);
		yearList.setSelectedIndex(12);
		yearList.addActionListener(this);
		MainPanel.add(yearList);
		*/
		
		//	sex label
		sexLabel = new JLabel("Sex:");
		MainPanel.add(sexLabel);
		
		//	sex textField
		sex_entry = new JTextField("Enter sex");
		sex_entry.setToolTipText("Select Male, Female or Both");
		MainPanel.add(sex_entry);
		
		/*
		JComboBox sexList = new JComboBox(sexes);
		sexList.setSelectedIndex(1);
		sexList.addActionListener(this);
		MainPanel.add(sexList);
		*/
		
		//	education label
		educationLabel = new JLabel("Education Level:");
		MainPanel.add(educationLabel);
		
		//	education textField
		education_entry = new JTextField("Enter education");
		education_entry.setToolTipText("Select Primary, Lower/Upper Secondary, Leaving certificate, Third level, Higher certificate, Ordinary bachelor, Honours bachelor or Postgradute");
		MainPanel.add(education_entry);
		
		/*
		JComboBox educationList = new JComboBox(levels);
		educationList.setSelectedIndex(1);
		educationList.addActionListener(this);
		MainPanel.add(educationList);
		*/
		
		//	confirm entries		
		displayButton = new JButton("Display data");
		MainPanel.add(displayButton);
		displayButton.setToolTipText("Display data for specified terms");
		displayButton.addActionListener(this);
		
		
		//	user input SQL code 
		SQLPanel = new JPanel();
		SQLPanel.setLayout(new FlowLayout());
		add(SQLPanel);
		SQLPanel.isVisible();
		
		SQLLabel = new JLabel("SQL:");
		SQLPanel.add(SQLLabel);
		
		SQL_entry = new JTextField("select * from EducationData");
		SQL_entry.setToolTipText("Create your own SQL query here");
		SQLPanel.add(SQL_entry);
		
		SQLButton = new JButton("Submit");
		SQLPanel.add(SQLButton);
		SQLButton.setToolTipText("Submit your SQL query");
		SQLButton.addActionListener(this);
		
		// random button
		RandButton = new JButton("Random Fact");
		SQLPanel.add(RandButton);
		RandButton.setToolTipText("Click here to get a random fact about the data!");
		RandButton.addActionListener(this);
		
                
		}//close MyDataGUI constructor
				
		public void actionPerformed(ActionEvent e) 
		{
			
			Object obj = e.getSource();
			
			// button for user input SQL
			if(obj == SQLButton) {
				
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
			    }//close while
			    
			    //create JTable for the data
			    DefaultTableModel model = new DefaultTableModel(data, columns);
			    JTable table = new JTable(model);
			    table.setShowGrid(true);
			    table.setShowVerticalLines(true);
			    
			    JScrollPane pane = new JScrollPane(table);
			    JFrame f = new JFrame("Results from: " +SQL_entry.getText());
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
			}//end SQLButton
			
			
		 
			//////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			// button for set queries
			else if(obj == displayButton) {
				
			quoted_sex_entry = "\'" + sex_entry.getText() + "\'";
			quoted_education_entry = "\'" + education_entry.getText() + "\'";
			
			/*attempting error checking
			if (sex_entry.getText() != "Male" || sex_entry.getText() != "Female" || sex_entry.getText() != "Both") {
				JOptionPane.showMessageDialog(frame, "Enter Male, Female or Both!");	
				System.out.println (sex_entry.getText());
				System.exit(1);
			*/
		
			//Load Driver
			try
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // load the driver
			}
			   
			//Catch driver loading errors
			catch (ClassNotFoundException g)
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
			System.out.println ("- - - - - - - - - - - -\n");

			//Create statement object for running SQL clauses
			Statement stmt = conn.createStatement ();  

			//Query query_entry.getText(), education_entry.getText(), year_entry.getText(), sex_entry.getText() 
			ResultSet rset = stmt.executeQuery ("select * from EducationData where d_Year = " +year_entry.getText()+ 
					"and d_Sex = " +quoted_sex_entry+ "and d_EducationLevel = " +quoted_education_entry);
   
			while (rset.next()) 
			{
				JOptionPane.showMessageDialog(frame, rset.getString(4)+ "% Unemployment rate.");
			}
			   
			stmt.close();  // Close the statement object
			conn.close();   // Close the connection
			}//close try
			   
				catch(SQLException g)
				{
					System.err.print("SQLException: ");
					System.err.println(g.getMessage());
				}//close catch

			}//close displayButton
			
			
		
		////////////////////////////////////////////////////////////////////////////////////////
			
			
		
		if(obj == RandButton) {
			
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

			//Create statement object for running SQL clauses
			Statement stmt = conn.createStatement ();  

			//Query query_entry.getText(), education_entry.getText(), year_entry.getText(), sex_entry.getText() 
			ResultSet rset = stmt.executeQuery ("SELECT * from EducationData");
			
			DefaultPieDataset dataset = new DefaultPieDataset();
		      
		      while(rset.next()) {
		         dataset.setValue ( 
		         rset.getString("d_Sex") ,
		         Integer.parseInt(rset.getString("d_Value")));
		      }
		      
		      JFreeChart chart = ChartFactory.createPieChart(
		    	         "Sex VS Value",   // chart title           
		    	         dataset,          // data           
		    	         true,             // include legend          
		    	         true,           
		    	         false );
		      
		      int width = 560;    /* Width of the image */
		      int height = 370;   /* Height of the image */ 
		      File pieChart = new File( "Pie_Chart.jpeg" );
		      try {
				ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
		      } 
		      catch (IOException e1) 
		      {
				e1.printStackTrace();
		      }

			}
			
				catch(SQLException g)
				{
					System.err.print("SQLException: ");
					System.err.println(g.getMessage());
				}//close catch
			}
		
		}

}//end class


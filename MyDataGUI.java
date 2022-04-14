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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.swing.JButton;
//import javax.swing.JComboBox;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


import java.util.Random;


public class MyDataGUI extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	
	//  attributes
	JTextField year_entry, education_entry, sex_entry, query_entry, SQL_entry;
	JButton displayButton, SQLButton, RandButton;
	JPanel MainPanel, displayPanel, SQLPanel, RandPanel;
	JLabel yearLabel, educationLabel, sexLabel, queryLabel, SQLLabel;
	JFrame frame;
	JTable table;
	//JComboBox yearList;
	
	
	boolean education_flag = false;
	boolean sex_flag = false;
	
	int year;
	int index = 0;
	
	String sex, ed, quoted_sex_entry, quoted_education_entry;
	
	//  font styles
	Font  f4  = new Font(Font.SANS_SERIF, Font.BOLD|Font.ITALIC, 15);
	Font  f1  = new Font(Font.SERIF, Font.PLAIN,  15);
	
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
		setSize(600, 150);
		setLayout(new FlowLayout());
		
		//	create panel
		MainPanel = new JPanel();
		MainPanel.setLayout(new FlowLayout());
		add(MainPanel);
		
		//	year label
		yearLabel = new JLabel("Year:");
		yearLabel.setFont(f1);
		MainPanel.add(yearLabel);
		
		//	year textField
		year_entry = new JTextField("Enter year");
		year_entry.setFont(f1);
		year_entry.setToolTipText("Select a year between 2009 and 2021");
		MainPanel.add(year_entry);

		/*drop down menu for yearEntry
		JComboBox yearList = new JComboBox(years);
		yearList.setSelectedIndex(12);
		yearList.addActionListener(this);
		MainPanel.add(yearList);
		*/
		
		
		//	sex label
		sexLabel = new JLabel("Sex:");
		sexLabel.setFont(f1);
		MainPanel.add(sexLabel);
		
		//	sex textField
		sex_entry = new JTextField("Enter sex");
		sex_entry.setFont(f1);
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
		educationLabel.setFont(f1);
		MainPanel.add(educationLabel);
		
		//	education textField
		education_entry = new JTextField("Enter education");
		education_entry.setFont(f1);
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
		displayButton.setBackground(Color.orange);
		displayButton.setFont(f4);
		MainPanel.add(displayButton);
		displayButton.setToolTipText("Display data for specified terms");
		displayButton.addActionListener(this);
		
		
		//	user input SQL code 
		SQLPanel = new JPanel();
		SQLPanel.setLayout(new FlowLayout());
		add(SQLPanel);
		SQLPanel.isVisible();
		
		SQLLabel = new JLabel("SQL:");
		SQLLabel.setFont(f1);
		SQLPanel.add(SQLLabel);
		
		SQL_entry = new JTextField("select * from EducationData where");
		SQL_entry.setFont(f1);
		SQL_entry.setToolTipText("Column names include d_Year, d_Sex, d_EducationLevel and d_Value");
		SQLPanel.add(SQL_entry);
		
		SQLButton = new JButton("Submit");
		SQLButton.setBackground(Color.orange);
		SQLButton.setFont(f4);
		SQLPanel.add(SQLButton);
		SQLButton.setToolTipText("Submit your SQL query");
		SQLButton.addActionListener(this);
		
		// random button
		RandButton = new JButton("Random Diagram");
		RandButton.setBackground(Color.orange);
		RandButton.setFont(f4);
		SQLPanel.add(RandButton);
		RandButton.setToolTipText("Click here to get a random diagram about the data!");
		RandButton.addActionListener(this);
		
                
		}//  close MyDataGUI constructor
				
		public void actionPerformed(ActionEvent e) 
		{
			Object obj = e.getSource();
			
			//  button for user input SQL
			if(obj == SQLButton) {
				
				try
				{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   //  load the driver
				}
				   
					//  Catch driver loading errors
					catch (ClassNotFoundException f)
					{
						System.out.println ("Could not load the driver.");
					}

				try {
					
				//  URL for connection to the database
				String url = "jdbc:sqlserver://localhost;Database=JavaAssignment;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true";

				//  Register the driver
				DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

				//  Connecting to the database
				Connection conn = DriverManager.getConnection(url);
				System.out.println ("\n\n- - - - - - - - - - - -");
				System.out.println ("Connection successful");
				System.out.println ("- - - - - - - - - - - -\n\n");
				System.out.println("Your Query: " +SQL_entry.getText()+ "\n");

				//  Create statement object for running SQL clauses
				Statement stmt = conn.createStatement ();  

				//  Query query_entry.getText(), education_entry.getText(), year_entry.getText(), sex_entry.getText() 
				ResultSet rset = stmt.executeQuery (SQL_entry.getText());
				
				//  create table for displaying results in the GUI
				String columns[] = { "Year", "Sex", "Education Level", "Unemployment Rate" };
			    String data[][] = new String[360][4];
			    
			    //  loop through the results and populate the JTable
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
			    }//  close while
			    
			    //  create JTable for the data
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
				  
				stmt.close();  //  Close the statement object
				conn.close();   //  Close the connection
				}//  close try
				   
				catch(SQLException g)
				{
					System.err.print("SQLException: ");
					System.err.println(g.getMessage());
				}//  close catch
			}//  end SQLButton
			
			
		 
			//////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			//  button for set queries
			if(obj == displayButton) {
				
			//error checking the sex entry
			String str1 = "Male";
			String str2 = "Female";
			String str3 = "Both";
			String str4 = "male";
			String str5 = "female";
			String str6 = "both";
				
			if(str1.equals(sex_entry.getText()) || str2.equals(sex_entry.getText()) || str3.equals(sex_entry.getText()) || str4.equals(sex_entry.getText()) || str5.equals(sex_entry.getText()) || str6.equals(sex_entry.getText())) {
				
			//  add single quotes to strings so they can be executed in SQL
			quoted_sex_entry = "\'" + sex_entry.getText() + "\'";
			quoted_education_entry = "\'" + education_entry.getText() + "\'";
			
			//  Load Driver
			try
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // load the driver
			}
			   
			//  Catch driver loading errors
			catch (ClassNotFoundException g)
			{
				System.out.println ("Could not load the driver.");
			}

			try {
				
			//  URL for connection to the database
			String url = "jdbc:sqlserver://localhost;Database=JavaAssignment;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true";

			//  Register the driver
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());   

			//  Connecting to the database
			Connection conn = DriverManager.getConnection(url);
			System.out.println ("\n\n- - - - - - - - - - - -");
			System.out.println ("Connection successful");
			System.out.println ("- - - - - - - - - - - -\n");

			//  Create statement object for running SQL clauses
			Statement stmt = conn.createStatement ();  

			//  create result set for SQL results
			ResultSet rset = stmt.executeQuery ("select * from EducationData where d_Year = " +year_entry.getText()+ 
					"and d_Sex = " +quoted_sex_entry+ "and d_EducationLevel = " +quoted_education_entry);
   
			//  Display the data
			while (rset.next()) 
			{
				JOptionPane.showMessageDialog(frame, education_entry.getText() + ", " + sex_entry.getText()+ ", " 
						+ year_entry.getText()+ ": " +rset.getString(4)+ "% unemployment rate");
			}
			   
			stmt.close();  //  Close the statement object
			conn.close();   //  Close the connection
			}//  close try
			   
				catch(SQLException g)
				{
					System.err.print("SQLException: ");
					System.err.println(g.getMessage());
				}//  close catch
			
			}// close if
			
			else {
				JOptionPane.showMessageDialog(frame, "Invalid entry, try again.");
			}

			}//  close displayButton
			
			
		
		////////////////////////////////////////////////////////////////////////////////////////
			
			
		
		if(obj == RandButton) {
			
			//  generate a random number after each click 
			Random rand = new Random();
			int number = rand.nextInt(3);
			number += 1;
		
			
			try
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   //  load the driver
			}
			   
				//  Catch driver loading errors
				catch (ClassNotFoundException f)
				{
					System.out.println ("Could not load the driver.");
				}

			try {
				
			//  URL for connection to the database
			String url = "jdbc:sqlserver://localhost;Database=JavaAssignment;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true";

			//  register the driver
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

			//  connecting to the database
			Connection conn = DriverManager.getConnection(url);
			System.out.println ("\n\n- - - - - - - - - - - -");
			System.out.println ("Connection successful");
			System.out.println ("- - - - - - - - - - - -\n\n");

			//  Create statement object for running SQL clauses
			Statement stmt = conn.createStatement ();  
			
			//  create ResultSet from Database
			ResultSet rset = stmt.executeQuery ("SELECT * from EducationData");
			
			if (number == 1) {
			
			//  create dataset 
			DefaultPieDataset dataset = new DefaultPieDataset();
		      
			//  load data into dataset
		      while(rset.next()) {
		         dataset.setValue ( 
		         rset.getString("d_EducationLevel") ,
		         Integer.parseInt(rset.getString("d_Value")));
		      }
		      
		      //  populate diagram
		      JFreeChart chart = ChartFactory.createPieChart(
		    	         "Education Level VS Unemployment Rate",              
		    	         dataset,                     
		    	         true,                      
		    	         true,           
		    	         false );
		      
		      int width = 1000;     
		      int height = 700;     
		      File pieChart = new File( "Pie_Chart.jpeg" );
		      
		      //  save pie chart to PC
		      try {
				ChartUtilities.saveChartAsPNG(pieChart, chart, width, height);
				JOptionPane.showMessageDialog(frame, "Saved pie chart to PC!");
		      }//  close try 
		      
		      catch (IOException e1) 
		      {
				e1.printStackTrace();
		      }//  close catch
		       
			}//  close if number == 1
		      
			
			if (number == 2) {
			
			//  create dataset
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			//  load values into dataset
			while(rset.next()) {
		         dataset.setValue ( 
		         Integer.parseInt(rset.getString("d_Value")), 
		         rset.getString("d_EducationLevel") , rset.getString("d_Sex")
		         );
		      }
		      
			//  create bar chart
			JFreeChart barChart = ChartFactory.createBarChart(
			         getTitle(),
			         "Gender",
			         "Unemployment Rate",
			         dataset,
			         PlotOrientation.VERTICAL,
			         true,true,false);
			         
				int width = 1000;    //  Width of the image 
				int height = 700;   //  Height of the image  
				File BarChart = new File( "Bar_Chart.jpeg" );
				
			  //  save bar chart to PC
		      try {
				ChartUtilities.saveChartAsPNG(BarChart, barChart, width, height);
				JOptionPane.showMessageDialog(frame, "Saved bar chart to PC!");
		      }//  close try 
		      
		      catch (IOException e1) 
		      {
				e1.printStackTrace();
		      }//  close catch
		      
			}//  close if number == 2
			
			//  Line chart diagram
			if (number == 3) {
				
				//  create dataset
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				
				//  load values into dataset
				while(rset.next()) {
			         dataset.setValue ( 
			         Integer.parseInt(rset.getString("d_Value")), 
			         rset.getString("d_Sex") , rset.getString("d_Year")
			         );
			      }//  close while
				
				//  create line graph
				JFreeChart lineChart = ChartFactory.createLineChart(
				         getTitle(),
				         "Years","Unemployment Rate",
				         dataset,
				         PlotOrientation.VERTICAL,
				         true,true,false);
				         
					int width = 1000;    //  width of the image 
					int height = 700;    //  height of the image  
					File LineChart = new File("Line_Chart.jpeg");
					
					//  save complete diagram to PC
			      try {
					ChartUtilities.saveChartAsPNG(LineChart, lineChart, width, height);
					JOptionPane.showMessageDialog(frame, "Saved line chart to PC!");
			      }//  close try
			      
			      		catch (IOException e1) 
			      		{
			      			e1.printStackTrace();
			      		}//  close catch
				}//  close if number == 3
			      
			}//  close try RandomButton
			
				catch(SQLException g)
				{
					System.err.print("SQLException: ");
					System.err.println(g.getMessage());
				}//  close catch
			
			}//  end RandomButton
		
		}//  end actionPerformed

}//  end class

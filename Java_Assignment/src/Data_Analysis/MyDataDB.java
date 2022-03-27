package Data_Analysis;

import java.sql.*;
import java.io.*;
import java.lang.*;
import java.sql.DriverManager.*; //make sure this is in classpath

//susan's code, just trying to establish a connection on laptop
public class MyDataDB {

 // main method to run (normally in a separate class, as per MVC)
 public static void main (String args [])
throws SQLException, IOException
 {

  //Load Driver
   try
   {
	   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // load the driver
	   System.out.println("Driver loaded.");
   }
   
   //Catch driver loading errors
   catch (ClassNotFoundException e)
   {
	   System.out.println ("Could not load the driver.");
   }

   //URL for connection to the database
   String url = "jdbc:sqlserver://localhost;Database=JavaAssignment;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true";

   //Register the driver
   DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());   
   System.out.println ("Before connection.");

   // Connecting to the database
   Connection conn = DriverManager.getConnection(url);
   System.out.println ("After connection");
   System.out.println ("- - - - - - - - - - - -\n");

   //Create statement object for running SQL clauses
   Statement stmt = conn.createStatement ();  

   //Query
   ResultSet rset = stmt.executeQuery ("select * from EducationData\r\n"
   		+ "where d_Year = 2017 and d_EducationLevel = 'Higher Certificate';");
   
   while (rset.next()) 
   {
	   System.out.println(rset.getString(1) + " - " + rset.getString(2)  + " - " + rset.getString(3)  + " - " + rset.getString(4));
   }
   
   stmt.close();  // Close the statement object
   conn.close();   // Close the connection
 }


  //readEntry function - For reading user input
  static String readEntry(String prompt)
  {
	  try {
		  		StringBuffer buffer = new StringBuffer();
		  		System.out.print(prompt);
		  		System.out.flush();
		  		int c = System.in.read();
		  		while (c != '\n' && c != -1) 
		  		{
		  			buffer.append((char)c);
		  			c = System.in.read();
		  		}
		  			return buffer.toString().trim();
     	  }	  
   	  catch (IOException e) 
   		{
   		  return "";
   		}
  }
}
 
 

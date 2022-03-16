package Data_Analysis;

import java.sql.*;
import java.io.*;
//import java.lang.*;
//import com.microsoft.sqlserver.jdbc.SQLServerDriver; 


public class MyDataDB 
{

  // main method to run (normally in a separate class, as per MVC)	
  public static void main (String args [])
	throws SQLException, IOException 
  {

 // load the dB driver for whatever database you plan to use
	
    try 
    {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // load the driver
    	System.out.println("Driver loaded.");
    } 
    
    catch (ClassNotFoundException e) 
    {
	    System.out.println ("Could not load the driver.");
	    
	}

    // dB name and credentials. These will be specific to your server etc
	// for your assignment, you would have imported your dataset into a dB. 

    String url = "jdbc:sqlserver://BEN-PC;databaseName=JavaAssignment;integratedSecurity=true";
    System.out.println(url);

    /* register the driver
    DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());   // register the driver 
	System.out.println ("before connection");
	*/

    // Connect to the database, passing the credentials
    Connection conn = DriverManager.getConnection(url);
	System.out.println ("after connection");

    // Then, get a statement object (which will be used to execute queries, I/U/D etc)
    Statement stmt = conn.createStatement ();  // now have a mechanism to run SQL statements

    // The example here is of executing a query (which returns a result set)
    ResultSet rset = stmt.executeQuery
	("select staff_no, staff_name from builder2.staff");
    while (rset.next()) 
    {
	System.out.println(rset.getString(1) + " " + rset.getString(2));
    }//end while
    
    stmt.close();  // close the statement object
    conn.close();   // close the connection
  }//end main


   //readEntry function -- to read input data
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
         }// end try  
      
      	 catch (IOException e) 
      	 {
      		 return " ";
      	 }//end catch
  }
}

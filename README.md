# JavaAssignment
This java assignment demonstrates my understanding and use of various OOP concepts.

Core Functionality:  Build a tool that shows
interesting facts from a dataset taken from 
data.gov.ie. The dataset that I chose was 
"Unemployment Rate of Persons 25-64 years".
It contains data from 2009 to 2021 about
gender, education level and the rate of 
unemployment. Users can query the database 
by wrtiting their own SQL clauses, and view 
the results in a table, or they can input 3 values,
Year, Sex and Education Level and be shown the result.

Optional Functionality:  I have implemented a button
which will save a random diagram to the user's
PC. The diagram is one of: Bar chart, pie chart
or line chart for a visual representation of the SQL
queries. As I mentioned above, users can write their own 
queries and dont need to rely on hard coded clauses. 

If I had more time for this project, I would add extensive error checking 
as we are dealing with a database, it is very vunerable to SQL injection.
I would also add some flexibilty, for example your queries dont need to 
be exact, possibly using wildcards. I would also add
the JComboBox functionality as it is much easier for the user to
input values that way. I would definitely try and move everything
to different classes but I struggled to do that while using 
the actionListener.

As mentioned above, I struggled to break down my program into 
classes while using the actionListener method. The classes that
I have are: MyDataGUI_control, a control class that creates a new
instance of my GUI, and MyDataGUI, extends JFrame implements
ActionListener and holds all the GUI configurations and code 
processes like handling button presses and error checking.


Video demonstration: (url)

Inspiration taken: 
https://stackoverflow.com/questions/32277771/how-to-check-if-an-array-contains-a-particular-word-in-a-string-and-get-it
https://stackhowto.com/how-to-populate-jtable-from-database/
https://www.tutorialspoint.com/jfreechart/jfreechart_database_interface.htm
http://www.java2s.com/Tutorials/Java/Java_Swing/1520__Java_Swing_Font.htm

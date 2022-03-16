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
	JButton button1, button2, button3;
	JPanel panel1, panel2, panel3, panel4;
	JLabel label1, label2;
	JFrame frame;
	int year;
	String options = "National";
		
	//methods
	public MyDataGUI(String title)
	{
		super(title);
		setVisible(true);
		setSize(700, 500);
		setLayout(new FlowLayout());
		
		//JFrame frame = new JFrame();
		
		//generate random fact
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		add(panel1);
		
		button1 = new JButton("Display a random fact!");
		panel1.add(button1);
		button1.setToolTipText("This button will a random fact about this dataset");
		button1.addActionListener(this);
		
		//specify year
		panel2 = new JPanel();
		add(panel2);
		panel2.setLayout(new FlowLayout());
		
		label1 = new JLabel("Year:");
		panel2.add(label1);
		
		year_entry = new JTextField(" 1976 - 2015 ");
		panel2.add(year_entry);
		
		//specify area
		panel3 = new JPanel();
		add(panel3);
		panel3.setLayout(new FlowLayout());
		
		label2 = new JLabel("Area:");
		panel3.add(label2);
		
		area_entry = new JTextField(" National, Dublin, Cork, Galway, Limerick, Other ");
		panel3.add(area_entry);
		
		//confirm entries
		panel4 = new JPanel();
		panel4.setLayout(new FlowLayout());
		add(panel4);
		
		button2 = new JButton("Display data");
		panel4.add(button2);
		button2.setToolTipText("Display data for specified terms");
		button2.addActionListener(this);
		
		}//close MyDataGUI constructor

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == button1)
			{
				
				
			}//end if 
			
			//error check
			if(e.getSource() == button2)
			{
				year = Integer.parseInt(year_entry.getText()); //year
				boolean areEqual = area_entry.equals(options); //area
				
				if(areEqual)
				{
					JOptionPane.showMessageDialog(frame, "Yes");
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "No");
				}
				
				if(year < 1976 || year > 2015)
				{
					JOptionPane.showMessageDialog(frame, "Select a year between 1976 and 2015!");
				}//end if 
				
			}//end if button2
			
		}//end if action e
		
	}//end class


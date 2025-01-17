package Trips;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Employees.EmployeeDatabase;
import Trains.TrainsDatabase;
import railwayManagementSystem.Database;
import railwayManagementSystem.GUI;
import railwayManagementSystem.Main;

public class AddTrip {
	
	public AddTrip(JFrame parent,Database database) throws SQLException
	{
		
		String [] HH = new String[25];
		HH[0] = "HH";
		for(int i=0;i<24;i++)
		{
			HH[i+1] = String.format("%02d",i);
		}
		String[] mm = new String[61];
		mm[0] = "mm";
		for(int i=0;i<60;i++)
		{
			mm[i+1] = String.format("%02d",i);
		}
		
		String[] dd =new String[32];
		dd[0]= "DD";
		for(int i=1;i<32;i++)
		{
			dd[i] = String.format("%02d",i);
		}
		
		String[] MM =new String[13];
		dd[0]= "MM";
		for(int i=1;i<13;i++)
		{
			MM[i] = String.format("%02d",i);
		}
		
		String[] yyyy =new String[51];
		yyyy[0]= "YYYY";
		for(int i=2023;i<2073;i++)
		{
			yyyy[i-2022] = String.format("%02d",i);
		}
		JFrame frame = new JFrame("Add Trip");
		frame.setSize(750,675);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(parent);
		frame.getContentPane().setBackground(GUI.background);
		
		JPanel panel= new JPanel(new GridLayout(10,2,20,20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50,50,30,50));
		
		panel.add(GUI.JLabel("ID:"));
		JLabel id = GUI.JLabel(String.valueOf(TripsDatabase.getNextID(database)));
		panel.add(id);
		
		panel.add(GUI.JLabel("Start:"));
		JTextField start = GUI.JTextField();
		panel.add(start);
		
		panel.add(GUI.JLabel("Destination:"));
		JTextField destination = GUI.JTextField();
		panel.add(destination);
		
		panel.add(GUI.JLabel("Department Time: "));
		JPanel dept = new JPanel(new GridLayout(1,2,10,10));
		dept.setBackground(null);
		JComboBox<String> deptHH = GUI.JComboBox(HH);
		JComboBox<String> deptmm = GUI.JComboBox(mm);
		dept.add(deptHH);
		dept.add(deptmm);
		panel.add(dept);
		
		panel.add(GUI.JLabel("Arrival Time"));
		JPanel arr = new JPanel(new GridLayout(1,2,10,10));
		arr.setBackground(null);
		JComboBox<String> arrHH = GUI.JComboBox(HH);
		JComboBox<String> arrmm = GUI.JComboBox(mm);
		arr.add(arrHH);
		arr.add(arrmm);
		panel.add(arr);
		
		
		
		panel.add(GUI.JLabel("Date: "));
		JPanel date = new JPanel(new GridLayout(1,3,10,10));
		date.setBackground(null);
		JComboBox<String> datedd = GUI.JComboBox(dd);
		JComboBox<String> dateMM = GUI.JComboBox(MM);
		JComboBox<String> dateyyyy = GUI.JComboBox(yyyy);
		date.add(datedd);
		date.add(dateMM);
		date.add(dateyyyy);
		panel.add(date);
		
		panel.add(GUI.JLabel("Price:"));
		JTextField price = GUI.JTextField();
		panel.add(price);
		
		panel.add(GUI.JLabel("Driver:"));
		JComboBox<String> driver = GUI.JComboBox(EmployeeDatabase.getEmployeesName(database));
		panel.add(driver);
		
		panel.add(GUI.JLabel("Train:"));
		JComboBox<String> train = GUI.JComboBox(TrainsDatabase.getTrainsName(database));
		panel.add(train);
		
		JButton cancel = GUI.JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
			
		});
		panel.add(cancel);
		
		JButton submit = GUI.JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e1) {
				if(checkChoice(deptHH,frame)) return;
				if(checkChoice(deptmm,frame)) return;
				if(checkChoice(arrHH,frame)) return;
				if(checkChoice(arrmm,frame)) return;
				if(checkChoice(datedd,frame)) return;
				if(checkChoice(dateMM,frame)) return;
				if(checkChoice(dateyyyy,frame)) return;
				try {
					Double.parseDouble(price.getText());
				}catch(Exception e2){
					JOptionPane.showMessageDialog(frame, "Price must be a double");
					return ;
				}
				
				Trip trip = new Trip();
				trip.setId(Integer.parseInt(id.getText()));
				trip.setStartStation(start.getText());
				trip.setDestination(destination.getText());
				String deptTime = deptHH.getSelectedItem().toString()+":"+deptmm.getSelectedItem().toString();
				String arrTime = arrHH.getSelectedItem().toString()+":"+arrmm.getSelectedItem().toString();
				trip.setDepartureTime(deptTime);
				trip.setArrivalTime(arrTime);
				
				String d = dateyyyy.getSelectedItem().toString()+"-"+dateMM.getSelectedItem().toString()+""
						+ "-"+datedd.getSelectedItem().toString();
				trip.setDate(d);
				trip.setBookedSeats(0);
				
				trip.setPrice(Double.parseDouble(price.getText()));
				try {
					trip.setDriver(EmployeeDatabase.getEmployeeByName(driver.getSelectedItem().toString(), database));
					trip.setTrain(TrainsDatabase.getTrainByDescription(train.getSelectedItem().toString(), database));
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(frame, e.getMessage());
				}
				
				trip.setPassenger(new ArrayList<>());
				try {
					TripsDatabase.AddTrip(trip, database);
					JOptionPane.showMessageDialog(frame, "Trip Added Succesfully");
					Main.refreshTable(TripsDatabase.getAllTrips(database));
					frame.dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(frame, "Operation Error");
					frame.dispose();
				}
				
			}
			
		});
		panel.add(submit);
		
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.setVisible(true);
		
		
		
	}
	private boolean checkChoice(JComboBox<String> comboBox,JFrame frame)
	{
		boolean c = false;
		if(comboBox.getSelectedIndex()==0)
		{
			JOptionPane.showMessageDialog(frame, "Data Error: "+comboBox.getSelectedItem().toString());
			c=true;
		}
		return c;
	}

}

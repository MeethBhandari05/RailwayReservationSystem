package railwayManagementSystem;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import Employees.AddEmployee;
import Employees.EditEmployee;
import Passenger.AddPassenger;
import Passenger.EditPassenger;
import Trains.AddTrain;
import Trains.EditTrain;
import Trips.AddTrip;
import Trips.EditTrip;
import Trips.ShowTripPassengers;

public class ModifyList {
	public ModifyList(JFrame oldFrame,Database database)
	{
		JFrame frame = new JFrame("Railway Management System");
		frame.setSize(400,600);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(oldFrame);
		frame.getContentPane().setBackground(Color.decode("#EBFFD8"));
		
		JPanel panel=new JPanel(new GridLayout(9,18,10,10));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		
		JButton addTrain = JButton ("Add Train");
		addTrain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddTrain(frame,database);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		panel.add(addTrain);
		
		JButton editTrain = JButton ("Edit Train");
		editTrain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new EditTrain(frame,database);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		panel.add(editTrain);
		
		JButton addEmployee = JButton ("Add Employee");
		addEmployee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddEmployee(frame,database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
				
			}
		});
		panel.add(addEmployee);
		
		JButton editEmployee = JButton ("Edit Employee");
		editEmployee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new EditEmployee(frame,database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});
		panel.add(editEmployee);
		
		JButton addPassenger = JButton ("Add Passenger");
		addPassenger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddPassenger(frame,database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});
		panel.add(addPassenger);
		
		JButton editPassenger = JButton ("Edit Passenger");
		editPassenger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new EditPassenger(frame,database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});
		panel.add(editPassenger);
		
		JButton addTrip = JButton ("Add Trip");
		addTrip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddTrip(frame,database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});
		panel.add(addTrip);
		
		JButton editTrip = JButton ("Edit Trip");
		editTrip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new EditTrip(frame,database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});
		panel.add(editTrip);
		
		JButton showPassenger = JButton("Show Trip Passenger");
		showPassenger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ShowTripPassengers(frame,database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
			
		});
		panel.add(showPassenger);
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.setVisible(true);
	}
	private JButton JButton(String text)
	{
		JButton btn= new JButton(text);
		btn.setBackground(Color.decode("#45C4B0"));
		btn.setForeground(Color.white);
		btn.setFont(new Font(null,Font.BOLD,22));
		return btn;
	}
}

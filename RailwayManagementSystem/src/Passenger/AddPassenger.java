package Passenger;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import railwayManagementSystem.Database;
import railwayManagementSystem.GUI;

public class AddPassenger {
	
	public AddPassenger(JFrame parent,Database database) throws SQLException{
		
		JFrame frame = new JFrame("Add Passeneger");
		frame.setSize(750,500);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(parent);
		frame.getContentPane().setBackground(GUI.background);
		
		JPanel panel= new JPanel(new GridLayout(5,2,20,20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50,50,30,50));
		
		panel.add(GUI.JLabel("ID:"));
		JLabel id = GUI.JLabel(String.valueOf(PassengerDatabase.getNextID(database)));
		panel.add(id);
		
		panel.add(GUI.JLabel("Name:"));
		JTextField name = GUI.JTextField();
		panel.add(name);
		
		panel.add(GUI.JLabel("Tel:"));
		JTextField tel = GUI.JTextField();
		panel.add(tel);
		
		panel.add(GUI.JLabel("Email:"));
		JTextField email = GUI.JTextField();
		panel.add(email);
		
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
				Passenger p = new Passenger();
				p.setID(Integer.parseInt(id.getText()));
				p.setName(name.getText());
				p.setEmail(email.getText());
				p.setTel(tel.getText());
				try {
					PassengerDatabase.AddPassenger(p,database);
					JOptionPane.showMessageDialog(frame, "Employee added successfully");
					frame.dispose();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(frame, "Operation Failed");
					frame.dispose();
				}
			}
			
		});
		panel.add(submit);
		
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.setVisible(true);
	}

}

package Trains;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

import railwayManagementSystem.Database;
import railwayManagementSystem.GUI;

public class AddTrain {
	
//	private Database database;
	
	public AddTrain(JFrame oldframe, Database database) throws SQLException
	{
		
		JFrame frame=new JFrame("Add Train");
		frame.setSize(750,400);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(oldframe);
		frame.getContentPane().setBackground(Color.decode("#EBFFD8"));
		
		JPanel panel = new JPanel(new GridLayout(4,2,20,20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));
		
		panel.add(GUI.JButton("ID: "));
		
		JLabel id= GUI.JLabel(String.valueOf(TrainsDatabase.getNextID(database)));
		panel.add(id);
		
		panel.add(GUI.JButton("Capacity: "));
		
		JTextField capacity = GUI.JTextField();
		panel.add(capacity);
		
		
		panel.add(GUI.JButton("Description: "));
		JTextField description = GUI.JTextField();
		panel.add(description);
		
		JButton cancel = GUI.JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
			
		});
		panel.add(cancel);
		
		JButton submit= GUI.JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Train t =new Train();
				t.setId(Integer.parseInt(id.getText()));
				t.setCapacity(Integer.parseInt(capacity.getText()));
				t.setDescription(description.getText());
					
				try {
						TrainsDatabase.AddTrain(t,database);
						JOptionPane.showConfirmDialog(frame, "Train Added Successfully");
						frame.dispose();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, "Operation failed");
						e1.printStackTrace();
					}
				} 
			
		});
		panel.add(submit);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	
}

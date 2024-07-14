package railwayManagementSystem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import Trips.BookTrip;
import Trips.Trip;
import Trips.TripsDatabase;

import javax.swing.*;

public class Main {

	private static JFrame frame;
	private static JPanel table;
	private static GridLayout gridLayout;
	private static Database database;
	
	public static void main(String[] args) throws SQLException {
		
		database = new Database();
		
		JFrame frame=new JFrame("Railway Management System");
		frame.setSize(1050,650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		
		frame.getContentPane().setBackground(Color.decode("#EBFFD8"));
		frame.setLocationRelativeTo(null);
		
		JPanel panel =new JPanel(new BorderLayout(20,20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50,40,30,40));
		
		JLabel title =new JLabel("Welcome to Railway Management System");
		title.setForeground(Color.decode("#012030"));
		title.setFont(new Font(null,Font.BOLD,35));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title,BorderLayout.NORTH);
		
		gridLayout=new GridLayout(6,1);
		table=new JPanel(gridLayout);
		table.setBackground(Color.decode("#EBFFD8"));
		
		ArrayList<Trip> trips=TripsDatabase.getAllTrips(database);
		refreshTable(trips);
		JScrollPane sp = new JScrollPane(table);
		panel.add(sp,BorderLayout.CENTER);
		
		JButton modify = new JButton("Modify");
		
		modify.setBackground(Color.decode("#45C480"));
		modify.setForeground(Color.white);
		modify.setFont(new Font(null,Font.BOLD,22));
		modify.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ModifyList(frame,database);
				
			}
		});
		
		panel.add(modify,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel,BorderLayout.CENTER);
		frame.setVisible(true);
		
	}

	
	public static void refreshTable(ArrayList<Trip> trips)
	{
		table.removeAll();
		table.repaint();
		table.revalidate();
		int rows =trips.size()+1;
		if(rows<6) rows=6;
		gridLayout.setRows(rows);
		table.add(row(0,null));
		for(int i=0;i<trips.size();i++)
		{
			JPanel trip =row(i+1,trips.get(i));
			table.add(trip);
		}
	}
	private static JPanel row(int index,Trip trip)
	{
		JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
		
		if(index%2==0)
		{
			row.setBackground(Color.decode("#e5e5e5"));
		}
		else
		{
			row.setBackground(Color.decode("#EEEEEE"));
		}
		String trainS,startS,destS,dateS,deptS,arrS,priceS,StatusS;
		if(trip!=null)
		{
			trainS=trip.getTrain().getDescription();
			startS=trip.getStartStation();
			destS=trip.getDestination();
			dateS=trip.getDate();
			deptS=trip.getDepartureTime();
			arrS=trip.getArrivalTime();
			priceS=trip.getPrice()+"₹";
			StatusS="Booked";
			if(trip.getTrain().getCapacity()>trip.getBookedSeats())
			{
				StatusS="Available";
			}
			row.setCursor(new Cursor(Cursor.HAND_CURSOR));
			row.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						new BookTrip(frame, database, trip);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, e1.getMessage());
					}
				}
				

				public void mousePressed(MouseEvent e) {}

				public void mouseReleased(MouseEvent e) {}

				public void mouseEntered(MouseEvent e) {}

				public void mouseExited(MouseEvent e) {}
					
					
				
				});
		}
		else
		{
			trainS="Train";
			startS="From";
			destS="To";
			dateS="Date";
			deptS="Dept";
			arrS="Arr";
			priceS="Price";
			StatusS="Status";
		}
		JLabel train =JLabel(trainS,100);
		row.add(train);
		JLabel start =JLabel(startS,100);
		row.add(start);
		JLabel dest =JLabel(destS,100);
		row.add(dest);
		JLabel date =JLabel(dateS,150);
		row.add(date);
		JLabel deptTime =JLabel(deptS,65);
		row.add(deptTime);
		JLabel arrTime =JLabel(arrS,65);
		row.add(arrTime);
		JLabel price =JLabel(priceS,69);
		row.add(price);
		JLabel statusS =JLabel(StatusS,100);
		row.add(statusS);
		return row;
		
	}
	
	private static JLabel JLabel(String text,int width)
	{
		JLabel label=new JLabel(text);
		label.setPreferredSize(new Dimension(width,20));
		label.setFont(new Font(null,Font.PLAIN,20));
		label.setForeground(Color.decode("#13678A"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;		
	}
}

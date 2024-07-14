package Trips;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Employees.EmployeeDatabase;
import Trains.TrainsDatabase;
import railwayManagementSystem.Database;

public class TripsDatabase {
	
	public static void AddTrip(Trip t, Database database) throws SQLException
	{
		String insert = "INSERT INTO `trips`(`ID`, `Start`, `Destination`, "
				+ "`DepartureTime`, `ArrTime`, `Date`, `BookedSeats`, `Price`, `Driver`, `Train`) "
				+ "VALUES ('"+t.getId()+"','"+t.getStartStation()+"','"+t.getDestination()+"','"+t.getDepartureTime()+"',"
				+ "'"+t.getArrivalTime()+"','"+t.getDate()+"','"+t.getBookedSeats()+"','"+t.getPrice()+"',"
						+ "'"+t.getDriver().getID()+"','"+t.getTrain().getId()+"')";
		database.getStatement().execute(insert);
		
		String create="CREATE TABLE `Trip"+t.getId()+"Passenger` (Passenger int NOT NULL UNIQUE, Tickets int NOT NULL UNIQUE);";
		database.getStatement().execute(create);
	}
	
	public static int getNextID(Database database) throws SQLException
	{
		int id = 0;
		ArrayList<Trip> trips = getAllTrips(database);
		int size = trips.size();
		if(size!=0) {
			id = trips.get(size-1).getId()+1;
		}
		return id;
	}
	
	public static ArrayList<Trip> getAllTrips(Database database) throws SQLException
	{
		ArrayList<Trip> trips = new ArrayList<>();
		ArrayList<Integer> drivers = new ArrayList<>();
		ArrayList<Integer> trains = new ArrayList<>();
		String select = "SELECT * FROM `trips`;";
		ResultSet rs = database.getStatement().executeQuery(select);
		while(rs.next())
		{
			Trip t =new Trip();
			t.setId(rs.getInt("ID"));
			t.setStartStation(rs.getString("Start"));
			t.setDestination(rs.getString("Destination"));
			t.setDepartureTime(rs.getString("DepartureTime"));
			t.setArrivalTime(rs.getString("ArrTime"));
			t.setDate(rs.getString("Date"));
			t.setBookedSeats(rs.getInt("BookedSeats"));
			t.setPrice(rs.getDouble("Price"));
			drivers.add(rs.getInt("Driver"));
			trains.add(rs.getInt("Train"));
			trips.add(t);
		}
		
		for(int i=0;i<trips.size();i++)
		{
			trips.get(i).setDriver(EmployeeDatabase.getEmployee(
					String.valueOf(drivers.get(i)),database));
			trips.get(i).setTrain(TrainsDatabase.getTrain(
					String.valueOf(trains.get(i)),database));
		}
		return trips;
		
	}
	
	public static String[] getIDs(Database database) throws SQLException
	{
		ArrayList<Trip> trips = getAllTrips(database);
		String[] array = new String[trips.size()];
		for(int i=0;i<trips.size();i++)
		{
			array[i]= String.valueOf(trips.get(i).getId());
		}
		return array;
	}

	public static Trip getTrip(String id, Database database) throws SQLException
	{
		String select = "SELECT `ID`, `Start`, `Destination`, `DepartureTime`, `ArrTime`, `Date`, "
				+ "`BookedSeats`, `Price`, `Driver`, `Train` FROM `trips` WHERE `ID` = "+id+";";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		Trip t =new Trip();
		t.setId(rs.getInt("ID"));
		t.setStartStation(rs.getString("Start"));
		t.setDestination(rs.getString("Destination"));
		t.setDepartureTime(rs.getString("DepartureTime"));
		t.setArrivalTime(rs.getString("ArrTime"));
		t.setDate(rs.getString("Date"));
		t.setBookedSeats(rs.getInt("BookedSeats"));
		t.setPrice(rs.getDouble("Price"));
		int driverID = rs.getInt("Driver");
		int trainsID = rs.getInt("Train");
		t.setDriver(EmployeeDatabase.getEmployee(String.valueOf(driverID), database));
		t.setTrain(TrainsDatabase.getTrain(String.valueOf(trainsID), database));
		return t;
	}
	
	public static void EditTrip(Trip t,Database database) throws SQLException
	{
		String update="UPDATE `trips` SET "
				+ "`Start`='"+t.getStartStation()+"',`Destination`='"+t.getDestination()+"',"
				+ "`DepartureTime`='"+t.getDepartureTime()+"',`ArrTime`='"+t.getArrivalTime()+"',"
				+ "`Date`='"+t.getDate()+"',"
				+ "`Price`='"+t.getPrice()+"',`Driver`='"+t.getDriver().getID()+"',"
				+ "`Train`='"+t.getTrain().getId()+"' WHERE `ID` ="+t.getId()+";";
		database.getStatement().execute(update);
	}
	
	public static void DeleteTrip(String id ,Database database) throws SQLException
	{
		String delete="DELETE FROM `trips` WHERE `ID` = "+id+";";
		database.getStatement().execute(delete);
		
		String drop = "DROP TABLE `Trip"+id+"Passenger`;";
		database.getStatement().execute(drop);
	}
	
	public static void BookTrip(Trip t ,String passengerID, String num, Database database) throws SQLException
	{
		String insert = "INSERT INTO `trip"+t.getId()+"passenger`(`Passenger`, `Tickets`) "
				+ "VALUES ('"+passengerID+"','"+num+"');";
		database.getStatement().execute(insert);
	}
	
	public static String[][] getPassengers(String id, Database database) throws SQLException
	{
		String select = "SELECT * FROM `trip"+id+"passenger`;";
		ResultSet rs=database.getStatement().executeQuery(select);
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Integer> nums = new ArrayList<>();
		while(rs.next())
		{
			ids.add(rs.getInt("Passenger"));
			nums.add(rs.getInt("Tickets"));
		}
		String [][] array =new String [ids.size()][2];
		for(int i=0;i<ids.size();i++)
		{
			array[i][0] = String.valueOf(ids.get(i));
			array[i][1] = String.valueOf(nums.get(i));
		}
		return array;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

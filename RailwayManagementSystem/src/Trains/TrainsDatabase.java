package Trains;
import java.sql.*;
import java.util.ArrayList;

import railwayManagementSystem.Database;

public class TrainsDatabase {
	
	public static void AddTrain(Train t,Database database) throws SQLException
	{
		String insert = "INSERT INTO `trains`(`ID`, `Capacity`, `Description`) VALUES "
				+ "('"+t.getId()+"','"+t.getCapacity()+"','"+t.getDescription()+"');";
		database.getStatement().execute(insert);
	}
	
	public static int getNextID(Database database) throws SQLException
	{
		int id =0;
		if(getAllTrains(database).size()!=0)
		{
			id = getAllTrains(database).get(getAllTrains(database).size()-1).getId()+1;
		}
		return id;
	}
	
	public static ArrayList<Train> getAllTrains(Database database) throws SQLException
	{
		String select = "SELECT * FROM `trains`;";
		ArrayList<Train> trains = new ArrayList<>();
		
		ResultSet rs = database.getStatement().executeQuery(select);
		while(rs.next())
		{
			Train t=new Train();
			t.setId(rs.getInt("ID"));
			t.setCapacity(rs.getInt("Capacity"));
			t.setDescription(rs.getString("Description"));
			trains.add(t);
		}
		return trains;
	}
	
	public static String[] getTrainsIDs(Database database) throws SQLException
	{
		ArrayList<Train> trains = getAllTrains(database);
		String[] array = new String[trains.size()];
		for(int i=0;i<trains.size();i++)
		{
			array[i]=String.valueOf(trains.get(i).getId());
		}
		return array;
	}
	
	public static Train getTrain(String id,Database database) throws SQLException
	{
		Train t =new Train();
		String select="SELECT `ID`, `Capacity`, `Description` FROM `trains` WHERE `ID` = "+id+";";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		t.setId(rs.getInt("ID"));
		t.setCapacity(rs.getInt("Capacity"));
		t.setDescription(rs.getString("Description"));
		return t;
	}
	
	public static void editTrain(Train t, Database database) throws SQLException
	{
		String update = "UPDATE `trains` SET `Capacity`='"+t.getCapacity()+
				"',`Description`='"+t.getDescription()+"' WHERE `ID`= "+t.getId()+";";
		database.getStatement().execute(update);
		
	}
	
	public static void DeleteTrain(String id, Database database) throws SQLException
	{
		String delete="DELETE FROM `trains` WHERE `ID`="+id+";";
		database.getStatement().execute(delete);
	}
	
	public static String[] getTrainsName(Database database) throws SQLException
	{
		ArrayList<Train> train = getAllTrains(database);
		String [] array = new String[train.size()];
		for(int i=0;i<train.size();i++)
		{
			array[i] = train.get(i).getDescription();
		}
		return array;
		
	}
	
	public static Train getTrainByDescription(String description,Database database) throws SQLException
	{
		Train t =new Train();
		String select="SELECT `ID`, `Capacity`, `Description` FROM `trains` WHERE `Description` = '"+description+"';";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		t.setId(rs.getInt("ID"));
		t.setCapacity(rs.getInt("Capacity"));
		t.setDescription(rs.getString("Description"));
		return t;
	}
}
